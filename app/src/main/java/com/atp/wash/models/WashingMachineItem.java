package com.atp.wash.models;

import com.atp.wash.Contanst;
import com.atp.wash.GeneralUtil;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

import java.io.Serializable;

/**
 * Created by khiem on 2/27/16.
 */
public class WashingMachineItem implements Serializable{
    private int machineNumber;
    private String custName = "";
    private String custInfo = "";
    private int moreWashing = 0;
    private int moreSoftner = 0;
    private boolean isDry = false;
    private boolean isActive = false;

    public WashingMachineItem(){}

    public WashingMachineItem(int machineNumber){
        this.machineNumber = machineNumber;
    }

    public int getMachineNumber(){return machineNumber; }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustInfo() {
        return custInfo;
    }

    public void setCustInfo(String custInfo) {
        this.custInfo = custInfo;
    }

    public int getMoreWashing() {
        return moreWashing;
    }

    public void setMoreWashing(int moreWashing) {
        this.moreWashing = moreWashing;
    }

    public int getMoreSoftner() {
        return moreSoftner;
    }

    public void setMoreSoftner(int moreSoftner) {
        this.moreSoftner = moreSoftner;
    }

    public long getTotalWashing(){
        return Contanst.Prices.ExtraWashing * moreWashing;
    }

    public long getTotalSoftner(){
        return Contanst.Prices.ExtraSoft * moreSoftner;
    }

    public long getTotalPrice(){
        if(isDry)
            return getTotalSoftner() + getTotalWashing() + Contanst.Prices.WashingPrice + Contanst.Prices.DryPrice;
        return getTotalSoftner() + getTotalWashing() + Contanst.Prices.WashingPrice;
    }

    public void decreaseMoreWashing(){
        if(moreWashing == 0)
            return;
        moreWashing --;
    }

    public void increaseMoreWashing(){
        if(moreWashing == 5)
            return;
        moreWashing ++;
    }

    public void decreaseMoreSoftner(){
        if(moreSoftner == 0)
            return;
        moreSoftner --;
    }

    public void increaseMoreSoftner(){
        if(moreSoftner == 5)
            return;
        moreSoftner ++;
    }

    public boolean isDry() {
        return isDry;
    }

    public void setIsDry(boolean isDry) {
        this.isDry = isDry;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
