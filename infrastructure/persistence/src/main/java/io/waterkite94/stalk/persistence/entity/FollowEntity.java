package io.waterkite94.stalk.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
	name = "follow",
	uniqueConstraints = @UniqueConstraint(
		name = "FOLLOW_UNIQUE",
		columnNames = {"follower_id", "followed_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	/**
	 * 팔로우를 신청한 회원 아이디
	 */
	@Column(name = "follower_id", columnDefinition = "varchar(60)", nullable = false)
	private String followerId;

	/**
	 * 팔로우를 받은 회원 아이디
	 */
	@Column(name = "followed_id", columnDefinition = "varchar(60)", nullable = false)
	private String followedId;

	@Builder
	private FollowEntity(String followerId, String followedId) {
		this.followerId = followerId;
		this.followedId = followedId;
	}
}
