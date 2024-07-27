package NM.SpringBoot.BlogApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import NM.SpringBoot.BlogApp.DAO.BlogDoa;

public interface BlogRepo extends JpaRepository<BlogDoa,Long>{
    List<BlogDoa> findByHostId(Long userId);
}
