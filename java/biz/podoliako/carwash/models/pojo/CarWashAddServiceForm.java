package biz.podoliako.carwash.models.pojo;

import java.util.Arrays;

public class CarWashAddServiceForm {
    private String carwashid;
    private String categoryid;
    private String[] serviceNameIdList;
    private String[] priceList;
    private String[] dayCommissionList;
    private String[] nightCommissionList;

    public CarWashAddServiceForm() {
    }

    public String getCarwashid() {
        return carwashid;
    }

    public void setCarwashid(String carwashid) {
        this.carwashid = carwashid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String[] getServiceNameIdList() {
        return serviceNameIdList;
    }

    public void setServiceNameIdList(String[] serviceNameIdList) {
        this.serviceNameIdList = serviceNameIdList;
    }

    public String[] getPriceList() {
        return priceList;
    }

    public void setPriceList(String[] priceList) {
        this.priceList = priceList;
    }

    public String[] getDayCommissionList() {
        return dayCommissionList;
    }

    public void setDayCommissionList(String[] dayCommissionList) {
        this.dayCommissionList = dayCommissionList;
    }

    public String[] getNightCommissionList() {
        return nightCommissionList;
    }

    public void setNightCommissionList(String[] nightCommissionList) {
        this.nightCommissionList = nightCommissionList;
    }

    @Override
    public String toString() {
        return "CarWashAddServiceForm{" +
                "carwashid='" + carwashid + '\'' +
                ", categoryid='" + categoryid + '\'' +
                ", serviceNameIdList=" + Arrays.toString(serviceNameIdList) +
                ", priceList=" + Arrays.toString(priceList) +
                ", dayCommissionList=" + Arrays.toString(dayCommissionList) +
                ", nightCommissionList=" + Arrays.toString(nightCommissionList) +
                '}';
    }
}
