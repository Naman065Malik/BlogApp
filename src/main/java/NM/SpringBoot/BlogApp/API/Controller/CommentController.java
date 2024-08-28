package NM.SpringBoot.BlogApp.API.Controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NM.SpringBoot.BlogApp.Domain.DAO.CommentDoa;
import NM.SpringBoot.BlogApp.Domain.DTO.CommentDto;
import NM.SpringBoot.BlogApp.Domain.Service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID id) {
        CommentDoa comment = commentService.getCommentById(id);
        return new ResponseEntity<>(modelMapper.map(comment, CommentDto.class), HttpStatus.OK);
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByBlogId(@PathVariable UUID blogId) {
        List<CommentDto> comments = commentService.getAllCommentsByBlogId(blogId)
        .stream().map( comment -> modelMapper.map(comment, CommentDto.class))
        .collect(Collectors.toList());
        
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/blog/{blogId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable UUID blogId, @RequestBody CommentDto commentDto, Principal principal) {
        CommentDoa comment = commentService.createComment(commentDto, blogId, principal.getName());
        return new ResponseEntity<>(modelMapper.map(comment, CommentDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable UUID id, @RequestBody CommentDto commentDto, Principal principal) {
        CommentDoa comment = commentService.updateComment(id, commentDto, principal.getName());
        return new ResponseEntity<>(modelMapper.map(comment, CommentDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id, Principal principal) {
        commentService.deleteComment(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
