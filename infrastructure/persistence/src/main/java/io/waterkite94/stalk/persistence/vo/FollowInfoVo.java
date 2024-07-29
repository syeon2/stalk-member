package io.waterkite94.stalk.persistence.vo;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowInfoVo {

	private String memberId;
	private String username;

	@QueryProjection
	public FollowInfoVo(String memberId, String username) {
		this.memberId = memberId;
		this.username = username;
	}
}
