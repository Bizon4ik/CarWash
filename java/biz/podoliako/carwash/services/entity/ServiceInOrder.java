package biz.podoliako.carwash.services.entity;


public class ServiceInOrder {
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

    @Override
    public String toString() {
        return "ServiceInOrder{" +
                "CarWashServiceId=" + CarWashServiceId +
                ", count=" + count +
                ", additionPrice=" + additionPrice +
                '}';
    }
}
