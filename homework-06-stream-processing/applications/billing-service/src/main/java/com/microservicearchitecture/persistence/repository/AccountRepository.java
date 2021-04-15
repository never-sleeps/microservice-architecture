package com.microservicearchitecture.persistence.repository;

import com.microservicearchitecture.persistence.entity.BillingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<BillingAccountEntity, Long>, JpaSpecificationExecutor<BillingAccountEntity> {
    Optional<BillingAccountEntity> findByUserId(Long userId);
}
