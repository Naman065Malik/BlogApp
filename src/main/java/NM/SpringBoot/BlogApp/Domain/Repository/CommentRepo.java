package NM.SpringBoot.BlogApp.Domain.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.Domain.DAO.CommentDoa;

public interface CommentRepo extends JpaRepository<CommentDoa,UUID>{
    List<CommentDoa> findByBlogId(UUID blog_id);
    
}
