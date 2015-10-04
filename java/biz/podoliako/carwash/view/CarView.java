package biz.podoliako.carwash.view;

import biz.podoliako.carwash.models.entity.Car;

public class CarView extends Car {
    private String brandName;
    private String categoryName;
    private String categotyDescription;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategotyDescription() {
        return categotyDescription;
    }

    public void setCategotyDescription(String categotyDescription) {
        this.categotyDescription = categotyDescription;
    }
}
