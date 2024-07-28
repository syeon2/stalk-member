package io.waterkite94.stalk.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.stalk.persistence.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

	Optional<MemberEntity> findByEmailOrPhoneNumber(String email, String phoneNumber);

	Optional<MemberEntity> findByEmail(String email);
}
