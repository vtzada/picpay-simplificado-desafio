package com.vitortheof.picpay.repository;

import com.vitortheof.picpay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByCpfCnpj(String cpf);
}
