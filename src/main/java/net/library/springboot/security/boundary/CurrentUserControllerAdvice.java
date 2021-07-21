package net.library.springboot.security.boundary;


import lombok.extern.slf4j.Slf4j;
import net.library.springboot.security.domain.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Slf4j
public class CurrentUserControllerAdvice {

    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
//        log.info("authentication = " + authentication);
        return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }

}
