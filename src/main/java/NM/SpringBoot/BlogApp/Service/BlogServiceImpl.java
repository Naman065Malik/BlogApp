package NM.SpringBoot.BlogApp.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NM.SpringBoot.BlogApp.DAO.BlogDoa;
import NM.SpringBoot.BlogApp.DAO.UserDao;
import NM.SpringBoot.BlogApp.DTO.BlogDto;
import NM.SpringBoot.BlogApp.Exception.ResourceNotFound;
import NM.SpringBoot.BlogApp.Repository.BlogRepo;
import NM.SpringBoot.BlogApp.Repository.UserRepo;

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
        return blogRepo.findByHostId(user_id);
    }

    @Override
    public BlogDoa getBlogById(long id) {
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
    public BlogDoa updateBlog(long id, BlogDto blog) {
        BlogDoa blogDoa = blogRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + id));
        modelMapper.map(blog,blogDoa);
        blogDoa.setUpdatedAt(LocalDateTime.now());

        return blogRepo.save(blogDoa);
    }

    @Override
    public boolean deleteUser(long id) {
        BlogDoa blogDoa = blogRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + id));

        blogRepo.delete(blogDoa);
        return true;
    }
    
}
