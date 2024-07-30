package NM.SpringBoot.BlogApp.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.DAO.BlogDoa;

public interface BlogRepo extends JpaRepository<BlogDoa,UUID>{
    List<BlogDoa> findByHostId(Long userId);
}
