package com.atp.wash.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by khiem.tran on 03/03/2016.
 */
public class WSErrorDataItemCommit extends GenericJson {

    @Key
    private int machineNumber;
    @Key
    private boolean isAnotherError = false;
    @Key
    private boolean unActivate = false;
    @Key
    private boolean isError = false;
    @Key
    private String errorReason = "";
    @Key("_kmd")
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;

    public WSErrorDataItemCommit(){}

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
