package com.atp.wash.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.atp.wash.R;
import com.atp.wash.WashingDetailActivity;
import com.atp.wash.application.ATPApplication;
import com.atp.wash.models.WSErrorDataItemCommit;
import com.atp.wash.models.WSDataItemLocal;
import com.atp.wash.models.WSErrorDataItemLocal;
import com.atp.wash.utils.Contanst;

import java.util.ArrayList;

/**
 * Created by khiem on 2/25/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    private View rootView;
    private ImageView ws1, ws2, ws3, ws4, ws5, ws6, ws7, ws8;
    private ArrayList<ImageView> lstImgView;
    private Context mContext;
    private WSDataItemLocal machineSelected;

    public MainFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getActivity();
        initData();
        initView();
        return rootView;
    }

    private void initData() {
    }

    private void initView() {
        lstImgView = new ArrayList<>();
        ws1 = (ImageView) rootView.findViewById(R.id.ws_1);
        ws2 = (ImageView) rootView.findViewById(R.id.ws_2);
        ws3 = (ImageView) rootView.findViewById(R.id.ws_3);
        ws4 = (ImageView) rootView.findViewById(R.id.ws_4);
        ws5 = (ImageView) rootView.findViewById(R.id.ws_5);
        ws6 = (ImageView) rootView.findViewById(R.id.ws_6);
        ws7 = (ImageView) rootView.findViewById(R.id.ws_7);
        ws8 = (ImageView) rootView.findViewById(R.id.ws_8);

        lstImgView.add(ws1);
        lstImgView.add(ws2);
        lstImgView.add(ws3);
        lstImgView.add(ws4);
        lstImgView.add(ws5);
        lstImgView.add(ws6);
        lstImgView.add(ws7);
        lstImgView.add(ws8);

        for (int i = 0; i < 8; i++) {
            lstImgView.get(i).setTag(i);
            lstImgView.get(i).setOnClickListener(this);
            lstImgView.get(i).setOnLongClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        machineSelected = getWSJustSelected((int) v.getTag());
        if(machineSelected.isError()){
            startDialogMachineError();
        }else if(machineSelected.isActive()){
            startDialogWarning();
        }else{
            startDialogActivate();
        }
    }

    private void startDialogMachineError(){
        final Dialog mDialog = new Dialog(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(R.layout.dialog_layout);

        TextView mTxtDialog = (TextView) mDialog.findViewById(R.id.txt_dialog);
        mTxtDialog.setText(String.format(getString(R.string.text_machine_error), machineSelected.getMachineNumber()));

        Button btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel_dialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button btnOK = (Button) mDialog.findViewById(R.id.btn_ok_dialog);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void startDialogActivate(){
        final Dialog mDialog = new Dialog(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(R.layout.dialog_layout);

        TextView mTxtDialog = (TextView) mDialog.findViewById(R.id.txt_dialog);
        mTxtDialog.setText(String.format(getString(R.string.text_active_ws), machineSelected.getMachineNumber()));

        Button btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel_dialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button btnOK = (Button) mDialog.findViewById(R.id.btn_ok_dialog);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                startWashingDetail();
            }
        });
        mDialog.show();
    }

    private void startDialogFinish(){
        final Dialog mDialog = new Dialog(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(R.layout.dialog_layout);

        TextView mTxtDialog = (TextView) mDialog.findViewById(R.id.txt_dialog);
        mTxtDialog.setText(String.format(getString(R.string.text_ws_finish), machineSelected.getMachineNumber()));

        Button btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel_dialog);
        btnCancel.setVisibility(View.GONE);
        Button btnOK = (Button) mDialog.findViewById(R.id.btn_ok_dialog);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void startDialogWarning(){
        final Dialog mDialog = new Dialog(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(R.layout.dialog_layout);

        TextView mTxtDialog = (TextView) mDialog.findViewById(R.id.txt_dialog);
        mTxtDialog.setText(String.format(getString(R.string.text_ws_running), machineSelected.getMachineNumber()));
        mTxtDialog.setTextColor(getResources().getColor(R.color.holo_red_light));

        Button btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel_dialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        Button btnOK = (Button) mDialog.findViewById(R.id.btn_ok_dialog);
        btnOK.setText(getString(R.string.txt_update));
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                startWashingDetail();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDialog.isShowing())
                    mDialog.dismiss();
            }
        }, 2000);

        mDialog.show();
    }

    private void startWashingDetail(){
        Intent mIntent = new Intent(mContext, WashingDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.ExtraIntent.INTENT_WS_ITEM, machineSelected);
        mIntent.putExtras(bundle);
        mContext.startActivity(mIntent);
    }

    private WSDataItemLocal getWSJustSelected(int numberMachine){
        return ATPApplication.getInstance().getListMachine().get(numberMachine);
    }

    @Override
    public void onResume() {
        super.onResume();
        refeshView();
    }

    private void refeshView() {
        ArrayList<WSDataItemLocal> lstMachine =  ATPApplication.getInstance().getListMachine();
        for (int i = 0; i < lstMachine.size(); i++) {
            if(lstMachine.get(i).isActive()){
                setMachineSelected(i);
            }
        }
        machineSelected = null;
    }
    private void setMachineSelected(int numberMachine){
        lstImgView.get(numberMachine).setImageResource(R.drawable.washing_machine);
    }

    private void startDialogError(){
        final Dialog mDialog = new Dialog(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        mDialog.setContentView(R.layout.dialog_error_layout);

        final CheckBox chkUnActivate = (CheckBox) mDialog.findViewById(R.id.chk_un_activate);
        final CheckBox chkAnotherError = (CheckBox) mDialog.findViewById(R.id.chk_another_error);
        final EditText inputStrError = (EditText) mDialog.findViewById(R.id.input_str_error);

        Button btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel_dialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button btnOK = (Button) mDialog.findViewById(R.id.btn_ok_dialog);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chkAnotherError.isChecked() && !chkUnActivate.isChecked()) {
                    machineSelected.setIsError(false);
                } else {
                    WSErrorDataItemLocal saveItem = new WSErrorDataItemLocal();
                    saveItem.setMachineNumber(machineSelected.getMachineNumber());
                    saveItem.setErrorReason(inputStrError.getText().toString());
                    saveItem.setIsAnotherError(chkAnotherError.isChecked());
                    saveItem.setIsError(true);
                    saveItem.setUnActivate(chkUnActivate.isChecked());
                    ATPApplication.getInstance().commitMachineError(saveItem, true);
                }
                mDialog.dismiss();
            }
        });

        TextView txtTitle = (TextView) mDialog.findViewById(R.id.txt_title);
        txtTitle.setText(String.format(getString(R.string.text_title_error),machineSelected.getMachineNumber()));

        mDialog.show();
    }

    @Override
    public boolean onLongClick(View v) {
        machineSelected = getWSJustSelected((int) v.getTag());
        if(machineSelected.isError()){
            startDialogMachineError();
        }else {
            startDialogError();
        }
        return true;
    }
}
