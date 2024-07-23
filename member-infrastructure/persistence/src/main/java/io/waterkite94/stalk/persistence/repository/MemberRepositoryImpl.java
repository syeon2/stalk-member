package io.waterkite94.stalk.persistence.repository;

import static io.waterkite94.stalk.persistence.entity.QMemberEntity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Modifying(clearAutomatically = true)
	@Transactional
	@Override
	public void updateInformation(String memberId, UpdateMemberInformationDto updateMemberInformationDto) {
		System.out.println(updateMemberInformationDto.getIntroduction());
		JPAUpdateClause update = queryFactory.update(memberEntity);

		update.set(memberEntity.username, updateMemberInformationDto.getUsername());
		update.set(memberEntity.introduction, updateMemberInformationDto.getIntroduction());

		update.where(memberEntity.memberId.eq(memberId))
			.execute();
	}
}
