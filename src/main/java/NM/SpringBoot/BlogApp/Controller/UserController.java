package NM.SpringBoot.BlogApp.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import NM.SpringBoot.BlogApp.DTO.UserDto;
import NM.SpringBoot.BlogApp.Service.UserService;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<UserDto> response = userService.getAllUsers().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return response;
    }

    @GetMapping("/user/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return modelMapper.map(userService.getUserById(id), UserDto.class);
    }

    @GetMapping("/user/username/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        return modelMapper.map(userService.getUserByUsername(username), UserDto.class);
    }

    @GetMapping("/user/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return modelMapper.map(userService.getUserByEmail(email), UserDto.class);
    }

    @PostMapping("/user")
    public UserDto createUser(@RequestBody @Validated UserDto userDto) {
        return modelMapper.map(userService.createUser(userDto), UserDto.class);
    }

    @PutMapping("/user/{id}")
    public UserDto updateUser(@PathVariable long id,@RequestBody @Validated UserDto userDto) {
        return modelMapper.map(userService.updateUser(id, userDto), UserDto.class);
    }

    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

}
