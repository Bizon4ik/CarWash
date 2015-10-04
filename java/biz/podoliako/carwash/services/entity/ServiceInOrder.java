package biz.podoliako.carwash.services.entity;


public class ServiceInOrder {
    private Integer orderId;
    private Integer CarWashServiceId;
    private Integer count;
    private Integer additionPrice;

    public ServiceInOrder() {
    }

    public Integer getCarWashServiceId() {
        return CarWashServiceId;
    }

    public void setCarWashServiceId(Integer carWashServiceId) {
        CarWashServiceId = carWashServiceId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAdditionPrice() {
        return additionPrice;
    }

    public void setAdditionPrice(Integer additionPrice) {
        this.additionPrice = additionPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ServiceInOrder{" +
                "orderId=" + orderId +
                ", CarWashServiceId=" + CarWashServiceId +
                ", count=" + count +
                ", additionPrice=" + additionPrice +
                '}';
    }
}
