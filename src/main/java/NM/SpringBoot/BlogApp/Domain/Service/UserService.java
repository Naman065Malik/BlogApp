package NM.SpringBoot.BlogApp.Domain.Service;

import java.util.List;

import NM.SpringBoot.BlogApp.Domain.DTO.UserDto;
import NM.SpringBoot.BlogApp.Domain.DAO.UserDao;

public interface UserService {
    public List<UserDao> getAllUsers();
    public UserDao getUserById(long id);
    public UserDao getUserByUsername(String username);
    public UserDao getUserByEmail(String email);
    public UserDao createUser(UserDto userDto);
    public UserDao updateUser(long id, UserDto userDto);
    public boolean deleteUser(long id);
}
