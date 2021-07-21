package net.library.springboot.repository;

import net.library.springboot.security.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByLoginName(String loginName);
}
