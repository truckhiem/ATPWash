package com.atp.wash.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.atp.wash.Contanst;
import com.atp.wash.GeneralUtil;
import com.atp.wash.R;
import com.atp.wash.models.SaveInfo;
import com.atp.wash.models.WashingMachineItem;
import com.google.api.client.util.Key;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.google.api.client.json.GenericJson;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.model.KinveyMetaData;

import java.util.ArrayList;

/**
 * Created by khiem on 2/27/16.
 */
public class ATPApplication extends Application{
    private static ATPApplication instance;
    private ArrayList<WashingMachineItem> listMachine;
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
            listMachine = (ArrayList<WashingMachineItem>) mGson.fromJson(strLstMachine, new TypeToken<ArrayList<WashingMachineItem>>() {}.getType());
        if(listMachine == null) {
            listMachine = new ArrayList<>();
            for (int i = 0; i < Contanst.TOTAL_MACHINE; i++) {
                WashingMachineItem item = new WashingMachineItem(i + 1);
                listMachine.add(item);
            }
            saveToCache();
        }
    }

    public ArrayList<WashingMachineItem> getListMachine(){
        return listMachine;
    }

    public void updateStatusMachine(WashingMachineItem item){
        for (int i = 0; i < listMachine.size(); i++) {
            if(item.getMachineNumber() == listMachine.get(i).getMachineNumber()){
                listMachine.set(i, item);
                break;
            }
        }
        saveToCache();
        commitMachine(item, true);
        commitListMachineUnSave();
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

    public void commitMachine(final WashingMachineItem item, final boolean isNew) {
        SaveInfo saveItem = new SaveInfo();
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
        AsyncAppData<SaveInfo> myevents = mKinveyClient.appData("eventsCollection", SaveInfo.class);
        myevents.save(saveItem, new KinveyClientCallback<SaveInfo>() {
            @Override
            public void onSuccess(SaveInfo eventEntity) {
                Log.e("TAG", "success to save event data");
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("TAG", "failed to save event data", e);
                if(isNew)
                    commitFail(item);
            }

        });
    }

    private void commitListMachineUnSave(){
        ArrayList<WashingMachineItem> listMachineUnsave = loadListMachineUnsave();
        if(listMachineUnsave == null)
            return;
        for (int i = 0; i < listMachineUnsave.size(); i++) {
            commitMachine(listMachineUnsave.get(i),false);
        }

    }

    private void commitFail(WashingMachineItem item){
        ArrayList<WashingMachineItem> listMachineUnsave = loadListMachineUnsave();
        if(listMachineUnsave == null){
            listMachineUnsave = new ArrayList<>();
        }
        listMachineUnsave.add(item);
        Gson mGson = new Gson();
        GeneralUtil.saveString(getContext(), Contanst.Shareference.LIST_MACHINE_UNSAVE, mGson.toJson(listMachineUnsave));

    }

    private ArrayList<WashingMachineItem> loadListMachineUnsave(){
        ArrayList<WashingMachineItem> listMachineUnsave = null;
        Gson mGson = new Gson();
        String strLstMachine = GeneralUtil.getString(getContext(),Contanst.Shareference.LIST_MACHINE_UNSAVE,"");
        if(!strLstMachine.equals(""))
            listMachineUnsave = (ArrayList<WashingMachineItem>) mGson.fromJson(strLstMachine, new TypeToken<ArrayList<WashingMachineItem>>() {}.getType());
        return listMachineUnsave;
    }

}
