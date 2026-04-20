package ru.ranepa;

import ru.ranepa.presentation.Menu;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class HrmApplication {
    public static void main(String[] args) {
        //кодировка вывода UTF-8
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(out);

        //инициализация слоев приложения
        EmployeeRepository repository = new EmployeeRepository();
        HRMService service = new HRMService(repository);
        Menu menu = new Menu(service);

        //запуск меню
        menu.start();
    }
}