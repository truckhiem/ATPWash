package com.atp.wash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.atp.wash.application.ATPApplication;
import com.atp.wash.models.WashingMachineItem;

/**
 * Created by khiem on 2/26/16.
 */
public class WashingDetailActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private TextView mTxtTotalPrice, mTxtNumWashing, mTxtNumSoftner, mTxtTotalWashing, mTxtTotalSoftner, mTxtTotalDry;
    private EditText mInputCustName, mInputCustInfo;
    private Button btnIncreaseWashing, btnDecreaseWashing, btnIncreaseSoftner, btnDecreaseSoftner, btnOk, btnCancel;
    private WashingMachineItem wsItem;
    private CheckBox mChkIsDry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washing_detail);
        getDataIntent();
        intiView();
        bindingData();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        wsItem = (WashingMachineItem) bundle.get(Contanst.ExtraIntent.INTENT_WS_ITEM);
    }

    private void bindingData() {
        mTxtNumWashing.setText(String.valueOf(wsItem.getMoreWashing()));
        mTxtNumSoftner.setText(String.valueOf(wsItem.getMoreSoftner()));
        mTxtTotalSoftner.setText(GeneralUtil.formatPrice(wsItem.getTotalSoftner()));
        mTxtTotalWashing.setText(GeneralUtil.formatPrice(wsItem.getTotalWashing()));
        mTxtTotalPrice.setText(GeneralUtil.formatPrice(wsItem.getTotalPrice()));
        mTxtTotalDry.setText(wsItem.isDry() ? GeneralUtil.formatPrice(Contanst.Prices.WashingPrice) : GeneralUtil.formatPrice(0));

        if(!wsItem.isActive()){
            btnOk.setText(getString(R.string.txt_active_machine));
        }
    }

    private void intiView() {
        btnIncreaseWashing = (Button) findViewById(R.id.btn_increase_washing);
        btnDecreaseWashing = (Button) findViewById(R.id.btn_decrease_washing);
        btnIncreaseSoftner = (Button) findViewById(R.id.btn_increase_softner);
        btnDecreaseSoftner = (Button) findViewById(R.id.btn_decrease_softner);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        mInputCustName = (EditText) findViewById(R.id.input_cust_name);
        mInputCustInfo = (EditText) findViewById(R.id.input_cust_info);

        mTxtTotalPrice = (TextView) findViewById(R.id.txt_total_price);
        mTxtNumWashing = (TextView) findViewById(R.id.txt_num_more_washing);
        mTxtNumSoftner = (TextView) findViewById(R.id.txt_num_more_softner);
        mTxtTotalWashing = (TextView) findViewById(R.id.txt_total_washing);
        mTxtTotalSoftner = (TextView) findViewById(R.id.txt_total_softner);
        mTxtTotalDry = (TextView) findViewById(R.id.txt_total_dry);

        mChkIsDry = (CheckBox) findViewById(R.id.chk_is_dry);
        mChkIsDry.setOnCheckedChangeListener(this);

        btnIncreaseWashing.setOnClickListener(this);
        btnDecreaseWashing.setOnClickListener(this);
        btnIncreaseSoftner.setOnClickListener(this);
        btnDecreaseSoftner.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_increase_washing:
                wsItem.increaseMoreWashing();
                bindingData();
                break;
            case R.id.btn_decrease_washing:
                wsItem.decreaseMoreWashing();
                bindingData();
                break;
            case R.id.btn_increase_softner:
                wsItem.increaseMoreSoftner();
                bindingData();
                break;
            case R.id.btn_decrease_softner:
                wsItem.decreaseMoreSoftner();
                bindingData();
                break;
            case R.id.btn_ok:
                btnOKAction();
                break;
            case R.id.btn_cancel:
                btnCancelAction();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        wsItem.setIsDry(isChecked);
        bindingData();
    }

    private void btnOKAction(){
        saveData();
        finish();
    }

    private void btnCancelAction(){
        finish();
    }

    private void saveData(){
        wsItem.setIsActive(true);
        wsItem.setCustName(mInputCustName.getText().toString());
        wsItem.setCustInfo(mInputCustInfo.getText().toString());
        ATPApplication.getInstance().updateStatusMachine(wsItem);
    }
}
