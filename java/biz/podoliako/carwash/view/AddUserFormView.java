package biz.podoliako.carwash.view;

import biz.podoliako.carwash.models.entity.CarWash;

import java.util.List;

public class AddUserFormView {
    private String title;
    private List<CarWash> carWashList;
    private boolean logPassPart;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CarWash> getCarWashList() {
        return carWashList;
    }

    public void setCarWashList(List<CarWash> carWashList) {
        this.carWashList = carWashList;
    }

    public boolean isLogPassPart() {
        return logPassPart;
    }

    public void setLogPassPart(boolean logPassPart) {
        this.logPassPart = logPassPart;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
