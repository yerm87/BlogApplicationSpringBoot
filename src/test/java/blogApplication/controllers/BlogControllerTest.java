package blogApplication.controllers;

import blogApplication.ServingWebContentApplication;
import blogApplication.models.Car;
import blogApplication.repo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value="/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("classpath:application-test.properties")
public class BlogControllerTest {
    @Autowired
    public CustomQueryRepository customRepository;

    @MockBean
    public PostRepository postRepository;

    @MockBean
    public MakerRepository makerRepository;

    @MockBean
    public ShopRepository shopRepository;

    @MockBean
    public CarRepository carRepository;

    @Before()
    public void setUp(){
        Car volkswagen = new Car();
        volkswagen.setName("Volkswagen");
        volkswagen.setAge(2010);

        Car mercedes = new Car();
        mercedes.setName("Mercedes");
        mercedes.setAge(2012);

        customRepository.save(volkswagen);
        customRepository.save(mercedes);
    }

    @Test
    public void testFetchCarData(){
        List<Car> cars = customRepository.fetchData("Volkswagen", 2008);
        Car car = cars.get(0);
        Assert.assertEquals(1, cars.size());
        Assert.assertEquals("Volkswagen", car.getName());
    }
}
