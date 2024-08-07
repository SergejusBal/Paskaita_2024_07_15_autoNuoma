package com.example.Paskaita_2024_07_15_autoNuoma.Controllers;

import com.example.Paskaita_2024_07_15_autoNuoma.Models.User;
import com.example.Paskaita_2024_07_15_autoNuoma.Services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
@CrossOrigin(origins = {"*"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody User user) {

        String response = userService.registerUser(user);
        HttpStatus status = checkHttpStatus(response);

        if(status == HttpStatus.OK) return new ResponseEntity<>(true, status);
        else return new ResponseEntity<>(false, status);

    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestParam int id) {

        User userdata = userService.getUserById(id);

        if(userdata.getId() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(userdata, HttpStatus.OK);
    }



    @PostMapping("/authenticate")
    public ResponseEntity<String> login(@RequestBody User user) {

        String response = userService.login(user);
        HttpStatus status = checkHttpStatus(response);

        if(status == HttpStatus.OK) return new ResponseEntity<>(response, status);
        else return new ResponseEntity<>(response, status);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserId(@RequestParam String email,@RequestHeader("Authorization") String authorizationHeader) {

        String trimmedAuthorizationHeader = authorizationHeader.substring(7);

        String response = userService.getUserId(email, trimmedAuthorizationHeader);

        HttpStatus status = checkHttpStatus(response);

        if(status == HttpStatus.OK) return new ResponseEntity<>(response, status);
        else return new ResponseEntity<>(response, status);
    }


    @GetMapping("/user/autoLogin")
    public ResponseEntity<Boolean> userAutoLogIn(@RequestHeader("Authorization") String authorizationHeader) {

        String trimmedAuthorizationHeader = authorizationHeader.substring(7);

        boolean response = userService.userAutoLogIn(trimmedAuthorizationHeader);

        if(response) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<Boolean> registerUser(@RequestBody User user, @PathVariable Long id) {

        String response = userService.updateUser(user,id);
        HttpStatus status = checkHttpStatus(response);

        if(status == HttpStatus.OK) return new ResponseEntity<>(true, status);
        else return new ResponseEntity<>(false, status);

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
