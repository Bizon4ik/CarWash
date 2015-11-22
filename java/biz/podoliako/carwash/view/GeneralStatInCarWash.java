package biz.podoliako.carwash.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class GeneralStatInCarWash {
    private Date from;
    private Date to;

    HashSet<GeneralStatInBox> generalStatInBoxes = new LinkedHashSet<>();

    private Integer totalOrderQuantity;
    private BigDecimal totalMoneyCash;
    private BigDecimal totalMoneyBeznal;
    private BigDecimal totalMoneyCarWash;
    private BigDecimal totalSalary;

    public void addGeneralStatInBox(GeneralStatInBox generalStatInBox){
        compareFrom(generalStatInBox);
        compareTo(generalStatInBox);
        if (!generalStatInBoxes.contains(generalStatInBox) && generalStatInBox.getOrderAmount()>0) {
            increaseTotalQuantity(generalStatInBox);
            increaseMoneyInCar(generalStatInBox);
            increaseMoneyBeznal(generalStatInBox);
            increaseMoneyTotal(generalStatInBox);
            incraseSalary(generalStatInBox);
        }
        generalStatInBoxes.add(generalStatInBox);


    }

    private void incraseSalary(GeneralStatInBox generalStatInBox) {
        if (totalSalary != null){
            totalSalary = totalSalary.add(generalStatInBox.getSalary());
        }else {
            totalSalary = generalStatInBox.getSalary();
        }
    }

    private void increaseMoneyTotal(GeneralStatInBox generalStatInBox) {
        if (totalMoneyCarWash != null){
            totalMoneyCarWash = totalMoneyCarWash.add(generalStatInBox.getMoneyTotal());
        }else {
            totalMoneyCarWash = generalStatInBox.getMoneyTotal();
        }
    }

    private void increaseMoneyBeznal(GeneralStatInBox generalStatInBox) {
        if (totalMoneyBeznal != null){
            totalMoneyBeznal = totalMoneyBeznal.add(generalStatInBox.getMoneyBeznal());
        }else {
            totalMoneyBeznal = generalStatInBox.getMoneyBeznal();
        }
    }

    private void increaseMoneyInCar(GeneralStatInBox generalStatInBox) {
        if (totalMoneyCash != null){
            totalMoneyCash = totalMoneyCash.add(generalStatInBox.getMoneyInCash());
        }else {
            totalMoneyCash = generalStatInBox.getMoneyInCash();
        }
    }

    private void increaseTotalQuantity(GeneralStatInBox generalStatInBox) {
        if (totalOrderQuantity != null){
            totalOrderQuantity = totalOrderQuantity + generalStatInBox.getOrderAmount();
        }else {
            totalOrderQuantity = generalStatInBox.getOrderAmount();
        }
    }

    private void compareTo(GeneralStatInBox generalStatInBox) {
        if (to != null) {
            if (!to.equals(generalStatInBox.getTo())) {
                throw new RuntimeException("Дата конца статистики не совпадает");
            }
        }else {
            to = generalStatInBox.getTo();
        }
    }

    private void compareFrom(GeneralStatInBox generalStatInBox) {
        if (from != null) {
            if (!from.equals(generalStatInBox.getFrom())) {
                System.out.println("from = " + from);
                System.out.println("injected date = " + generalStatInBox.getFrom());
                System.out.println("box = " + generalStatInBox.getBox());

                throw new RuntimeException("Дата начала статистики не совпадает");
            }
        }else {
            from = generalStatInBox.getFrom();
        }
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public HashSet<GeneralStatInBox> getGeneralStatInBoxes() {
        return generalStatInBoxes;
    }

    public Integer getTotalOrderQuantity() {
        return totalOrderQuantity;
    }

    public BigDecimal getTotalMoneyCash() {
        return totalMoneyCash;
    }

    public BigDecimal getTotalMoneyBeznal() {
        return totalMoneyBeznal;
    }

    public BigDecimal getTotalMoneyCarWash() {
        return totalMoneyCarWash;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    @Override
    public String toString() {
        return "GeneralStatInCarWash{" +
                "from=" + from +
                ", to=" + to +
                ", generalStatInBoxes=" + generalStatInBoxes +
                ", totalOrderQuantity=" + totalOrderQuantity +
                ", totalMoneyCash=" + totalMoneyCash +
                ", totalMoneyBeznal=" + totalMoneyBeznal +
                ", totalMoneyCarWash=" + totalMoneyCarWash +
                ", totalSalary=" + totalSalary +
                '}';
    }
}
