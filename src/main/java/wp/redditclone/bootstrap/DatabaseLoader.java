package wp.redditclone.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import wp.redditclone.model.Comment;
import wp.redditclone.model.Link;
import wp.redditclone.model.Role;
import wp.redditclone.model.User;
import wp.redditclone.repository.CommentRepository;
import wp.redditclone.repository.LinkRepository;
import wp.redditclone.repository.RoleRepository;
import wp.redditclone.repository.UserRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class DatabaseLoader implements CommandLineRunner {

    private LinkRepository linkRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository,
                          UserRepository userRepository, RoleRepository roleRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        //add users and roles
        addUsersAndRoles();

        Map<String, String> links = new HashMap<>();
        links.put("Learn Spring Boot", "https://www.baeldung.com/spring-boot");
        links.put("Spring Boot JPA", "https://www.javatpoint.com/spring-boot-jpa");
        links.put("REST with Spring Tutorial", "https://www.baeldung.com/rest-with-spring-series");

        links.forEach((k, v) -> {
            User u1 = users.get("user@gmail.com");
            User u2 = users.get("super@gmail.com");
            Link link = new Link(k, v);
            if (k.startsWith("Build")) {
                link.setUser(u1);
            } else {
                link.setUser(u2);
            }

            linkRepository.save(link);

            Comment spring = new Comment("Thank you for this link related to Spring Boot!", link);
            commentRepository.save(spring);
            link.addComment(spring);
        });


        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount);
        Optional<User> byEmail = userRepository.findByEmail("user@gmail.com");
    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com", secret, "Joe", "User", "user1");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        userRepository.save(user);
        users.put("user@gmail.com", user);

        User admin = new User("admin@gmail.com", secret,"Joe", "Admin", "masteradmin");
        admin.setAlias("masteradmin");
        admin.setConfirmPassword(secret);
        admin.addRole(adminRole);
        userRepository.save(admin);
        users.put("admin@gmail.com", admin);

        User master = new User("master@gmail.com", secret, "Super", "User", "user3");
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        master.setConfirmPassword(secret);
        userRepository.save(master);
        users.put("super@gmail.com", master);

    }
}