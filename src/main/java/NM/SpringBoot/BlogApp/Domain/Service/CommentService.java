package NM.SpringBoot.BlogApp.Domain.Service;

import java.util.List;
import java.util.UUID;

import NM.SpringBoot.BlogApp.Domain.DAO.CommentDoa;
import NM.SpringBoot.BlogApp.Domain.DTO.CommentDto;

public interface CommentService {
    public CommentDoa getCommentById(UUID id);
    public List<CommentDoa> getAllCommentsByBlogId(UUID blog_id);
    public CommentDoa createComment(CommentDto commentDto, UUID blog_id, String username);
    public CommentDoa updateComment(UUID id, CommentDto commentDto, String username);
    public boolean deleteComment(UUID id, String username);
    
}
