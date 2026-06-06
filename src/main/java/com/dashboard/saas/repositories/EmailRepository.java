package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailOtp, Long> {


    public Optional<EmailOtp> findTopByEmailOrderByCreatedAtDesc(String email);





}
