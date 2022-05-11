package wp.redditclone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wp.redditclone.model.Comment;
import wp.redditclone.repository.CommentRepository;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
