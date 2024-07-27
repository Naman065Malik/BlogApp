package NM.SpringBoot.BlogApp.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import NM.SpringBoot.BlogApp.DAO.BlogDoa;
import NM.SpringBoot.BlogApp.DTO.BlogDto;
import NM.SpringBoot.BlogApp.Service.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        List<BlogDto> blogs = blogService.getAllBlogs()
        .stream().map(blog -> modelMapper.map(blog, BlogDto.class))
        .collect(Collectors.toList());
        
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable("id") long id) {
        BlogDoa blog = blogService.getBlogById(id);
        return new ResponseEntity<>(modelMapper.map(blog, BlogDto.class), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogDto>> getAllBlogsByUserId(@PathVariable("userId") long userId) {
        List<BlogDto> blogs = blogService.getAllBlogsbyUserId(userId)
        .stream().map(blog -> modelMapper.map(blog, BlogDto.class))
        .collect(Collectors.toList());
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<BlogDto> createBlog(@PathVariable long userId, @RequestBody BlogDto blogDto) {
        BlogDoa Blog = blogService.creatBlog(blogDto, userId);
        return new ResponseEntity<>(modelMapper.map(Blog, BlogDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDto> updateBlog(@PathVariable long id, @RequestBody BlogDto blogDto) {
        System.out.println(blogDto.toString());
        BlogDoa Blog = blogService.updateBlog(id, blogDto);
        return new ResponseEntity<>(modelMapper.map(Blog, BlogDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable long id) {
        blogService.deleteUser(id);
        return new ResponseEntity<>("Blog deleted successfully", HttpStatus.NO_CONTENT);
    }
}
