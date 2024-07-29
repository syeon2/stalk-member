package io.waterkite94.stalk.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.persistence.entity.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long>, FollowRepositoryCustom {

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO follow (follow_id, followed_id) VALUES (:followId, :followedId)", nativeQuery = true)
	void saveByIds(String followId, String followedId);

	@Transactional
	@Modifying(clearAutomatically = true)
	void deleteByFollowIdAndFollowedId(String followId, String followedId);

	// 함수를 Call한 회원이 팔로우한 회원 수
	Integer countByFollowId(String followId);

	// 함수를 Call한 회원을 팔로우한 회원 수
	Integer countByFollowedId(String memberId);
}
