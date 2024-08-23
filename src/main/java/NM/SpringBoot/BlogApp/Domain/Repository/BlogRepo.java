package NM.SpringBoot.BlogApp.Domain.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.Domain.DAO.BlogDoa;

public interface BlogRepo extends JpaRepository<BlogDoa,UUID>{
    List<BlogDoa> findByHostId(Long userId);
}
