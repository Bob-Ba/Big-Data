package au.edu.utas.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @SpringBootApplication is the sign that this application is a Spring Boot application
 * Spring boot scan the top package that the SpringBootApplication class exists and all subpackages here
 * scanBasePackages feature can help scan the packages that you point out
 */
@SpringBootApplication(scanBasePackages = {"au.edu.utas"})
public class SpringBootMainApplication {

    public static void main(String[] args){

        //the entrance that spring boot starts
        SpringApplication.run(SpringBootMainApplication.class, args);
    }
}

