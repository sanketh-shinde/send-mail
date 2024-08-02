package com.eidiko.email.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mail_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailEntity {

    @Id
    private String mailType;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String body;
}
