package net.library.springboot.security.service.currentuser;

import lombok.extern.slf4j.Slf4j;
import net.library.springboot.repository.UserRepository;
import net.library.springboot.security.domain.CurrentUser;
import net.library.springboot.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrentUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public CurrentUser loadUserByUsername(String loginName) throws UsernameNotFoundException {
        User user = userRepo.findByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException("Customer with loginName = " + loginName + " cannot be not found");
        }
        return new CurrentUser(user);
    }
}
