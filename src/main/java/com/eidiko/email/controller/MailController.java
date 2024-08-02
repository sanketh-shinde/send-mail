package com.eidiko.email.controller;

import com.eidiko.email.entity.MailEntity;
import com.eidiko.email.service.MailEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailEntityService mailEntityService;

    @PostMapping("/add")
    public ResponseEntity<?> addMailType(@RequestBody MailEntity mailEntity) {
        MailEntity createdMailType = mailEntityService.createMailType(mailEntity);
        if (createdMailType.getMailType() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdMailType, HttpStatus.CREATED);
    }

    @PostMapping("/get/{mailType}")
    public ResponseEntity<?> getMailType(@PathVariable String mailType) {
        MailEntity fetchedMailType = mailEntityService.getMailType(mailType);
        if (fetchedMailType.getMailType() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(fetchedMailType, HttpStatus.OK);
    }

}
