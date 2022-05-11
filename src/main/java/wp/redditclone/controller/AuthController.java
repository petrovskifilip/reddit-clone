package wp.redditclone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wp.redditclone.model.Link;
import wp.redditclone.model.User;
import wp.redditclone.service.LinkService;
import wp.redditclone.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class AuthController {

    private final UserService userService;
    private final LinkService linkService;

    public AuthController(UserService userService, LinkService linkService) {
        this.userService = userService;
        this.linkService = linkService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        List<Link> linkList = linkService.findByUser(username);
        model.addAttribute("username", username);
        model.addAttribute("links", linkList);
        return "auth/profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("Validation errors were found while registering a new user");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "auth/register";
        } else {
            User newUser = userService.register(user);
            redirectAttributes.addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/register";
        }
    }

    @GetMapping("/u/{username}")
    public String profileView(@PathVariable String username, Model model) {
        List<Link> linkList = linkService.findByUser(username);
        model.addAttribute("username", username);
        model.addAttribute("links", linkList);
        return "auth/profile";
    }


}
