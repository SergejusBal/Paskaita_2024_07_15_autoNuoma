package com.example.Paskaita_2024_07_15_autoNuoma.Controllers;

import com.example.Paskaita_2024_07_15_autoNuoma.Models.Car;
import com.example.Paskaita_2024_07_15_autoNuoma.Models.Filter;
import com.example.Paskaita_2024_07_15_autoNuoma.Models.User;
import com.example.Paskaita_2024_07_15_autoNuoma.Services.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
@CrossOrigin(origins = {"*"})
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @PostMapping("/car/new")
    public ResponseEntity<Boolean> registerCar(@RequestBody Car car,@RequestHeader("Authorization") String authorizationHeader) {

        String trimmedAuthorizationHeader = authorizationHeader.substring(7);

        String response = carService.resgisterCar(car,trimmedAuthorizationHeader);
        HttpStatus status = checkHttpStatus(response);

        if(status == HttpStatus.OK) return new ResponseEntity<>(true, status);
        else return new ResponseEntity<>(false, status);
    }

    @PutMapping("/car/alter")
    public ResponseEntity<Boolean> alterCar(@RequestBody Car car,@RequestHeader("Authorization") String authorizationHeader, @RequestParam int id) {

        String trimmedAuthorizationHeader = authorizationHeader.substring(7);

        String response = carService.alterCar(car,trimmedAuthorizationHeader, id);
        HttpStatus status = checkHttpStatus(response);

        if(status == HttpStatus.OK) return new ResponseEntity<>(true, status);
        else return new ResponseEntity<>(false, status);
    }




    @GetMapping("/car/c")
    public ResponseEntity<List<Car>> getNewCars(@RequestParam int offset , @RequestParam int limit) {

        List<Car> carList = carService.getNewestCars(offset, limit);

        if(carList.isEmpty()) return new ResponseEntity<>(carList, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @PostMapping("/car/f")
    public ResponseEntity<List<Car>> getFilteredCars(@RequestParam int offset , @RequestParam  int limit, @RequestBody Filter filter) {

        List<Car> carList = carService.getFilteredCars(offset, limit, filter);

        if(carList.isEmpty()) return new ResponseEntity<>(carList, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(carList, HttpStatus.OK);
    }



    @GetMapping("/car")
    public ResponseEntity<Car> getCarById(@RequestParam int id) {

        Car car = carService.getCarById(id);
        if (car.getId() != 0) return new ResponseEntity<>(car, HttpStatus.OK);
        else return new ResponseEntity<>(car, HttpStatus.NO_CONTENT);

    }

    @GetMapping("/car/make")
    public ResponseEntity<List<String>> getAllMakes() {

        List<String> makeList = carService.getAllMakes();

        if (makeList.isEmpty()) return new ResponseEntity<>(makeList, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(makeList, HttpStatus.OK);
    }

    @PostMapping("/car/price")
    public ResponseEntity<Long> getCarById(@RequestParam String price, @RequestBody Filter filter) {

        long priceResponse = carService.getMinMAx(filter.getMake(), filter.getModel(), price);

        if (priceResponse > 0) return new ResponseEntity<>(priceResponse, HttpStatus.OK);
        else return new ResponseEntity<>(priceResponse, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/car/model")
    public ResponseEntity<List<String>> getAllMakes(@RequestBody Filter filter) {

        List<String> makeList = carService.getAllModelsByMake(filter.getMake());

        if (makeList.isEmpty()) return new ResponseEntity<>(makeList, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(makeList, HttpStatus.OK);
    }


    @DeleteMapping("/car")
    public ResponseEntity<String> deleteCarById(@RequestParam int id, @RequestHeader("Authorization") String authorizationHeader) {

        String trimmedAuthorizationHeader = authorizationHeader.substring(7);

        String response = carService.deleteCarById(id, trimmedAuthorizationHeader);
        HttpStatus status = checkHttpStatus(response);

        if (status == HttpStatus.OK) return new ResponseEntity<>(response, status);
        else return new ResponseEntity<>(response, status);
    }



    private HttpStatus checkHttpStatus(String response){

        switch (response){
            case "Invalid username or password", "No authorization":
                return HttpStatus.UNAUTHORIZED;
            case "Database connection failed":
                return HttpStatus.INTERNAL_SERVER_ERROR;
            case "User already exists":
                return HttpStatus.CONFLICT;
            case "Invalid data":
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.OK;
        }

    }



}
