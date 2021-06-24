package net.javaguides.springboot.common;


import net.javaguides.springboot.security.domain.CurrentUser;
import org.springframework.ui.Model;

public class CurrentUserUtil {
	public static CurrentUser getCurrentUser(Model model) {
        CurrentUser currentUser = (CurrentUser) model.asMap().get("currentUser");
        return currentUser;
    }
}
