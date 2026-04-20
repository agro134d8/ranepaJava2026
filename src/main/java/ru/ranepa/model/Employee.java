package ru.ranepa.model;

import java.time.LocalDate;

public class Employee {
    private Long id;
    private String name;
    private String position;
    private double salary;
    private LocalDate hireDate;

    //конструктор без id
    public Employee(String name, String position, double salary, LocalDate hireDate) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    //полный конструктор для восстановления из хранилища
    public Employee(Long id, String name, String position, double salary, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    //геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Имя: %s | Должность: %s | Зарплата: %.2f | Дата приема: %s",
                id, name, position, salary, hireDate);
    }
}