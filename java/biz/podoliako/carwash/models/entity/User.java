package biz.podoliako.carwash.models.entity;


import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class User {

    @Length(max=50, message = "Максимальная длина 50 символов")
    private Integer id;

    @Length(max=50, message = "Максимальная длина 50 символов")
    private String name;

    @Length(max=50, message = "Максимальная длина 50 символов")
    private String surname;

    @Length(max=50, message = "Максимальная длина 50 символов")
    private String phoneNumber;

    private Set<Integer> carWashPermissionSet;

    private UserCompensation compensation;

    private String login = null;

    private Role role;

    private Date dateOfCreation;

    private Date dateOfDelete;

    private Integer ownerId;

    private Integer createdBy;

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfDelete() {
        return dateOfDelete;
    }

    public void setDateOfDelete(Date dateOfDelete) {
        this.dateOfDelete = dateOfDelete;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Set<Integer> getCarWashPermissionSet() {
        return carWashPermissionSet;
    }

    public void setCarWashPermissionSet(Set<Integer> carWashPermissionSet) {
        this.carWashPermissionSet = carWashPermissionSet;
    }

    public UserCompensation getCompensation() {
        return compensation;
    }

    public void setCompensation(UserCompensation compensation) {
        this.compensation = compensation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carWashPermissionSet=" + carWashPermissionSet +
                ", compensation=" + compensation +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDelete=" + dateOfDelete +
                ", ownerId=" + ownerId +
                ", createdBy=" + createdBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }
}
