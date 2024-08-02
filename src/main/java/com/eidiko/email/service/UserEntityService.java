package com.eidiko.email.service;

import com.eidiko.email.dto.UserEntityDTO;
import com.eidiko.email.entity.UserEntity;
import com.eidiko.email.exception.UserNotFoundException;
import org.apache.catalina.User;

public interface UserEntityService {

    UserEntityDTO createUser(UserEntity userEntity);

    UserEntityDTO getUser(String email, String password) throws UserNotFoundException;

    UserEntityDTO getUserByMail(String mail);

    UserEntityDTO updatePassword(String newPassword, String email) throws UserNotFoundException;

}
