package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
}
