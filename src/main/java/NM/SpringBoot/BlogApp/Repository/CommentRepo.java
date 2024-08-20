package NM.SpringBoot.BlogApp.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.DAO.CommentDoa;

public interface CommentRepo extends JpaRepository<CommentDoa,UUID>{
    
}
