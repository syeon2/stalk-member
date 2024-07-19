package io.waterkite94.stalk.infrastructure.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.infrastructure.entity.MemberEntity;
import io.waterkite94.stalk.usecase.port.MemberRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

	private final MemberJpaRepository memberJpaRepository;
	private final MemberMapper memberMapper;

	@NotNull
	@Override
	public Member save(@NotNull Member member) {
		MemberEntity savedMember = memberJpaRepository.save(
			memberMapper.toEntity(member));

		return memberMapper.toDomain(savedMember);
	}
}
