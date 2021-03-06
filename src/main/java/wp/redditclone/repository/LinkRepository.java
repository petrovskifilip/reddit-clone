package wp.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wp.redditclone.model.Link;
import wp.redditclone.model.User;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findLinksByUser(User user);
}
