package wp.redditclone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wp.redditclone.model.Comment;
import wp.redditclone.model.Link;
import wp.redditclone.model.User;
import wp.redditclone.service.CommentService;
import wp.redditclone.service.LinkService;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
public class LinkController {

    private final LinkService linkService;
    private final CommentService commentService;

    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkService.findAll());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> linkOptional = linkService.findById(id);
        if (linkOptional.isPresent()) {
            Link currentLink = linkOptional.get();
            Comment comment = new Comment();
            comment.setLink(currentLink);
            model.addAttribute("comment", comment);
            model.addAttribute("link", currentLink);
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link", new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("Validation errors were found while submitting a nwe link");
            model.addAttribute("link", link);
            return "link/submit";
        } else {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            link.setUser(user);

            linkService.save(link);
            log.info("New link was saved successfully");
            redirectAttributes
                    .addAttribute("id", link.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("There was a problem adding a new comment.");
        } else {
            commentService.save(comment);
            log.info("New comment was saved successfully");
        }
        return "redirect:/link/" + comment.getLink().getId();
    }

}