package wp.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wp.redditclone.model.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
}
