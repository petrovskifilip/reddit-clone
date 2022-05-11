package wp.redditclone.service;

import wp.redditclone.model.Link;

import java.util.List;
import java.util.Optional;

public interface LinkService {

    List<Link> findAll();

    Optional<Link> findById(Long id);

    Link save(Link link);

    List<Link> findByUser(String username);
}
