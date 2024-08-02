package com.eidiko.email.service;

import com.eidiko.email.entity.MailEntity;

public interface MailEntityService {

    MailEntity createMailType(MailEntity mailEntity);

    MailEntity getMailType(String mailType);
}
