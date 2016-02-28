package com.atp.wash;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.atp.wash.application.ATPApplication;
import com.atp.wash.models.WashingMachineItem;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by khiem on 2/25/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private ImageView ws1, ws2, ws3, ws4, ws5, ws6, ws7, ws8;
    private Context mContext;
    private WashingMachineItem machineSelected;

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
        ws1 = (ImageView) rootView.findViewById(R.id.ws_1);
        ws2 = (ImageView) rootView.findViewById(R.id.ws_2);
        ws3 = (ImageView) rootView.findViewById(R.id.ws_3);
        ws4 = (ImageView) rootView.findViewById(R.id.ws_4);
        ws5 = (ImageView) rootView.findViewById(R.id.ws_5);
        ws6 = (ImageView) rootView.findViewById(R.id.ws_6);
        ws7 = (ImageView) rootView.findViewById(R.id.ws_7);
        ws8 = (ImageView) rootView.findViewById(R.id.ws_8);

        ws1.setTag(0);
        ws2.setTag(1);
        ws3.setTag(2);
        ws4.setTag(3);
        ws5.setTag(4);
        ws6.setTag(5);
        ws7.setTag(6);
        ws8.setTag(7);

        ws1.setOnClickListener(this);
        ws2.setOnClickListener(this);
        ws3.setOnClickListener(this);
        ws4.setOnClickListener(this);
        ws5.setOnClickListener(this);
        ws6.setOnClickListener(this);
        ws7.setOnClickListener(this);
        ws8.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        machineSelected = getWSJustSelected((int) v.getTag());
        if(machineSelected.isActive()){
            startDialogWarning();
        }else{
            startDialogActivate();
        }

//        startDialogFinish(1);

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

    private void startDialogFinish(int WSJustFinish){
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

    private WashingMachineItem getWSJustSelected(int numberMachine){
        return ATPApplication.getInstance().getListMachine().get(numberMachine);
    }

    @Override
    public void onResume() {
        super.onResume();
        refeshView();
    }

    private void refeshView() {
        ArrayList<WashingMachineItem> lstMachine =  ATPApplication.getInstance().getListMachine();
        for (int i = 0; i < lstMachine.size(); i++) {
            if(lstMachine.get(i).isActive()){
                setMachineSelected(i);
            }
        }
        machineSelected = null;
    }
    private void setMachineSelected(int numberMachine){
        switch (numberMachine) {
            case 0:
                ws1.setImageResource(R.drawable.washing_machine);
                break;
            case 1:
                ws2.setImageResource(R.drawable.washing_machine);
                break;
            case 2:
                ws3.setImageResource(R.drawable.washing_machine);
                break;
            case 3:
                ws4.setImageResource(R.drawable.washing_machine);
                break;
            case 4:
                ws5.setImageResource(R.drawable.washing_machine);
                break;
            case 5:
                ws6.setImageResource(R.drawable.washing_machine);
                break;
            case 6:
                ws7.setImageResource(R.drawable.washing_machine);
                break;
            case 7:
                ws8.setImageResource(R.drawable.washing_machine);
                break;
        }
    }
}
