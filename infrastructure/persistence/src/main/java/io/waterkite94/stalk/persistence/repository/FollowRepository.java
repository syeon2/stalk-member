package io.waterkite94.stalk.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.persistence.entity.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO follow (follower_id, followed_id) VALUES (:followerId, :followedId)", nativeQuery = true)
	void saveByIds(String followerId, String followedId);

	@Transactional
	@Modifying(clearAutomatically = true)
	void deleteByFollowerIdAndFollowedId(String followerId, String followedId);

	// 함수를 Call한 회원이 팔로우한 회원 수
	Integer countByFollowerId(String memberId);

	// 함수를 Call한 회원을 팔로우한 회원 수
	Integer countByFollowedId(String memberId);
}
