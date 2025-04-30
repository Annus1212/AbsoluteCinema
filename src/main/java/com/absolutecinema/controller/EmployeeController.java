package com.absolutecinema.controller;

import com.absolutecinema.entity.Employee;
import com.absolutecinema.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
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