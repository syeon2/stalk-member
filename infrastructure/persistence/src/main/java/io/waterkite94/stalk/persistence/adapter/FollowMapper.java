package io.waterkite94.stalk.persistence.adapter;

import org.springframework.stereotype.Component;

import io.waterkite94.stalk.domain.model.FollowInfoDto;
import io.waterkite94.stalk.persistence.vo.FollowInfoVo;

@Component
public class FollowMapper {

	public FollowInfoDto toFollowInfoDto(FollowInfoVo followerDto) {
		return new FollowInfoDto(followerDto.getMemberId(), followerDto.getUsername());
	}
}
