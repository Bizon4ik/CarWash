package biz.podoliako.carwash.models.pojo;

public class Authorization {
    private Integer carWashOwnerId = null;
    private Integer ownerid = null;
    private String userName = null;
    private boolean userAuthorized = false;

    public Integer getCarWashOwnerId() {
        return carWashOwnerId;
    }

    public void setCarWashOwnerId(Integer carWashOwnerId) {
        userAuthorized = true;
        this.carWashOwnerId = carWashOwnerId;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "carWashOwnerId=" + carWashOwnerId +
                ", ownerid=" + ownerid +
                ", userName='" + userName + '\'' +
                '}';
    }

    public boolean isUserAuthorized() {
        return userAuthorized;
    }
}
