package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.*;

public class EmployeeRepository {
    private Map<Long, Employee> employees = new HashMap<>();
    private Long nextId = 1L;  // Генератор ID

    //сохранение нового сотрудника (генерируем ID автоматически)
    public Employee save(Employee employee) {
        // Если у сотрудника нет ID, присваиваем новый
        if (employee.getId() == null) {
            employee.setId(nextId++);
        }
        employees.put(employee.getId(), employee);
        return employee;
    }

    //получение всех сотрудников
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    //поиск по ID (возвращаем Optional, чтобы не было null)
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    //удаление по ID
    public boolean delete(Long id) {
        return employees.remove(id) != null;
    }

    //проверка существования сотрудника
    public boolean existsById(Long id) {
        return employees.containsKey(id);
    }

    //очистка всех данных (нужно для тестов)
    public void clear() {
        employees.clear();
        nextId = 1L;
    }
}