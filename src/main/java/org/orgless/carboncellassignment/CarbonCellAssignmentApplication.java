package org.orgless.carboncellassignment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

@SpringBootApplication
public class CarbonCellAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonCellAssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(@Qualifier("userDetailsManager") UserDetailsManager service) {
        return args -> {
            UserDetails admin = service.loadUserByUsername("admin@caRBONCELL.com");
            System.out.println("IN RUNNER");

            admin.getAuthorities()
                    .forEach(o -> {
                        System.out.println(o.getClass());
                        System.out.println(o);
                    });
        };
    }
}
