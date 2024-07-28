package io.waterkite94.stalk.persistence.adapter;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.persistence.IntegrationTestSupport;
import io.waterkite94.stalk.persistence.entity.FollowEntity;
import io.waterkite94.stalk.persistence.repository.FollowRepository;

class FollowPersistenceAdapterTest extends IntegrationTestSupport {

	@Autowired
	private FollowPersistenceAdapter followPersistenceAdapter;

	@Autowired
	private FollowRepository followRepository;

	@BeforeEach
	void before() {
		followRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우 관계를 저장합니다.")
	void save() {
		// given
		String followerId = "followerId";
		String followedId = "followedId";

		// when
		followPersistenceAdapter.saveFollow(followerId, followedId);

		// then
		List<FollowEntity> findAll = followRepository.findAll();

		assertThat(findAll.size()).isEqualTo(1);
		assertThat(findAll.get(0))
			.extracting("followerId", "followedId")
			.containsExactly(followerId, followedId);
	}

	@Test
	@Transactional
	@DisplayName(value = "이미 저장된 팔로우 관계라면 예외를 반환합니다. (follower & followed Unique)")
	void save_unique() {
		// given
		String followerId = "followerId";
		String followedId = "followedId";

		// when // then
		followPersistenceAdapter.saveFollow(followerId, followedId);

		assertThatThrownBy(() -> followPersistenceAdapter.saveFollow(followerId, followedId))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@Transactional
	@DisplayName(value = "같은 followerId는 여려명의 followedId를 저장할 수 있습니다.")
	void save_diff_followerId() {
		// given
		String followerId = "followerId";
		String followedId1 = "followerId1";
		String followedId2 = "followerId2";

		// when
		followPersistenceAdapter.saveFollow(followerId, followedId1);
		followPersistenceAdapter.saveFollow(followerId, followedId2);

		// then
		assertThat(followRepository.findAll().size()).isEqualTo(2);
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우 관계를 삭제합니다.")
	void deleteFollow() {
		// given
		String followerId = "followerId";
		String followedId = "followedId";
		followPersistenceAdapter.saveFollow(followerId, followedId);

		// when
		followPersistenceAdapter.deleteFollow(followerId, followedId);

		// then
		assertThat(followRepository.findAll().size()).isEqualTo(0);
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우한 회원의 수를 조회합니다.")
	void countFollower() {
		// given
		String followerId = "followerId";
		String followedId = "followedId1";
		String followedId2 = "followedId2";
		followPersistenceAdapter.saveFollow(followerId, followedId);
		followPersistenceAdapter.saveFollow(followerId, followedId2);

		// when
		int countFollowers = followPersistenceAdapter.countFollower(followerId);

		// then
		assertThat(countFollowers).isEqualTo(2);
	}

	@Test
	@Transactional
	@DisplayName(value = "회원을 팔로우한 수를 조회합니다.")
	void countFollowed() {
		// given
		String followerId = "followerId";
		String followedId1 = "followerId1";
		String followedId2 = "followerId2";
		followPersistenceAdapter.saveFollow(followedId1, followerId);
		followPersistenceAdapter.saveFollow(followedId2, followerId);

		// when
		int countFollowers = followPersistenceAdapter.countFollowed(followerId);

		// then
		assertThat(countFollowers).isEqualTo(2);
	}
}
