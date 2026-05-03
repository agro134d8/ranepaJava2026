package ru.ranepa.dto;

import java.math.BigDecimal;

public class EmployeeStatsDto {
    private BigDecimal averageSalary;
    private EmployeeResponseDto topSalaryEmployee;
    private long totalEmployees;

    // Конструктор
    public EmployeeStatsDto(BigDecimal averageSalary, EmployeeResponseDto topSalaryEmployee, long totalEmployees) {
        this.averageSalary = averageSalary;
        this.topSalaryEmployee = topSalaryEmployee;
        this.totalEmployees = totalEmployees;
    }

    // Геттеры
    public BigDecimal getAverageSalary() { return averageSalary; }
    public EmployeeResponseDto getTopSalaryEmployee() { return topSalaryEmployee; }
    public long getTotalEmployees() { return totalEmployees; }

    // Сеттеры
    public void setAverageSalary(BigDecimal averageSalary) { this.averageSalary = averageSalary; }
    public void setTopSalaryEmployee(EmployeeResponseDto topSalaryEmployee) { this.topSalaryEmployee = topSalaryEmployee; }
    public void setTotalEmployees(long totalEmployees) { this.totalEmployees = totalEmployees; }
}