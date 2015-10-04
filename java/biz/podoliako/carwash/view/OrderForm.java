package biz.podoliako.carwash.view;


import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.services.entity.ServiceInOrder;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;
import biz.podoliako.carwash.services.validation.NotNegative;



import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderForm {

    @NotEmptyTrim
    private String carNumber;

    private Integer categoryId;


    @NotNegative(message = "Бренд не выбран")
    private Integer carBrandId;

    @NotNull(message = "Не выбран бокс")
    private Integer boxNumber;

    private PaymentMethod paymentMethod;

    private List<ServiceInOrder> serviceIdList;

    private Integer userId;

    private Integer ownerId;

    private Integer carWashId;

    private Integer orderId;



    public OrderForm() {
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Integer carBrandId) {
        this.carBrandId = carBrandId;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<ServiceInOrder> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<ServiceInOrder> serviceIdList) {
        this.serviceIdList = serviceIdList;
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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "carNumber='" + carNumber + '\'' +
                ", categoryId=" + categoryId +
                ", carBrandId=" + carBrandId +
                ", boxNumber=" + boxNumber +
                ", paymentMethod=" + paymentMethod +
                ", serviceIdList=" + serviceIdList +
                ", userId=" + userId +
                ", ownerId=" + ownerId +
                ", carWashId=" + carWashId +
                ", orderId=" + orderId +
                '}';
    }
}
