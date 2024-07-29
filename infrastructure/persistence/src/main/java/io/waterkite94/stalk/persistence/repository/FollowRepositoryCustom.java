package io.waterkite94.stalk.persistence.repository;

import java.util.List;

import io.waterkite94.stalk.persistence.vo.FollowInfoVo;

public interface FollowRepositoryCustom {

	List<FollowInfoVo> findFollowings(String memberId, Integer offset, Integer limit);

	List<FollowInfoVo> findFollowers(String memberId, Integer offset, Integer limit);
}
