package biz.podoliako.carwash.view;


import java.math.BigDecimal;
import java.util.Date;

public class GeneralStatInBox {
    private Date from;
    private Date to;
    private Integer orderAmount;
    private BigDecimal moneyInCash = new BigDecimal(0);
    private BigDecimal moneyBeznal = new BigDecimal(0);
    private BigDecimal moneyTotal = new BigDecimal(0);
    private BigDecimal salary = new BigDecimal(0);
    private Integer box;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getMoneyInCash() {
        return moneyInCash;
    }

    public void setMoneyInCash(BigDecimal moneyInCash) {
        this.moneyInCash = moneyInCash;
    }

    public BigDecimal getMoneyBeznal() {
        return moneyBeznal;
    }

    public void setMoneyBeznal(BigDecimal moneyBeznal) {
        this.moneyBeznal = moneyBeznal;
    }

    public BigDecimal getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(BigDecimal moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getBox() {
        return box;
    }

    public void setBox(Integer box) {
        this.box = box;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralStatInBox that = (GeneralStatInBox) o;

        if (!box.equals(that.box)) return false;
        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + box.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GeneralStatInBox{" +
                "from=" + from +
                ", to=" + to +
                ", orderAmount=" + orderAmount +
                ", moneyInCash=" + moneyInCash +
                ", moneyBeznal=" + moneyBeznal +
                ", moneyTotal=" + moneyTotal +
                ", salary=" + salary +
                ", box=" + box +
                '}';
    }
}
