package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class HRMService {

    private final EmployeeRepository repository;

    @Autowired  // Внедрение зависимости через конструктор
    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден с ID: " + id));
    }

    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    public boolean deleteEmployee(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public BigDecimal getAverageSalary() {
        return repository.findAll().stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(Math.max(1, repository.count())), 2, BigDecimal.ROUND_HALF_UP);
    }

    public Employee getTopSalaryEmployee() {
        return repository.findAll().stream()
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()))
                .orElse(null);
    }

    public List<Employee> getEmployeesByPosition(String position) {
        return repository.findByPosition(position);
    }

    public List<Employee> getEmployeesBySalaryGreaterThan(BigDecimal salary) {
        return repository.findBySalaryGreaterThanEqual(salary);
    }

    public long getTotalEmployees() {
        return repository.count();
    }
}