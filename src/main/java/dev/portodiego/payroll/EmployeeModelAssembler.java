package dev.portodiego.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public EntityModel<Employee> toModel(Employee e) {
        return EntityModel.of(
                e,
                linkTo(methodOn(EmployeeController.class).read(e.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
        );
    }
}
