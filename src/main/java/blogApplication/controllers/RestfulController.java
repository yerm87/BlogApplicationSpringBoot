package blogApplication.controllers;

import blogApplication.models.Car;
import blogApplication.repo.CustomQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/json")
public class RestfulController {

    @Autowired
    public CustomQueryRepository carRepository;

    @GetMapping("/cars")
    public List<Car> getCars(){
        List<Car> cars = carRepository.fetchData("Volkswagen", 2008);
        return cars;
    }

    @GetMapping("/cars_by_param")
    public List<Car> carsByParam(){
        List<Car> cars = carRepository.getDataByParam("Ford");
        return cars;
    }
}
