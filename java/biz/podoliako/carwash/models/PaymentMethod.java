package biz.podoliako.carwash.models;


public enum PaymentMethod {
    Cash("Наличный"), Beznal("Безналичный");

    private String lable;

     PaymentMethod(String label) {
         this.lable = label;

    }

    public String getLable() {
        return lable;
    }
}
