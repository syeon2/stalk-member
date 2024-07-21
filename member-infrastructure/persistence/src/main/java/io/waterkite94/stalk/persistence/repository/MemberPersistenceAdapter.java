package io.waterkite94.stalk.persistence.repository;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.persistence.entity.MemberEntity;
import io.waterkite94.stalk.usecase.port.MemberPersistencePort;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPersistencePort {

	private final MemberJpaRepository memberJpaRepository;
	private final MemberMapper memberMapper;

	@NotNull
	@Override
	public Member save(@NotNull Member member) {
		MemberEntity savedMember = memberJpaRepository.save(memberMapper.toEntity(member));

		return memberMapper.toDomain(savedMember);
	}

	@Nullable
	@Override
	public Member findMemberByEmailOrPhoneNumber(@NotNull String email, @NotNull String phoneNumber) {
		Optional<MemberEntity> findMemberOptional =
			memberJpaRepository.findMemberByEmailOrPhoneNumber(email, phoneNumber);

		return findMemberOptional.map(memberMapper::toDomain).orElse(null);
	}
}
