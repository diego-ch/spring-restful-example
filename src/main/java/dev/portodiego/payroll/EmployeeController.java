package dev.portodiego.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RestController
class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(e -> EntityModel.of(
                        e,
                        linkTo(methodOn(EmployeeController.class).read(e.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(
                employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
        );
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
