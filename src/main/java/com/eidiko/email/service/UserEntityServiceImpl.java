package com.eidiko.email.service;

import com.eidiko.email.dto.UserEntityDTO;
import com.eidiko.email.entity.UserEntity;
import com.eidiko.email.exception.UserNotFoundException;
import com.eidiko.email.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserEntityDTO createUser(UserEntity userEntity) {
        UserEntity savedUserEntity = userEntityRepository.save(userEntity);
        return modelMapper.map(savedUserEntity, UserEntityDTO.class);
    }

    @Override
    public UserEntityDTO getUser(String email, String password) throws UserNotFoundException {
        UserEntity userEntity = userEntityRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userEntity, UserEntityDTO.class);
    }

    @Override
    public UserEntityDTO getUserByMail(String mail) {
        UserEntity userByMail = userEntityRepository.findByEmail(mail)
                .orElse(null);
        return modelMapper.map(userByMail, UserEntityDTO.class);
    }

    @Override
    public UserEntityDTO updatePassword(String newPassword, String email) throws UserNotFoundException {
        UserEntity fetchedUser = userEntityRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        fetchedUser.setPassword(newPassword);
        UserEntity updatedUser = userEntityRepository.save(fetchedUser);
        return modelMapper.map(updatedUser, UserEntityDTO.class);
    }
}
