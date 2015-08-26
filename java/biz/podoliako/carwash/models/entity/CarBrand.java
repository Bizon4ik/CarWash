package biz.podoliako.carwash.models.entity;

import biz.podoliako.carwash.services.validation.CarBrandNotExist;
import biz.podoliako.carwash.services.validation.NotEmptyTrim;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


public class CarBrand {

    @NotEmptyTrim
    @Length(max = 30, message = "Не больше 30 символов")
    @CarBrandNotExist
    private String name;
    private Integer ownerId;
    private Integer createdBy;
    private Date dateOfcreation;
    private Date dateOfdelete;
    private Integer id;

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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateOfcreation() {
        return dateOfcreation;
    }

    public void setDateOfcreation(Date dateOfcreation) {
        this.dateOfcreation = dateOfcreation;
    }

    public Date getDateOfdelete() {
        return dateOfdelete;
    }

    public void setDateOfdelete(Date dateOfdelete) {
        this.dateOfdelete = dateOfdelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CarBrand{" +
                "name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", createdBy=" + createdBy +
                ", dateOfcreation=" + dateOfcreation +
                ", dateOfdelete=" + dateOfdelete +
                ", id=" + id +
                '}';
    }
}
