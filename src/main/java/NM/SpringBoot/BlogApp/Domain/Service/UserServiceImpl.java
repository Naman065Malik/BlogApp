package NM.SpringBoot.BlogApp.Domain.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import NM.SpringBoot.BlogApp.Domain.DTO.UserDto;
import NM.SpringBoot.BlogApp.API.Exception.ResourceNotFound;
import NM.SpringBoot.BlogApp.Domain.DAO.UserDao;
import NM.SpringBoot.BlogApp.Domain.Repository.UserRepo;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDao> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDao getUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
    }

    @Override
    public UserDao getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFound("User not found with username: " + username));
    }

    @Override
    public UserDao getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFound("User not found with email: " + email));
    }

    @Override
    public UserDao createUser(UserDto userDto) {
        UserDao userDao = modelMapper.map(userDto, UserDao.class);
        userDao.setCreatedAt(LocalDateTime.now());
        userDao.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(userDao);
    }

    @Override
    public UserDao updateUser(long id, UserDto userDto) {
        UserDao userDao = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
        modelMapper.map(userDto, userDao);
        userDao.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(userDao);
    }

    @Override
    public boolean deleteUser(long id) {
        UserDao userDao = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
        userRepo.delete(userDao);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = this.getUserByUsername(username);

        return new User(user.getUsername(),user.getPassword(), new ArrayList<>());
    }
    
}
