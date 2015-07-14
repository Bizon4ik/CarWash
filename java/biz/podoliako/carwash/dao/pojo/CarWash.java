package biz.podoliako.carwash.dao.pojo;

import java.sql.Time;
import java.util.Date;

public class CarWash {
    private Integer id;
    private String name;
    private String address = "";
    private Integer boxAmount;
    private String phoneNumber = "";
    private Date dateOfCreation;
    private Integer ownerId;
    private Time startDayShift;
    private Time finishDayShift;




    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getBoxAmount() {
        return boxAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public void setBoxAmount(Integer boxAmount) {
        this.boxAmount = boxAmount;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Time getStartDayShift() {
        return startDayShift;
    }

    public void setStartDayShift(Time startDayShift) {
        this.startDayShift = startDayShift;
    }

    public Time getFinishDayShift() {
        return finishDayShift;
    }

    public void setFinishDayShift(Time finishDayShift) {
        this.finishDayShift = finishDayShift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarWash carWash = (CarWash) o;

        if (id != null ? !id.equals(carWash.id) : carWash.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CarWash{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", boxAmount=" + boxAmount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", ownerId=" + ownerId +
                ", startDayShift=" + startDayShift +
                ", finishDayShift=" + finishDayShift +
                '}';
    }
}
