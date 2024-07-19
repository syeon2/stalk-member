package io.waterkite94.stalk.infrastructure.repository;

import org.springframework.stereotype.Repository;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.infrastructure.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter {

	private final MemberJpaRepository memberJpaRepository;
	private final MemberMapper memberMapper;

	public Member save(Member member) {
		MemberEntity savedMember = memberJpaRepository.save(
			memberMapper.toEntity(member));

		return memberMapper.toDomain(savedMember);
	}
}
