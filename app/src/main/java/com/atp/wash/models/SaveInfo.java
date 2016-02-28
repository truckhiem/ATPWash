package com.atp.wash.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by khiem on 2/28/16.
 */
public class SaveInfo extends GenericJson{
    @Key
    private int machineNumber;
    @Key
    private String custName;
    @Key
    private String custInfo;
    @Key
    private int moreWashing;
    @Key
    private int moreSoftner;
    @Key
    private boolean isDry;
    @Key
    private long totalPriceWashing;
    @Key
    private long totalPriceSoftner;
    @Key
    private long totalPrice;
    @Key("_kmd")
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;

    public SaveInfo(){}

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

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

    public boolean isDry() {
        return isDry;
    }

    public void setIsDry(boolean isDry) {
        this.isDry = isDry;
    }

    public long getTotalPriceWashing() {
        return totalPriceWashing;
    }

    public void setTotalPriceWashing(long totalPriceWashing) {
        this.totalPriceWashing = totalPriceWashing;
    }

    public long getTotalPriceSoftner() {
        return totalPriceSoftner;
    }

    public void setTotalPriceSoftner(long totalPriceSoftner) {
        this.totalPriceSoftner = totalPriceSoftner;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
