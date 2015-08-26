package biz.podoliako.carwash.services.entity;

import biz.podoliako.carwash.services.validation.LoginExist;
import org.hibernate.validator.constraints.Length;

public class AddAuthorizedUserForm extends AddUserForm  {
    @Length(min=1, max=25, message = "Длина должна быть от 1 до 25 символов")
    @LoginExist
    private String login;

    @Length(min=5, max=25, message = "Длина должна быть от 5 до 25 символов")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
