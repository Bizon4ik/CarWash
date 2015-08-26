package biz.podoliako.carwash.models.entity;


import biz.podoliako.carwash.services.validation.ServiceNameNotExist;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class ServiceName {
    private Integer id;

    @Length(min=1, max=300, message = "Длина имени должна быть от 1 до 300 символов")
    @ServiceNameNotExist
    private String name;

    private Integer ownerId;
    private Date dateOfCreation;
    private Date dateOfDelete;
    private Integer createdBy;
    private boolean countable;
    private boolean additionPrice;

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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    public boolean isAdditionPrice() {
        return additionPrice;
    }

    public void setAdditionPrice(boolean additionPrice) {
        this.additionPrice = additionPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceName serviceName = (ServiceName) o;

        if (id != null ? !id.equals(serviceName.id) : serviceName.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ServiceName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDelete=" + dateOfDelete +
                ", createdBy=" + createdBy +
                ", countable=" + countable +
                ", additionPrice=" + additionPrice +
                '}';
    }
}
