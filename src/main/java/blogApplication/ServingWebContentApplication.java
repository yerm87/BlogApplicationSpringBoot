package blogApplication;

import blogApplication.controllers.BlogController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import simpleObjects.Bird;

import java.io.File;

@ComponentScan(basePackages = {"blogApplication.repo", "blogApplication.models",
"blogApplication.controllers"})
@SpringBootApplication
public class ServingWebContentApplication {

    @Bean
    public Bird generateBird(){
        return new Bird(5, "Kanareyka");
    }

    public static void main(String[] args) {
        new File(BlogController.uploadDir).mkdir();
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}
