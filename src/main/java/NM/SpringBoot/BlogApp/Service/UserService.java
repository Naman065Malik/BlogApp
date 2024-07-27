package NM.SpringBoot.BlogApp.Service;

import java.util.List;

import NM.SpringBoot.BlogApp.DAO.UserDao;
import NM.SpringBoot.BlogApp.DTO.UserDto;

public interface UserService {
    public List<UserDao> getAllUsers();
    public UserDao getUserById(long id);
    public UserDao getUserByUsername(String username);
    public UserDao getUserByEmail(String email);
    public UserDao createUser(UserDto userDto);
    public UserDao updateUser(long id, UserDto userDto);
    public boolean deleteUser(long id);
}
