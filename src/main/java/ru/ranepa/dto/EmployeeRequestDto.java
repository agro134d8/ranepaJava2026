package ru.ranepa.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeRequestDto {

    @NotBlank(message = "Имя обязательно")
    private String name;

    @NotBlank(message = "Должность обязательна")
    private String position;

    @NotNull(message = "Зарплата обязательна")
    @Positive(message = "Зарплата должна быть положительной")
    @DecimalMin(value = "0.01", message = "Минимальная зарплата 0.01")
    private BigDecimal salary;

    @NotNull(message = "Дата приёма обязательна")
    private LocalDate hireDate;

    // Геттеры
    public String getName() { return name; }
    public String getPosition() { return position; }
    public BigDecimal getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }

    // Сеттеры
    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}