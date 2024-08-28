package NM.SpringBoot.BlogApp.Domain.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NM.SpringBoot.BlogApp.API.Exception.ResourceNotFound;
import NM.SpringBoot.BlogApp.Domain.DAO.BlogDoa;
import NM.SpringBoot.BlogApp.Domain.DAO.CommentDoa;
import NM.SpringBoot.BlogApp.Domain.DAO.UserDao;
import NM.SpringBoot.BlogApp.Domain.DTO.CommentDto;
import NM.SpringBoot.BlogApp.Domain.Repository.BlogRepo;
import NM.SpringBoot.BlogApp.Domain.Repository.CommentRepo;
import NM.SpringBoot.BlogApp.Domain.Repository.UserRepo;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired BlogRepo blogRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDoa getCommentById(UUID id) {
        return commentRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Comment Not Found with id " + id));
    }

    @Override
    public List<CommentDoa> getAllCommentsByBlogId(UUID blog_id) {
        BlogDoa blog = blogRepo.findById(blog_id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + blog_id));

        return blog.getComments();
    }

    @Override
    public CommentDoa createComment(CommentDto commentDto, UUID blog_id, String username) {
        CommentDoa commentDoa = modelMapper.map(commentDto, CommentDoa.class);

        BlogDoa blog = blogRepo.findById(blog_id).orElseThrow(() -> new ResourceNotFound("Blog Not Found with id " + blog_id));

        UserDao user = userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFound("User Not Found with username: " + username));

        commentDoa.setBlog(blog);
        commentDoa.setUser(user);
        commentDoa.setCreatedAt(LocalDateTime.now());
        commentDoa.setUpdatedAt(LocalDateTime.now());

        return commentRepo.save(commentDoa);
    }

    @Override
    public CommentDoa updateComment(UUID id, CommentDto commentDto, String username) {
        CommentDoa commentDoa = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Comment Not Found with id " + id));

        if(commentDoa.getUser().getUsername() != username){
            throw new RuntimeException("You are Unauthorized to update comment with id " + id);
        }

        commentDoa.setMessage(commentDto.getMessage());
        commentDoa.setUpdatedAt(LocalDateTime.now());

        return commentRepo.save(commentDoa);
    }

    @Override
    public boolean deleteComment(UUID id, String username) {
        CommentDoa commentDoa = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Comment Not Found with id " + id));

        if(commentDoa.getUser().getUsername() != username){
            throw new RuntimeException("You are Unauthorized to delete comment with id " + id);
        }

        commentRepo.delete(commentDoa);
        return true;
    }
    
}
