package biz.podoliako.carwash.view;


import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.models.entity.CarWashService;
import biz.podoliako.carwash.models.entity.Category;

import java.util.List;

public class OrderFormData {
    private Category category;
    private List<CarBrand> carBrandList;
    private List<Integer> availableBoxList;
    private List<CarWashService> carWashServices;
    private String carBrandName = "N/A";


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CarBrand> getCarBrandList() {
        return carBrandList;
    }

    public void setCarBrandList(List<CarBrand> carBrandList) {
        this.carBrandList = carBrandList;
    }

    public List<Integer> getAvailableBoxList() {
        return availableBoxList;
    }

    public void setAvailableBoxList(List<Integer> availableBoxList) {
        this.availableBoxList = availableBoxList;
    }

    public List<CarWashService> getCarWashServices() {
        return carWashServices;
    }

    public void setCarWashServices(List<CarWashService> carWashServices) {
        this.carWashServices = carWashServices;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }
}
