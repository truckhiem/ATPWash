package com.atp.wash.models;

import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by khiem.tran on 03/03/2016.
 */
public class WSErrorDataItemLocal {

    private int machineNumber;
    private boolean isAnotherError = false;
    private boolean unActivate = false;
    private boolean isError = false;
    private String errorReason = "";

    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public boolean isUnActivate() {
        return unActivate;
    }

    public void setUnActivate(boolean unActivate) {
        this.unActivate = unActivate;
    }

    public boolean isAnotherError() {
        return isAnotherError;
    }

    public void setIsAnotherError(boolean isAnotherError) {
        this.isAnotherError = isAnotherError;
    }

    public int getMachineNumber(){return machineNumber; }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

}
