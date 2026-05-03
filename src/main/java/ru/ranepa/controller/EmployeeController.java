package ru.ranepa.controller;

import ru.ranepa.dto.*;
import ru.ranepa.model.Employee;
import ru.ranepa.service.HRMService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final HRMService service;

    @Autowired
    public EmployeeController(HRMService service) {
        this.service = service;
    }

    // GET /api/employees — все сотрудники
    @GetMapping
    public List<EmployeeResponseDto> getAllEmployees() {
        return service.getAllEmployees().stream()
                .map(EmployeeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /api/employees/{id} — сотрудник по ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        Employee employee = service.findEmployeeById(id);
        return ResponseEntity.ok(EmployeeResponseDto.fromEntity(employee));
    }

    // POST /api/employees — создать сотрудника
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDto createEmployee(@Valid @RequestBody EmployeeRequestDto request) {
        Employee employee = new Employee(
                request.getName(),
                request.getPosition(),
                request.getSalary(),
                request.getHireDate()
        );
        Employee saved = service.addEmployee(employee);
        return EmployeeResponseDto.fromEntity(saved);
    }

    // DELETE /api/employees/{id} — удалить
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = service.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/employees/position/{position} — по должности
    @GetMapping("/position/{position}")
    public List<EmployeeResponseDto> getEmployeesByPosition(@PathVariable String position) {
        return service.getEmployeesByPosition(position).stream()
                .map(EmployeeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /api/employees/stats — статистика
    @GetMapping("/stats")
    public EmployeeStatsDto getStats() {
        BigDecimal avgSalary = service.getAverageSalary();
        Employee topEmployee = service.getTopSalaryEmployee();
        long total = service.getTotalEmployees();

        EmployeeResponseDto topDto = topEmployee != null
                ? EmployeeResponseDto.fromEntity(topEmployee)
                : null;

        return new EmployeeStatsDto(avgSalary, topDto, total);
    }
}