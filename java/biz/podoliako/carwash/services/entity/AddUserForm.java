package biz.podoliako.carwash.services.entity;

import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.services.validation.BigDecimalString;
import biz.podoliako.carwash.services.validation.LoginExist;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class AddUserForm {

    @Length(min=1, max=25, message = "Длина имени должна быть от 1 до 25 символов")
    private String name;

    @Length(min=1, max=25, message = "Длина фамилии должна быть от 1 до 25 символов")
    private String surname;

    @BigDecimalString(message = "Не корректное число")
    @Length(min=1, max=25, message = "Длина должна быть от 1 до 25 символов")
    private String salary;

    @Length(min=1, max=25, message = "Длина должна быть от 1 до 25 символов")
    private String phoneNumber;

    @NotNull
    private Integer[] carWashId;

    private Role role;

    @NotNull
    private Integer dayCommission;

    @NotNull
    private Integer nightCommission;

    private Integer ownerId;

    private String login;

    private String password;

    private Integer createdBy;

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


    public Integer getNightCommission() {
        return nightCommission;
    }

    public void setNightCommission(Integer nightCommission) {
        this.nightCommission = nightCommission;
    }

    public Integer getDayCommission() {
        return dayCommission;
    }

    public void setDayCommission(Integer dayCommission) {
        this.dayCommission = dayCommission;
    }

    public Integer[] getCarWashId() {
        return carWashId;
    }

    public void setCarWashId(Integer[] carWashId) {
        this.carWashId = carWashId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "AddUserForm{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary='" + salary + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carWashId=" + Arrays.toString(carWashId) +
                ", role=" + role +
                ", dayCommission=" + dayCommission +
                ", nightCommission=" + nightCommission +
                ", ownerId=" + ownerId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}
