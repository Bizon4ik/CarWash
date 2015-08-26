package biz.podoliako.carwash.models.entity;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Administrator  {

    @Length(min=1, max=25, message = "Длина имени должна быть от 1 до 25 символов")
    private String name;

    @Length(min=1, max=25, message = "Длина фамилии должна быть от 1 до 25 символов")
    private String surname;

    @Length(min=1, max=25, message = "Длина должна быть от 1 до 25 символов")
    private String salary;

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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
