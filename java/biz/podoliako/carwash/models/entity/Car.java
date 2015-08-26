package biz.podoliako.carwash.models.entity;


import biz.podoliako.carwash.services.validation.CarNumber;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;

public class Car {

    @NotEmptyTrim
    @CarNumber
    private String number;

    private Integer categoryId;

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
}
