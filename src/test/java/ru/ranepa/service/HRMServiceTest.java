package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HRMServiceTest {

    private EmployeeRepository repository;
    private HRMService service;

    // Этот метод выполняется перед каждым тестом
    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
        service = new HRMService(repository);

        // Добавляем тестовых сотрудников
        service.addEmployee("Иван Иванов", "Developer", 100000, LocalDate.of(2020, 1, 15));
        service.addEmployee("Петр Петров", "Manager", 150000, LocalDate.of(2019, 5, 20));
        service.addEmployee("Сидор Сидоров", "QA", 80000, LocalDate.of(2021, 3, 10));
    }

    // Тест 1: Расчет средней зарплаты
    @Test
    void shouldCalculateAverageSalaryCorrectly() {
        // Ожидаемая средняя: (100000 + 150000 + 80000) / 3 = 110000
        double expected = 110000.0;
        double actual = service.getAverageSalary();

        assertEquals(expected, actual, 0.01, "Средняя зарплата рассчитана неверно");
    }

    // Тест 2: Поиск самого высокооплачиваемого сотрудника
    @Test
    void shouldFindTopSalaryEmployee() {
        Optional<Employee> topEmployee = service.getTopSalaryEmployee();

        assertTrue(topEmployee.isPresent(), "Сотрудник должен быть найден");
        assertEquals("Петр Петров", topEmployee.get().getName(), "Топ-сотрудник определен неверно");
        assertEquals(150000, topEmployee.get().getSalary(), "Зарплата топ-сотрудника не совпадает");
    }

    // Тест 3: Фильтрация по должности
    @Test
    void shouldFilterEmployeesByPosition() {
        List<Employee> developers = service.getEmployeesByPosition("Developer");

        assertEquals(1, developers.size(), "Должен быть 1 разработчик");
        assertEquals("Иван Иванов", developers.get(0).getName());
    }

    // Тест 4: Пустой список (проверка граничных случаев)
    @Test
    void shouldReturnZeroAverageForEmptyList() {
        // Очищаем репозиторий
        repository.clear();

        double average = service.getAverageSalary();
        assertEquals(0.0, average, "Для пустого списка средняя должна быть 0");

        Optional<Employee> topEmployee = service.getTopSalaryEmployee();
        assertTrue(topEmployee.isEmpty(), "Для пустого списка топ-сотрудник должен отсутствовать");
    }

    // Тест 5: Поиск по ID
    @Test
    void shouldFindEmployeeById() {
        // Сначала добавляем сотрудника и получаем его ID
        Employee newEmployee = service.addEmployee("Тестовый", "Tester", 50000, LocalDate.now());
        Long id = newEmployee.getId();

        Optional<Employee> found = service.findEmployeeById(id);
        assertTrue(found.isPresent(), "Сотрудник должен быть найден по ID");
        assertEquals("Тестовый", found.get().getName());

        // Поиск несуществующего ID
        Optional<Employee> notFound = service.findEmployeeById(999L);
        assertTrue(notFound.isEmpty(), "Несуществующий ID не должен возвращать сотрудника");
    }
}