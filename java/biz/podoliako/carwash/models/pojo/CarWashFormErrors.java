package biz.podoliako.carwash.models.pojo;

/**
 * Created by bizon4ik on 16.06.15.
 */
public class CarWashFormErrors {

    public String nameErrorMsg = null;
    public String addressErrorMsg = null;
    private String phoneNumberErrorMsg = null;

    private boolean hasErrors = false;

    public CarWashFormErrors() {
    }

    public String getNameErrorMsg() {
        return nameErrorMsg;
    }

    public void setNameErrorMsg (String msg) {
        if (msg != null) {
            this.hasErrors = true;
        }
        this.nameErrorMsg = msg;
    }

    public String getAddressErrorMsg() {
        return addressErrorMsg;
    }

    public void setAddressErrorMsg(String msg) {
        if (msg != null){
            this.hasErrors = true;
        }
        this.addressErrorMsg = msg;
    }

    public boolean isHasErrors() {
        return this.hasErrors;
    }

    public String getPhoneNumberErrorMsg() {
        return phoneNumberErrorMsg;
    }

    public void setPhoneNumberErrorMsg(String msg) {
        if (msg != null){
            this.hasErrors = true;
        }
        this.phoneNumberErrorMsg = msg;
    }
}
