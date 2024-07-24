package com.example.Paskaita_2024_07_15_autoNuoma.Services;

import com.example.Paskaita_2024_07_15_autoNuoma.Models.User;
import com.example.Paskaita_2024_07_15_autoNuoma.Repositories.UserRepository;
import com.example.Paskaita_2024_07_15_autoNuoma.Security.JwtDecoder;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public String registerUser(User user){
        return userRepository.registerUser(user);
    }

    public String updateUser(User user,Long id){
        return userRepository.updateUser(user,id);
    }

    public String login(User user){
        return userRepository.login(user);
    }

    public String getUserId(String username, String authorizationHeader){

        if(autorize(authorizationHeader)) return userRepository.getUserId(username);
        return "No authorization";
    }

    public boolean userAutoLogIn(String authorizationHeader){
        return autorize(authorizationHeader);
    }



    private boolean autorize(String authorizationHeader){

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return false;
        }
        try {
            JwtDecoder.decodeJwt(authorizationHeader);
        } catch (JwtException e){
            return false;
        }
        return true;
    }


}
