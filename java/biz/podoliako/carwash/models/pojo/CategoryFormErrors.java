package biz.podoliako.carwash.models.pojo;

/**
 * Created by bizon4ik on 21.06.15.
 */
public class CategoryFormErrors {

    public String nameErrorMsg = null;
    public String descriptionErrorMsg = null;

    private boolean hasErrors = false;

    public String getNameErrorMsg() {
        return nameErrorMsg;
    }

    public void setNameErrorMsg(String msg) {
        if (msg != null) {
            hasErrors = true;
        }
        this.nameErrorMsg = msg;
    }

    public String getDescriptionErrorMsg() {
        return descriptionErrorMsg;
    }

    public void setDescriptionErrorMsg(String msg) {
        if (msg != null) {
            hasErrors = true;
        }
        this.descriptionErrorMsg = msg;
    }

    public boolean isHasErrors() {
        return  hasErrors;
    }

    @Override
    public String toString() {
        return "CategoryFormErrors{" +
                "nameErrorMsg='" + nameErrorMsg + '\'' +
                ", descriptionErrorMsg='" + descriptionErrorMsg + '\'' +
                ", hasErrors=" + hasErrors +
                '}';
    }
}
