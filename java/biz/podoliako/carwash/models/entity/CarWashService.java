package biz.podoliako.carwash.models.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CarWashService {
    private Integer id;
    private CarWash carWash;
    private Category category;
    private ServiceName serviceName;
    private BigDecimal price;
    private Integer commisionDay;
    private Integer commisionNight;
    private Date dateOfCreation;
    private Date dateOfDelate;
    private Integer ownerId;

    @Override
    public String toString() {
        return "CarWashService{" +
                "id=" + id +
                ", carWash=" + carWash +
                ", category=" + category +
                ", serviceName=" + serviceName +
                ", price=" + price +
                ", commisionDay=" + commisionDay +
                ", getCommisionNight=" + commisionNight +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDelate=" + dateOfDelate +
                ", ownerId=" + ownerId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CarWash getCarWash() {
        return carWash;
    }

    public void setCarWash(CarWash carWash) {
        this.carWash = carWash;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCommisionDay() {
        return commisionDay;
    }

    public void setCommisionDay(Integer commisionDay) {
        this.commisionDay = commisionDay;
    }

    public Integer getCommisionNight() {
        return commisionNight;
    }

    public void setCommisionNight(Integer getCommisionNight) {
        this.commisionNight = getCommisionNight;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfDelate() {
        return dateOfDelate;
    }

    public void setDateOfDelate(Date dateOfDelate) {
        this.dateOfDelate = dateOfDelate;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }


}
