package NM.SpringBoot.BlogApp.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NM.SpringBoot.BlogApp.DAO.UserDao;
import NM.SpringBoot.BlogApp.DTO.UserDto;
import NM.SpringBoot.BlogApp.Exception.ResourceNotFound;
import NM.SpringBoot.BlogApp.Repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

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
    
}
