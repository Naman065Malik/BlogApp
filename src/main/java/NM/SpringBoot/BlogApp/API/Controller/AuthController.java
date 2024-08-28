package NM.SpringBoot.BlogApp.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NM.SpringBoot.BlogApp.Domain.DTO.UserDto;
import NM.SpringBoot.BlogApp.API.Exception.InvalidPassword;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    private JWT jwt;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        UserDao user = userService.getUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPassword("Invalid Password for User: " + user.getUsername());
        }
        AuthResponse authResponse = new AuthResponse();
        // 1 Hour Access Token
        authResponse.setAccessToken(jwt.generateToken(user.getUsername(), 3600000 ));
        // 15 Days Refresh Token
        authResponse.setRefreshToken(jwt.generateToken(user.getUsername(), 1296000000));
        authResponse.setUsername(user.getUsername());
        authResponse.setSuccess(true);
        return authResponse;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Validated UserDto userDto) {
        // Encoding Password Before Creating User
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDao userDao = userService.createUser(userDto);
        AuthResponse authResponse = new AuthResponse();
        // 1 Hour Access Token
        authResponse.setAccessToken(jwt.generateToken(userDao.getUsername(), 3600000 ));
        // 15 Days Refresh Token
        authResponse.setRefreshToken(jwt.generateToken(userDao.getUsername(), 1296000000));
        authResponse.setUsername(userDao.getUsername());
        authResponse.setSuccess(true);
        return authResponse;
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody String refreshToken) {
        String username = jwt.getUsername(refreshToken);
        AuthResponse authResponse = new AuthResponse();
        // 1 Hour Access Token
        authResponse.setAccessToken(jwt.generateToken(username, 3600000 ));
        authResponse.setUsername(username);
        authResponse.setSuccess(true);
        return authResponse;
    }
    
}
