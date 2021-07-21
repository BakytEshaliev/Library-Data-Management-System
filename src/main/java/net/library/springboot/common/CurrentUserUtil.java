package net.library.springboot.common;


import net.library.springboot.security.domain.CurrentUser;
import org.springframework.ui.Model;

public class CurrentUserUtil {
	public static CurrentUser getCurrentUser(Model model) {
        CurrentUser currentUser = (CurrentUser) model.asMap().get("currentUser");
        return currentUser;
    }
}
