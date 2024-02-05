package com.example.securityjwt.repository;

import com.example.securityjwt.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserid(String userid);

    boolean existsByUserid(String userid);

    Optional<UserEntity> findByUsernameAndEmail(String username, String email);

    List<UserEntity> findByUseridAndUsernameAndEmail(String userid, String username, String email);

    boolean existsByEmail(String email);
}
