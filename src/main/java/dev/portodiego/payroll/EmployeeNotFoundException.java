package dev.portodiego.payroll;

class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id) {
        super("could not find employee with id=" + id);
    }
}
