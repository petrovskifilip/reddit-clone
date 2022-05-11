package wp.redditclone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wp.redditclone.model.Link;
import wp.redditclone.model.User;
import wp.redditclone.repository.LinkRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService{

    private final LinkRepository linkRepository;
    private final UserService userService;

    public LinkServiceImpl(LinkRepository linkRepository, UserService userService) {
        this.linkRepository = linkRepository;
        this.userService = userService;
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public Optional<Link> findById(Long id) {
        return linkRepository.findById(id);
    }

    @Override
    public Link save(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public List<Link> findByUser(String username) {
        User user = userService.findByUsername(username);
        return linkRepository.findLinksByUser(user);
    }
}
