package biz.podoliako.carwash.view;


import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.services.entity.ServiceInOrder;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;


import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderForm {

    @NotEmptyTrim
    private String carNumber;

    private Integer categoryId;

    @NotNull(message = "Не выбран бренд")
    private Integer carBrandId;

    @NotNull(message = "Не выбран бокс")
    private Integer boxNumber;

    private PaymentMethod paymentMethod;

    private List<ServiceInOrder> serviceIdList;

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

    @Override
    public String toString() {
        return "OrderForm{" +
                "carNumber='" + carNumber + '\'' +
                ", categoryId=" + categoryId +
                ", carBrandId=" + carBrandId +
                ", boxNumber=" + boxNumber +
                ", paymentMethod=" + paymentMethod +
                ", serviceIdList=" + serviceIdList +
                '}';
    }
}
