package biz.podoliako.carwash.models.entity;


import biz.podoliako.carwash.models.PaymentMethod;

import java.util.Date;

public class Order {
    private Integer id;
    private Integer carWashId;
    private Integer box;
    private Integer carId;
    private PaymentMethod paymentMethod;
    private Integer createdById;
    private Integer closeById;
    private Integer deleteById;
    private Date dateOfCreation;
    private Date dateOfClose;
    private Date dateOfDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarWashId() {
        return carWashId;
    }

    public void setCarWashId(Integer carWashId) {
        this.carWashId = carWashId;
    }

    public Integer getBox() {
        return box;
    }

    public void setBox(Integer box) {
        this.box = box;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public Integer getCloseById() {
        return closeById;
    }

    public void setCloseById(Integer closeById) {
        this.closeById = closeById;
    }

    public Integer getDeleteById() {
        return deleteById;
    }

    public void setDeleteById(Integer deleteById) {
        this.deleteById = deleteById;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfClose() {
        return dateOfClose;
    }

    public void setDateOfClose(Date dateOfClose) {
        this.dateOfClose = dateOfClose;
    }

    public Date getDateOfDelete() {
        return dateOfDelete;
    }

    public void setDateOfDelete(Date dateOfDelete) {
        this.dateOfDelete = dateOfDelete;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", carWashId=" + carWashId +
                ", box=" + box +
                ", carId=" + carId +
                ", paymentMethod=" + paymentMethod +
                ", createdById=" + createdById +
                ", closeById=" + closeById +
                ", deleteById=" + deleteById +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfClose=" + dateOfClose +
                ", dateOfDelete=" + dateOfDelete +
                '}';
    }
}
