package wp.redditclone.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import wp.redditclone.model.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication securityAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if(securityAuthentication == null ||
                securityAuthentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of("master@gmail.com");
        }
        return Optional.of(
                ((User) securityAuthentication
                        .getPrincipal())
                        .getUsername()
        );
    }
}