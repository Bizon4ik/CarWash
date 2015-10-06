package biz.podoliako.carwash.services.entity;


import biz.podoliako.carwash.services.validation.NotEmptyTrim;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CalendarPeriod {

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Поле не может быть пустым")
    Date fromDate;

    @NotNull
    Integer fromHour;

    @NotNull
    Integer fromMinute;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Поле не может быть пустым")
    Date toDate;

    @NotNull
    Integer toHour;

    @NotNull
    Integer toMinute;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getFromHour() {
        return fromHour;
    }

    public void setFromHour(Integer fromHour) {
        this.fromHour = fromHour;
    }

    public Integer getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(Integer fromMinute) {
        this.fromMinute = fromMinute;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getToHour() {
        return toHour;
    }

    public void setToHour(Integer toHour) {
        this.toHour = toHour;
    }

    public Integer getToMinute() {
        return toMinute;
    }

    public void setToMinute(Integer toMinute) {
        this.toMinute = toMinute;
    }

    @Override
    public String toString() {
        return "CalendarPeriod{" +
                "fromDate=" + fromDate +
                ", fromHour=" + fromHour +
                ", fromMinute=" + fromMinute +
                ", toDate=" + toDate +
                ", toHour=" + toHour +
                ", toMinute=" + toMinute +
                '}';
    }
}
