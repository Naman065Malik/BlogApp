package NM.SpringBoot.BlogApp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.DAO.UserDao;

public interface UserRepo extends JpaRepository<UserDao,Long> { 
    Optional<UserDao> findByUsername(String username);
    Optional<UserDao> findByEmail(String email);
}
