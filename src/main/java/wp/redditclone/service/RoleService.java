package wp.redditclone.service;

import wp.redditclone.model.Role;

public interface RoleService {

    Role findByName(String name);
}
