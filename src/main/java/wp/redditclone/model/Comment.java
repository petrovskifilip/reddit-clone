package wp.redditclone.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne
    private Link link;
}
