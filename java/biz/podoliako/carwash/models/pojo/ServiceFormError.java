package biz.podoliako.carwash.models.pojo;

import java.util.Arrays;


public class ServiceFormError {
    private String[] priceErrors;
    private String[] nameExistErros;
    private boolean hasErrors = false;

    public String[] getPriceErrors() {
        return priceErrors;
    }

    public void setPriceErrors(String[] price) {
        if (hasErrors == false) {
            hasErrors = true;
        }
        this.priceErrors = price;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public String[] getNameExistErros() {
        return nameExistErros;
    }

    public void setNameExistErros(String[] nameExistErros) {
        if (hasErrors == false) {
            hasErrors = true;
        }
        this.nameExistErros = nameExistErros;
    }

    @Override
    public String toString() {
        return "ServiceFormError{" +
                "priceErrors=" + Arrays.toString(priceErrors) +
                ", nameExistErros=" + Arrays.toString(nameExistErros) +
                ", hasErrors=" + hasErrors +
                '}';
    }
}

