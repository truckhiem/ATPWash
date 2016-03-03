package com.atp.wash.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.atp.wash.models.WSErrorDataItemCommit;
import com.atp.wash.models.WSErrorDataItemLocal;
import com.atp.wash.utils.Contanst;
import com.atp.wash.utils.GeneralUtil;
import com.atp.wash.R;
import com.atp.wash.models.WSDataItemCommit;
import com.atp.wash.models.WSDataItemLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;

import java.util.ArrayList;

/**
 * Created by khiem on 2/27/16.
 */
public class ATPApplication extends Application{
    private static ATPApplication instance;
    private ArrayList<WSDataItemLocal> listMachine;
    private static Context mContext;

    public static synchronized ATPApplication getInstance(){
        if(instance == null)
            instance = new ATPApplication();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ATPApplication.mContext = getApplicationContext();
        getInstance().initListMachine();
        loginServer();
    }

    public Context getContext(){
        return ATPApplication.mContext;
    }

    private void initListMachine() {
        Gson mGson = new Gson();
        String strLstMachine = GeneralUtil.getString(getContext(),Contanst.Shareference.LIST_MACHINE_STATUS,"");
        if(!strLstMachine.equals(""))
            listMachine = (ArrayList<WSDataItemLocal>) mGson.fromJson(strLstMachine, new TypeToken<ArrayList<WSDataItemLocal>>() {}.getType());
        if(listMachine == null) {
            listMachine = new ArrayList<>();
            for (int i = 0; i < Contanst.TOTAL_MACHINE; i++) {
                WSDataItemLocal item = new WSDataItemLocal(i + 1);
                listMachine.add(item);
            }
            saveToCache();
        }
    }

    public ArrayList<WSDataItemLocal> getListMachine(){
        return listMachine;
    }

    public void updateStatusMachine(WSDataItemLocal item){
        for (int i = 0; i < listMachine.size(); i++) {
            if(item.getMachineNumber() == listMachine.get(i).getMachineNumber()){
                listMachine.set(i, item);
                break;
            }
        }
        saveToCache();
        commitMachine(item, true);
        commitListMachineUnSave();
        commitListMachineErrorUnSave();
    }

    private void saveToCache(){
        Gson mGson = new Gson();
        GeneralUtil.saveString(getContext(), Contanst.Shareference.LIST_MACHINE_STATUS, mGson.toJson(listMachine));
    }

    private void loginServer(){
        final Client mKinveyClient = new Client.Builder(getContext().getString(R.string.api_key), getContext().getString(R.string.api_secret)
                , getContext()).build();
        if(!mKinveyClient.user().isUserLoggedIn()) {
            mKinveyClient.user().login("atp", "atp", new KinveyClientCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    Log.e("TAG", "success to save event data");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e("TAG", "fail to save event data");
                }
            });
        }
    }

    public void commitMachine(final WSDataItemLocal item, final boolean isNew) {
        WSDataItemCommit saveItem = new WSDataItemCommit();
        saveItem.setCustInfo(item.getCustInfo());
        saveItem.setCustName(item.getCustName());
        saveItem.setIsDry(item.isDry());
        saveItem.setMoreWashing(item.getMoreWashing());
        saveItem.setMoreSoftner(item.getMoreSoftner());
        saveItem.setTotalPrice(item.getTotalPrice());
        saveItem.setTotalPriceSoftner(item.getTotalSoftner());
        saveItem.setTotalPriceWashing(item.getTotalWashing());
        saveItem.setMachineNumber(item.getMachineNumber());

        final Client mKinveyClient = new Client.Builder(getContext().getString(R.string.api_key), getContext().getString(R.string.api_secret)
                , getContext()).build();
        AsyncAppData<WSDataItemCommit> myevents = mKinveyClient.appData(getContext().getString(R.string.kinvey_collection_machine), WSDataItemCommit.class);
        myevents.save(saveItem, new KinveyClientCallback<WSDataItemCommit>() {
            @Override
            public void onSuccess(WSDataItemCommit eventEntity) {
                Log.e("TAG", "success to save event data");
                commitStatus(item, true);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("TAG", "failed to save event data", e);
                if(isNew)
                    commitStatus(item, false);
            }

        });
    }

    public void commitMachineError(final WSErrorDataItemLocal item, final boolean isNew) {
        WSErrorDataItemCommit itemCommit = new WSErrorDataItemCommit();
        itemCommit.setErrorReason(item.getErrorReason());
        itemCommit.setIsAnotherError(item.isAnotherError());
        itemCommit.setIsError(item.isError());
        itemCommit.setUnActivate(item.isUnActivate());
        itemCommit.setMachineNumber(item.getMachineNumber());

        final Client mKinveyClient = new Client.Builder(getContext().getString(R.string.api_key), getContext().getString(R.string.api_secret)
                , getContext()).build();
        AsyncAppData<WSErrorDataItemCommit> myevents = mKinveyClient.appData(getContext().getString(R.string.kinvey_collection_error), WSErrorDataItemCommit.class);
        myevents.save(itemCommit, new KinveyClientCallback<WSErrorDataItemCommit>() {
            @Override
            public void onSuccess(WSErrorDataItemCommit eventEntity) {
                Log.e("TAG", "success to save event data");
                commitStatus(item, true);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("TAG", "failed to save event data", e);
                if(isNew)
                    commitStatus(item, false);
            }

        });

        for (int i = 0; i < listMachine.size(); i++) {
            if(listMachine.get(i).getMachineNumber() == item.getMachineNumber()){
                listMachine.get(i).setErrorReason(item.getErrorReason());
                listMachine.get(i).setIsAnotherError(item.isAnotherError());
                listMachine.get(i).setIsError(item.isError());
                listMachine.get(i).setUnActivate(item.isUnActivate());
                break;
            }
        }
    }

    private void commitListMachineUnSave(){
        ArrayList<WSDataItemLocal> listMachineUnsave = loadListMachineUnsave();
        if(listMachineUnsave == null)
            return;
        for (int i = 0; i < listMachineUnsave.size(); i++) {
            commitMachine(listMachineUnsave.get(i),false);
        }

    }

    private void commitStatus(WSDataItemLocal item, boolean status){
        ArrayList<WSDataItemLocal> listMachineUnsave = loadListMachineUnsave();
        if(status){
            if(listMachineUnsave != null){
                for (int i = 0; i < listMachineUnsave.size(); i++) {
                    if(listMachineUnsave.get(i).getMachineNumber() == item.getMachineNumber()){
                        listMachineUnsave.remove(listMachineUnsave.get(i));
                        break;
                    }
                }
            }
        }else {
            if (listMachineUnsave == null) {
                listMachineUnsave = new ArrayList<>();
            }
            listMachineUnsave.add(item);
        }
        Gson mGson = new Gson();
        GeneralUtil.saveString(getContext(), Contanst.Shareference.LIST_MACHINE_UNSAVE, mGson.toJson(listMachineUnsave));

    }

    private ArrayList<WSDataItemLocal> loadListMachineUnsave(){
        ArrayList<WSDataItemLocal> listMachineUnsave = null;
        Gson mGson = new Gson();
        String strLstMachine = GeneralUtil.getString(getContext(),Contanst.Shareference.LIST_MACHINE_UNSAVE,"");
        if(!strLstMachine.equals(""))
            listMachineUnsave = (ArrayList<WSDataItemLocal>) mGson.fromJson(strLstMachine, new TypeToken<ArrayList<WSDataItemLocal>>() {}.getType());
        return listMachineUnsave;
    }

    private void commitListMachineErrorUnSave(){
        ArrayList<WSErrorDataItemLocal> listMachineUnsave = loadListMachineErrorUnsave();
        if(listMachineUnsave == null)
            return;
        for (int i = 0; i < listMachineUnsave.size(); i++) {
            commitMachineError(listMachineUnsave.get(i), false);
        }

    }

    private void commitStatus(WSErrorDataItemLocal item, boolean status){
        ArrayList<WSErrorDataItemLocal> listMachineUnsave = loadListMachineErrorUnsave();
        if(status){
            if(listMachineUnsave != null){
                for (int i = 0; i < listMachineUnsave.size(); i++) {
                    if (listMachineUnsave.get(i).getMachineNumber() == item.getMachineNumber()) {
                        listMachineUnsave.remove(listMachineUnsave.get(i));
                        break;
                    }
                }
            }
        }else {
            if (listMachineUnsave == null) {
                listMachineUnsave = new ArrayList<>();
            }
            listMachineUnsave.add(item);
        }
        Gson mGson = new Gson();
        GeneralUtil.saveString(getContext(), Contanst.Shareference.LIST_MACHINE_ERROR_UNSAVE, mGson.toJson(listMachineUnsave));
    }

    private ArrayList<WSErrorDataItemLocal> loadListMachineErrorUnsave(){
        ArrayList<WSErrorDataItemLocal> listMachineUnsave = null;
        Gson mGson = new Gson();
        String strLstMachine = GeneralUtil.getString(getContext(),Contanst.Shareference.LIST_MACHINE_ERROR_UNSAVE,"");
        if(!strLstMachine.equals(""))
            listMachineUnsave = (ArrayList<WSErrorDataItemLocal>) mGson.fromJson(strLstMachine, new TypeToken<ArrayList<WSErrorDataItemLocal>>() {}.getType());
        return listMachineUnsave;
    }

}
