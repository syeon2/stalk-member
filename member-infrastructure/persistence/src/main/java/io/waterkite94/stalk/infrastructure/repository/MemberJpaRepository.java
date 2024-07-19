package io.waterkite94.stalk.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.stalk.infrastructure.entity.MemberEntity;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

	Optional<MemberEntity> findMemberByEmailOrPhoneNumber(String email, String phoneNumber);
}
