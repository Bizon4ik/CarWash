package biz.podoliako.carwash.models.entity;


import biz.podoliako.carwash.services.validation.CarNumber;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;

public class Car {

    private Integer id;

    @NotEmptyTrim
    @CarNumber
    private String number;

    private Integer categoryId;

    private Integer brandId;

    private Integer clientId;

    private Boolean isFinal;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public  Integer getBrandId() {
        return brandId;
    }

    public  void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }
}
