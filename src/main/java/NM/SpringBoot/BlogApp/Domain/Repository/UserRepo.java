package NM.SpringBoot.BlogApp.Domain.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.Domain.DAO.UserDao;

public interface UserRepo extends JpaRepository<UserDao,Long> { 
    Optional<UserDao> findByUsername(String username);
    Optional<UserDao> findByEmail(String email);
}
