package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.service.HRMService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final HRMService service;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Menu(HRMService service) {
        this.service = service;
    }

    //главный метод запуска меню
    public void start() {
        while (true) {
            printMainMenu();
            int choice = readIntInput("Выберите пункт: ");

            try {
                switch (choice) {
                    case 1:
                        showAllEmployees();
                        break;
                    case 2:
                        addEmployee();
                        break;
                    case 3:
                        deleteEmployee();
                        break;
                    case 4:
                        findEmployeeById();
                        break;
                    case 5:
                        showStatistics();
                        break;
                    case 6:
                        filterByPosition();
                        break;
                    case 7:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный пункт меню. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println("\nНажмите Enter для продолжения...");
            scanner.nextLine(); //очистка буфера
            scanner.nextLine(); //ожидание Enter
        }
    }

    //печать главного меню
    private void printMainMenu() {
        System.out.println("\n HRM System Menu ");
        System.out.println("1. Вывести список всех сотрудников");
        System.out.println("2. Добавить нового сотрудника");
        System.out.println("3. Удалить сотрудника по ID");
        System.out.println("4. Поиск сотрудника по ID");
        System.out.println("5. Показать статистику (средняя зарплата, топ-менеджер)");
        System.out.println("6. Фильтрация сотрудников по должности");
        System.out.println("7. Выход");
    }

    //показать всех сотрудников
    private void showAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }
        System.out.println("\n Список сотрудников");
        employees.forEach(System.out::println);
    }

    // Пункт 2: Добавить сотрудника
    private void addEmployee() {
        System.out.println("\n Добавление нового сотрудника ");

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите должность: ");
        String position = scanner.nextLine();

        double salary = readDoubleInput("Введите зарплату: ");

        LocalDate hireDate = readDateInput("Введите дату приема (день.месяц.год, например 15.03.2023): ");

        Employee newEmployee = service.addEmployee(name, position, salary, hireDate);
        System.out.printf("Сотрудник успешно добавлен с ID: %d%n", newEmployee.getId());
    }

    //удалить сотрудника
    private void deleteEmployee() {
        System.out.println("\nУдаление сотрудника");
        Long id = readLongInput("Введите ID сотрудника для удаления: ");

        if (service.deleteEmployee(id)) {
            System.out.println("Сотрудник с ID " + id + " успешно удален.");
        } else {
            System.out.println("Сотрудник с ID " + id + " не найден.");
        }
    }

    //поиск по ID
    private void findEmployeeById() {
        System.out.println("\n Поиск сотрудника");
        Long id = readLongInput("Введите ID сотрудника: ");

        Optional<Employee> employee = service.findEmployeeById(id);
        if (employee.isPresent()) {
            System.out.println("Найден сотрудник:");
            System.out.println(employee.get());
        } else {
            System.out.println("Сотрудник с ID " + id + " не найден.");
        }
    }

    //статистика
    private void showStatistics() {
        System.out.println("\n Статистика компании");

        double avgSalary = service.getAverageSalary();
        System.out.printf("Средняя зарплата по компании: %.2f%n", avgSalary);

        Optional<Employee> topEmployee = service.getTopSalaryEmployee();
        if (topEmployee.isPresent()) {
            System.out.println("Самый высокооплачиваемый сотрудник:");
            System.out.println(topEmployee.get());
        } else {
            System.out.println("Нет сотрудников для отображения статистики.");
        }
    }

    //вспомогательный метод для чтения целого числа
    private int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    //вспомогательный метод для чтения Long
    private Long readLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }
    }

    //вспомогательный метод для чтения double
    private double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число (например, 1500.50).");
            }
        }
    }

    //вспомогательный метод для чтения даты
    private LocalDate readDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine().trim();
                return LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: неверный формат даты. Используйте дд.мм.гггг (например, 15.03.2023).");
            }
        }
    }

    private void filterByPosition() {
        System.out.println("\nФильтрация сотрудников по должности");
        System.out.print("Введите должность для поиска: ");
        String position = scanner.nextLine();

        List<Employee> filtered = service.getEmployeesByPosition(position);

        if (filtered.isEmpty()) {
            System.out.println("Сотрудники с должностью '" + position + "' не найдены.");
        } else {
            System.out.println("\nНайдено сотрудников: " + filtered.size());
            filtered.forEach(System.out::println);
        }
    }
}