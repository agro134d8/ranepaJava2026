package ru.ranepa.dto;

import ru.ranepa.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    // Преобразование из Employee в DTO
    public static EmployeeResponseDto fromEntity(Employee employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setSalary(employee.getSalary());
        dto.setHireDate(employee.getHireDate());
        return dto;
    }

    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public BigDecimal getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }

    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}