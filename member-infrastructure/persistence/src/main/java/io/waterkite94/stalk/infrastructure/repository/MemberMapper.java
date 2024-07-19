package io.waterkite94.stalk.infrastructure.repository;

import org.springframework.stereotype.Component;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.infrastructure.entity.MemberEntity;

@Component
public class MemberMapper {

	public MemberEntity toEntity(Member member) {
		return MemberEntity.builder()
			.memberId(member.getMemberId())
			.username(member.getUsername())
			.email(member.getEmail())
			.password(member.getPassword())
			.phoneNumber(member.getPhoneNumber())
			.introduction(member.getIntroduction())
			.profileImageUrl(member.getProfileImageUrl())
			.roleLevel(member.getRoleLevel())
			.build();
	}

	public Member toDomain(MemberEntity memberEntity) {
		return new Member(
			memberEntity.getId(),
			memberEntity.getMemberId(),
			memberEntity.getUsername(),
			memberEntity.getEmail(),
			memberEntity.getPassword(),
			memberEntity.getPhoneNumber(),
			memberEntity.getIntroduction(),
			memberEntity.getProfileImageUrl(),
			memberEntity.getRoleLevel(),
			memberEntity.getCreatedAt(),
			memberEntity.getUpdatedAt()
		);
	}
}
