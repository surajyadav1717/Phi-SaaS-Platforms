package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository  extends JpaRepository<RefreshToken ,Long> {

    Optional<RefreshToken> findByRefreshToken (String refreshToken);

    List<RefreshToken> findByUser_IdAndRevokedFalse(Long userId);


}
