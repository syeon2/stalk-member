package io.waterkite94.stalk.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.persistence.entity.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO follow (followee_id, follower_id) VALUES (:followeeId, :followerId)", nativeQuery = true)
	void saveByIds(String followeeId, String followerId);

	@Transactional
	@Modifying(clearAutomatically = true)
	void deleteByFolloweeIdAndFollowerId(String followeeId, String followerId);
}
