package dev.portodiego.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        // create demo list and save in the database
        return args -> {
            List<Employee> employees = List.of(
                new Employee("Bilbo Baggings", "Burglar"),
                new Employee("Frodo Baggings", "Thief")
            );

            employees.forEach(e -> log.info("preloading: " + repository.save(e)));
        };
    }
}
