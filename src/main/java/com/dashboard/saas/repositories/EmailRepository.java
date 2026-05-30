package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Long, EmailOtp> {


    public Optional<EmailOtp> findTopByEmailOrderByCreatedAtDesc(String email);




}
