package NM.SpringBoot.BlogApp.Service;

import java.util.List;
import java.util.UUID;

import NM.SpringBoot.BlogApp.DAO.BlogDoa;
import NM.SpringBoot.BlogApp.DTO.BlogDto;

public interface BlogService {
    public List<BlogDoa> getAllBlogs();
    public List<BlogDoa> getAllBlogsbyUserId(long user_id);
    public BlogDoa getBlogById(UUID id);
    public BlogDoa creatBlog(BlogDto blog, long user_id);
    public BlogDoa updateBlog(UUID id, BlogDto blog);
    public boolean deleteUser(UUID id);
    
}
