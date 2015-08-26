package biz.podoliako.carwash.models.entity;


import java.util.Date;

public class WasherManInBox {
    private Integer id;
    private Integer userId;
    private Integer carWashId;
    private Integer boxNumber;
    private Date startInBox;
    private Date finishInBox;
    private Integer setInBoxBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarWashId() {
        return carWashId;
    }

    public void setCarWashId(Integer carWashId) {
        this.carWashId = carWashId;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public Date getStartInBox() {
        return startInBox;
    }

    public void setStartInBox(Date startInBox) {
        this.startInBox = startInBox;
    }

    public Date getFinishInBox() {
        return finishInBox;
    }

    public void setFinishInBox(Date finishInBox) {
        this.finishInBox = finishInBox;
    }

    public Integer getSetInBoxBy() {
        return setInBoxBy;
    }

    public void setSetInBoxBy(Integer setInBoxBy) {
        this.setInBoxBy = setInBoxBy;
    }

    @Override
    public String toString() {
        return "WasherManInBox{" +
                "id=" + id +
                ", userId=" + userId +
                ", carWashId=" + carWashId +
                ", boxNumber=" + boxNumber +
                ", startInBox=" + startInBox +
                ", finishInBox=" + finishInBox +
                ", setInBoxBy=" + setInBoxBy +
                '}';
    }
}
