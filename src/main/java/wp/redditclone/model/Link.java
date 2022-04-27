package wp.redditclone.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @OneToMany(mappedBy = "link")
    private List<Comment> comments;

}
