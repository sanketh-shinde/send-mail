package com.eidiko.email.service;

import com.eidiko.email.entity.MailEntity;
import com.eidiko.email.repository.MailEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailEntityServiceImpl implements MailEntityService {

    @Autowired
    private MailEntityRepository mailEntityRepository;

    @Override
    public MailEntity createMailType(MailEntity mailEntity) {
        return mailEntityRepository.save(mailEntity);
    }

    @Override
    public MailEntity getMailType(String mailType) {
        return mailEntityRepository.findByMailType(mailType);
    }
}
