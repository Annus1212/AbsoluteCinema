package com.absolutecinema.controller;

import com.absolutecinema.entity.Employee;
import com.absolutecinema.entity.Shift;
import com.absolutecinema.entity.User;
import com.absolutecinema.service.EmployeeService;
import com.absolutecinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserRepository userRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, UserRepository userRepository) {
        this.employeeService = employeeService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get the currently logged-in user's username from authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Find the user first to get their ID
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Find the employee by ID (which should match the user's ID)
        Employee employee = employeeService.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create salary data structure that matches the template
        Map<String, Object> currentMonthSalary = new HashMap<>();
        currentMonthSalary.put("month", "MAY");
        currentMonthSalary.put("year", "2024");
        currentMonthSalary.put("salary", employee.getSalary());

        // Add hardcoded demo shifts
        List<Map<String, String>> shifts = List.of(
                Map.of(
                        "date", "May 01, 2024",
                        "startTime", "09:00",
                        "endTime", "17:00",
                        "hours", "8.0"),
                Map.of(
                        "date", "May 02, 2024",
                        "startTime", "10:00",
                        "endTime", "18:00",
                        "hours", "8.0"));

        currentMonthSalary.put("shifts", shifts);

        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("currentMonthSalary", currentMonthSalary);

        return "employee/dashboard";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.createEmployee(employee);
        return "redirect:/employees/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", employee);
        return "employees/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/employees/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/dashboard";
    }
}