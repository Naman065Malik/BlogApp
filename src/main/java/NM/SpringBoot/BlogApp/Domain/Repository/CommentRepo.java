package NM.SpringBoot.BlogApp.Domain.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.Domain.DAO.CommentDoa;

public interface CommentRepo extends JpaRepository<CommentDoa,UUID>{
    
}
