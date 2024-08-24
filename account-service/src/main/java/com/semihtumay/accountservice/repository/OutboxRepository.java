package com.semihtumay.accountservice.repository;

import com.semihtumay.accountservice.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
}