package biz.podoliako.carwash;


public class test {

    private Integer data = 1;

    public class abc {

        public Integer data;

        public abc(Integer data) {
            this.data = data;
            test.this.data = data;

        }
    }

}