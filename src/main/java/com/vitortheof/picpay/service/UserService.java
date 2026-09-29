package com.vitortheof.picpay.service;


import com.vitortheof.picpay.dto.UserRequestDTO;
import com.vitortheof.picpay.dto.UserResponseDTO;
import com.vitortheof.picpay.entity.User;
import com.vitortheof.picpay.entity.WalletType;
import com.vitortheof.picpay.exceptions.CpfCnpjAlreadyExistsException;
import com.vitortheof.picpay.exceptions.EmailAlreadyExistsException;
import com.vitortheof.picpay.mapper.UserMapper;
import com.vitortheof.picpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO create(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("O e-mail informado já está cadastrado!");
        }
        if (userRepository.existsByCpfCnpj(request.cpfCnpj())) {
            throw new CpfCnpjAlreadyExistsException("O cpf/cnpj já está cadastrado!");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(request.password())
                .cpfCnpj(request.cpfCnpj())
                .walletType(resolveType(request.cpfCnpj()))
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    private WalletType resolveType(String cpfCnpj) {
        String digits = cpfCnpj.replaceAll("\\D", "");
        return switch (digits.length()) {
            case 11 -> WalletType.COMMON;
            case 14 -> WalletType.COMMERCE;
            default -> throw new IllegalStateException("Unexpected value: " + digits.length());
        };
    }
}
