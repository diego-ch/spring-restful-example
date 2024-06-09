package dev.portodiego.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    Employee create(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> read(@PathVariable Long id) {
        Employee employee = repository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return EntityModel.of(
                employee,
                linkTo(methodOn(EmployeeController.class).read(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
        );
    }

    @PutMapping("/employees/{id}")
    Employee update(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return repository
                .findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> repository.save(newEmployee));
    }

    @DeleteMapping("/employees/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
