package com.eidiko.email.repository;

import com.eidiko.email.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailEntityRepository extends JpaRepository<MailEntity, String> {
    MailEntity findByMailType(String mailType);
}
