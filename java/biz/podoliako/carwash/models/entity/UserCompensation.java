package biz.podoliako.carwash.models.entity;

import java.math.BigDecimal;

public class UserCompensation {
    private BigDecimal salary;
    private Integer dayCommission;
    private Integer nightCommission;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

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
        return "UserCompensation{" +
                "salary=" + salary +
                ", dayCommission=" + dayCommission +
                ", nightCommission=" + nightCommission +
                '}';
    }
}
