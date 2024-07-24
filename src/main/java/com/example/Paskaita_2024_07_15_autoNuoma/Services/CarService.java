package com.example.Paskaita_2024_07_15_autoNuoma.Services;

import com.example.Paskaita_2024_07_15_autoNuoma.Models.Car;
import com.example.Paskaita_2024_07_15_autoNuoma.Models.Filter;
import com.example.Paskaita_2024_07_15_autoNuoma.Repositories.CarRepository;
import com.example.Paskaita_2024_07_15_autoNuoma.Security.JwtDecoder;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public String resgisterCar(Car car, String authorizationHeader){

        if(autorize(authorizationHeader)) return carRepository.registerCar(car);
        return "No authorization";

    }

    public String alterCar(Car car, String authorizationHeader, int id){

        if(autorize(authorizationHeader)) return carRepository.alterCar(car, id);
        return "No authorization";

    }

    public List<Car> getNewestCars(int offset, int limit){
        return carRepository.getNewestCars(offset, limit);
    }

    public List<Car> getFilteredCars(int offset, int limit, Filter filter){
        return carRepository.getFilteredCars(offset, limit, filter);
    }

    public Car getCarById(int id){
        return carRepository.getCarById(id);
    }

    public String deleteCarById(int id, String authorizationHeader){

        if(autorize(authorizationHeader)) return carRepository.deleteCarById(id);

        return "No authorization";
    }

    public long getMinMAx(String make, String model, String parameter){
        return carRepository.getPrice(make, model, parameter);
    }

    public List<String> getAllMakes(){
        return carRepository.getAllMakes();
    }

    public List<String> getAllModelsByMake(String model){
        return carRepository.getAllModelsByMake(model);
    }

    private boolean autorize(String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return false;
        }
        try {
            JwtDecoder.decodeJwt(authorizationHeader);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
}




