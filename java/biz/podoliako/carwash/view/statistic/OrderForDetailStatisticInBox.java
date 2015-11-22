package biz.podoliako.carwash.view.statistic;


import biz.podoliako.carwash.models.entity.Order;

import java.math.BigDecimal;

public class OrderForDetailStatisticInBox extends Order {
    private BigDecimal salary;
    private BigDecimal totaCostOfOrder;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getTotaCostOfOrder() {
        return totaCostOfOrder;
    }

    public void setTotaCostOfOrder(BigDecimal totaCostOfOrder) {
        this.totaCostOfOrder = totaCostOfOrder;
    }

    @Override
    public String toString() {
        return "OrderForDetailStatisticInBox{" +
                "salary=" + salary +
                ", totaCostOfOrder=" + totaCostOfOrder +
                '}';
    }
}
