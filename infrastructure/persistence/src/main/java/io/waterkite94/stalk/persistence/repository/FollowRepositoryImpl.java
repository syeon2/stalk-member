package io.waterkite94.stalk.persistence.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.stalk.persistence.entity.QFollowEntity;
import io.waterkite94.stalk.persistence.entity.QMemberEntity;
import io.waterkite94.stalk.persistence.vo.FollowInfoVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FollowRepositoryImpl implements FollowRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<FollowInfoVo> findFollowings(String memberId, Integer offset, Integer limit) {
		return queryFactory
			.select(Projections.constructor(FollowInfoVo.class,
				QFollowEntity.followEntity.followedId,
				QMemberEntity.memberEntity.username))
			.from(QFollowEntity.followEntity)
			.leftJoin(QMemberEntity.memberEntity)
			.on(QFollowEntity.followEntity.followedId.eq(QMemberEntity.memberEntity.memberId))
			.where(QFollowEntity.followEntity.followId.eq(memberId))
			.offset(offset)
			.limit(limit)
			.fetch();
	}

	@Override
	public List<FollowInfoVo> findFollowers(String memberId, Integer offset, Integer limit) {
		return queryFactory
			.select(Projections.constructor(FollowInfoVo.class,
				QFollowEntity.followEntity.followId,
				QMemberEntity.memberEntity.username))
			.from(QFollowEntity.followEntity)
			.leftJoin(QMemberEntity.memberEntity)
			.on(QFollowEntity.followEntity.followId.eq(QMemberEntity.memberEntity.memberId))
			.where(QFollowEntity.followEntity.followedId.eq(memberId))
			.offset(offset)
			.limit(limit)
			.fetch();
	}
}
