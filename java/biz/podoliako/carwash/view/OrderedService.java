package biz.podoliako.carwash.view;

import java.math.BigDecimal;

public class OrderedService {
    private String name;
    private BigDecimal price;
    private Integer count;
    private BigDecimal additionPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getAdditionPrice() {
        return additionPrice;
    }

    public void setAdditionPrice(BigDecimal additionPrice) {
        this.additionPrice = additionPrice;
    }

    @Override
    public String toString() {
        return "OrderedService{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", additionPrice=" + additionPrice +
                '}';
    }
}
