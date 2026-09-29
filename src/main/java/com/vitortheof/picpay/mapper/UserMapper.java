package com.vitortheof.picpay.mapper;

import com.vitortheof.picpay.dto.UserResponseDTO;
import com.vitortheof.picpay.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
   public UserResponseDTO toResponse(User user) {
       return new UserResponseDTO(
               user.getFullName(),
               user.getEmail(),
               user.getCpfCnpj(),
               user.getWalletType(),
               user.getBalance());
   }
}
