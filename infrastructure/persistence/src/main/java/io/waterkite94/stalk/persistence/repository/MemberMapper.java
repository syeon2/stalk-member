package io.waterkite94.stalk.persistence.repository;

import org.springframework.stereotype.Component;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.persistence.entity.MemberEntity;

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
			.memberStatus(member.getMemberStatus())
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
			memberEntity.getMemberStatus(),
			memberEntity.getCreatedAt(),
			memberEntity.getUpdatedAt()
		);
	}
}
