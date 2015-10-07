package biz.podoliako.carwash.services.entity;


import biz.podoliako.carwash.models.entity.WasherManInBox;

public class WasherManInBoxWithRate extends WasherManInBox{
    private Integer dayCommission;
    private Integer nightCommission;

    public Integer getDayCommission() {
        return dayCommission;
    }

    public void setDayCommission(Integer dayCommission) {
        this.dayCommission = dayCommission;
    }

    public Integer getNightCommission() {
        return nightCommission;
    }

    public void setNightCommission(Integer nightCommission) {
        this.nightCommission = nightCommission;
    }

    @Override
    public String toString() {
        return "WasherManInBoxWithRate{" +
                super.toString() +
                "dayCommission=" + dayCommission +
                ", nightCommission=" + nightCommission +
                '}';
    }
}
