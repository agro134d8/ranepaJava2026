package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HRMService {
    private EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Получение всех сотрудников (возвращаем список)
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Поиск сотрудника по ID
    public Optional<Employee> findEmployeeById(Long id) {
        return repository.findById(id);
    }

    // Добавление нового сотрудника
    public Employee addEmployee(String name, String position, double salary, java.time.LocalDate hireDate) {
        Employee employee = new Employee(name, position, salary, hireDate);
        return repository.save(employee);
    }

    // Удаление сотрудника
    public boolean deleteEmployee(Long id) {
        return repository.delete(id);
    }

    // Расчет средней зарплаты (возвращаем 0, если сотрудников нет)
    public double getAverageSalary() {
        return repository.findAll()
                .stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    // Поиск самого высокооплачиваемого сотрудника
    public Optional<Employee> getTopSalaryEmployee() {
        return repository.findAll()
                .stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }

    // Фильтрация сотрудников по должности
    public List<Employee> getEmployeesByPosition(String position) {
        return repository.findAll()
                .stream()
                .filter(e -> e.getPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
    }
}