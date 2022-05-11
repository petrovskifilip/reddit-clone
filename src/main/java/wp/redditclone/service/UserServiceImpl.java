package wp.redditclone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wp.redditclone.model.User;
import wp.redditclone.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);
        user.addRole(roleService.findByName("ROLE_USER"));
        save(user);

        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void saveUsers(User... users) {

    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByAlias(username);
        if(user.isPresent()) {
            return user.get();
        }
        else
            throw new UsernameNotFoundException(username);
    }

}
