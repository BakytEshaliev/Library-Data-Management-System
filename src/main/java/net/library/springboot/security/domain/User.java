package net.library.springboot.security.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.library.springboot.common.BaseEntity;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity<Long> {

    private String loginName;
    private String passwordHash;
    private Role role;
}
