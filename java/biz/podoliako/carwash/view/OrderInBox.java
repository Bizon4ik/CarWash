package biz.podoliako.carwash.view;


import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.models.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class OrderInBox {


    private Order order;
    private CarViewWithClient carViewWithClient;
    private List<OrderedService> orderedServices;
    private BigDecimal totalPrice;
    private Integer discount;
    private BigDecimal action;
    private BigDecimal finalPrice;

    private  Set<WasherManInBoxWithName> washerManSet;

    public Set<WasherManInBoxWithName> getWasherManSet() {
        return washerManSet;
    }

    public void setWasherManSet(Set<WasherManInBoxWithName> washerManSet) {
        this.washerManSet = washerManSet;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public CarViewWithClient getCarViewWithClient() {
        return carViewWithClient;
    }

    public void setCarViewWithClient(CarViewWithClient carViewWithClient) {
        this.carViewWithClient = carViewWithClient;
    }

    public List<OrderedService> getOrderedServices() {
        return orderedServices;
    }

    public void setOrderedServices(List<OrderedService> orderedServices) {
        this.orderedServices = orderedServices;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getAction() {
        return action;
    }

    public void setAction(BigDecimal action) {
        this.action = action;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

}
