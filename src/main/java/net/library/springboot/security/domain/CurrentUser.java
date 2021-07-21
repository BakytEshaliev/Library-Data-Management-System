package net.library.springboot.security.domain;

import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.AuthorityUtils;


@ToString
public class CurrentUser extends org.springframework.security.core.userdetails.User {
    @Setter
    private User user;

    public CurrentUser(User user) {
        super(user.getLoginName(),user.getPasswordHash(),AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }


    public User getUser() {
        return user;
    }
}
