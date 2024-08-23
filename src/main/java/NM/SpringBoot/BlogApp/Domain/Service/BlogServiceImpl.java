package NM.SpringBoot.BlogApp.Domain.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NM.SpringBoot.BlogApp.Domain.DTO.BlogDto;
import NM.SpringBoot.BlogApp.API.Exception.ResourceNotFound;
import NM.SpringBoot.BlogApp.Domain.DAO.BlogDoa;
import NM.SpringBoot.BlogApp.Domain.DAO.UserDao;
import NM.SpringBoot.BlogApp.Domain.Repository.BlogRepo;
import NM.SpringBoot.BlogApp.Domain.Repository.UserRepo;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BlogDoa> getAllBlogs() {
        return blogRepo.findAll();
    }

    @Override
    public List<BlogDoa> getAllBlogsbyUserId(long user_id) {
        UserDao user = userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFound("User Not found with id " + user_id));
        return user.getBlogs();
    }

    @Override
    public BlogDoa getBlogById(UUID id) {
        return blogRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + id));
    }

    @Override
    public BlogDoa creatBlog(BlogDto blog, long user_id) {
        BlogDoa blogDoa = modelMapper.map(blog, BlogDoa.class);

        UserDao user = userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFound("User Not Found with id " + user_id));

        blogDoa.setHost(user);
        blogDoa.setCreatedAt(LocalDateTime.now());
        blogDoa.setUpdatedAt(LocalDateTime.now());
        return blogRepo.save(blogDoa);
    }

    @Override
    public BlogDoa updateBlog(UUID id, BlogDto blog) {
        BlogDoa blogDoa = blogRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + id));
        modelMapper.map(blog,blogDoa);
        blogDoa.setUpdatedAt(LocalDateTime.now());

        return blogRepo.save(blogDoa);
    }

    @Override
    public boolean deleteUser(UUID id) {
        BlogDoa blogDoa = blogRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + id));

        blogRepo.delete(blogDoa);
        return true;
    }
    
}
