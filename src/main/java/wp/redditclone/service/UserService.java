package wp.redditclone.service;

import wp.redditclone.model.User;

public interface UserService {

    User register(User user);

    User save(User user);

    void saveUsers(User... users);

    User findByUsername(String username);
}
