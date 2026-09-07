package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.EmailDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDeliveryRepository extends JpaRepository<EmailDelivery,Long> {


}
