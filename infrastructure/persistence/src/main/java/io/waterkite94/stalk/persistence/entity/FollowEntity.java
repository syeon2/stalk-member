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
		columnNames = {"followee_id", "follower_id"})
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
	@Column(name = "followee_id", columnDefinition = "varchar(60)", nullable = false)
	private String followeeId;

	/**
	 * 팔로우를 받은 회원 아이디
	 */
	@Column(name = "follower_id", columnDefinition = "varchar(60)", nullable = false)
	private String followerId;

	@Builder
	private FollowEntity(String followerId, String followeeId) {
		this.followerId = followerId;
		this.followeeId = followeeId;
	}
}
