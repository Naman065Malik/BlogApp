package NM.SpringBoot.BlogApp.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NM.SpringBoot.BlogApp.Domain.DTO.UserDto;
import NM.SpringBoot.BlogApp.API.Request.LoginRequest;
import NM.SpringBoot.BlogApp.API.Response.AuthResponse;
import NM.SpringBoot.BlogApp.Domain.DAO.UserDao;
import NM.SpringBoot.BlogApp.Domain.Service.UserService;
import NM.SpringBoot.BlogApp.Utils.JWT;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWT jwt;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        UserDao user = userService.getUserByUsername(request.getUsername());
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwt.generateToken(user.getUsername(), 100000));
        authResponse.setRefreshToken(jwt.generateToken(user.getUsername(), 10000000));
        authResponse.setUsername(user.getUsername());
        authResponse.setSuccess(true);
        return authResponse;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Validated UserDto userDto) {
        UserDao userDao = userService.createUser(userDto);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwt.generateToken(userDao.getUsername(), 100000));
        authResponse.setRefreshToken(jwt.generateToken(userDao.getUsername(), 10000000));
        authResponse.setUsername(userDao.getUsername());
        authResponse.setSuccess(true);
        return authResponse;
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody String refreshToken) {
        String username = jwt.getUsername(refreshToken);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwt.generateToken(username, 100000));
        authResponse.setRefreshToken(jwt.generateToken(username, 10000000));
        authResponse.setUsername(username);
        authResponse.setSuccess(true);
        return authResponse;
    }
    
}
