package NM.SpringBoot.BlogApp.Domain.Service;

import java.util.List;
import java.util.UUID;

import NM.SpringBoot.BlogApp.Domain.DTO.BlogDto;
import NM.SpringBoot.BlogApp.Domain.DAO.BlogDoa;

public interface BlogService {
    public List<BlogDoa> getAllBlogs();
    public List<BlogDoa> getAllBlogsbyUserId(long user_id);
    public BlogDoa getBlogById(UUID id);
    public BlogDoa creatBlog(BlogDto blog, long user_id);
    public BlogDoa updateBlog(UUID id, BlogDto blog);
    public boolean deleteUser(UUID id);
    
}
