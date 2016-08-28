package com.drpweb.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Asus on 7/8/2559.
 */

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
