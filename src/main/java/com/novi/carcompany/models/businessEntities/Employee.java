package com.novi.carcompany.models.businessEntities;

import com.novi.carcompany.models.security.User;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "employees")
public class Employee extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String function;
    @OneToOne
    private User employee_user;


    public Employee() {
    }

    public Employee(Long id, String function) {
        this.id = id;
        this.function = function;
    }


    public Long getId() {
        return this.id;
    }

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public User getEmployee_user() {
        return employee_user;
    }

    public void setEmployee_user(User employee_user) {
        this.employee_user = employee_user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(id, employee.id) && Objects.equals(function, employee.function) && Objects.equals(employee_user, employee.employee_user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, function, employee_user);
    }
}
