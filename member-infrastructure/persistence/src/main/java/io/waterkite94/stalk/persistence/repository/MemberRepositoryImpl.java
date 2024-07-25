package io.waterkite94.stalk.persistence.repository;

import static io.waterkite94.stalk.persistence.entity.QMemberEntity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto;
import io.waterkite94.stalk.domain.type.MemberStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public void updateInformation(String memberId, UpdateMemberProfileDto updateMemberProfileDto) {
		JPAUpdateClause update = queryFactory.update(memberEntity);

		update.set(memberEntity.username, updateMemberProfileDto.getUsername());
		update.set(memberEntity.introduction, updateMemberProfileDto.getIntroduction());

		update.where(memberEntity.memberId.eq(memberId))
			.execute();
	}

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public void updatePassword(String email, String password) {
		queryFactory.update(memberEntity)
			.set(memberEntity.password, password)
			.where(memberEntity.email.eq(email))
			.execute();
	}

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public void updateProfileImageUrl(String memberId, String profileImageUrl) {
		queryFactory.update(memberEntity)
			.set(memberEntity.profileImageUrl, profileImageUrl)
			.where(memberEntity.memberId.eq(memberId))
			.execute();
	}

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public void updateInactiveStatus(String memberId) {
		queryFactory.update(memberEntity)
			.set(memberEntity.memberStatus, MemberStatus.INACTIVE)
			.where(memberEntity.memberId.eq(memberId))
			.execute();
	}
}
