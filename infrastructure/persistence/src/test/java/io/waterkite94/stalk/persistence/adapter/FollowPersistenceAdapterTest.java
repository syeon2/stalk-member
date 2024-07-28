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
		String followeeId = "followeeId";
		String followerId = "followerId";

		// when
		followPersistenceAdapter.saveFollow(followeeId, followerId);

		// then
		List<FollowEntity> findAll = followRepository.findAll();

		assertThat(findAll.size()).isEqualTo(1);
		assertThat(findAll.get(0))
			.extracting("followeeId", "followerId")
			.containsExactly(followeeId, followerId);
	}

	@Test
	@Transactional
	@DisplayName(value = "이미 저장된 팔로우 관계라면 예외를 반환합니다. (followee & follower Unique)")
	void save_unique() {
		// given
		String followeeId = "followeeId";
		String followerId = "followerId";

		// when // then
		followPersistenceAdapter.saveFollow(followeeId, followerId);

		assertThatThrownBy(() -> followPersistenceAdapter.saveFollow(followeeId, followerId))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@Transactional
	@DisplayName(value = "같은 followeeId는 여려명의 followerId를 저장할 수 있습니다.")
	void save_diff_followerId() {
		// given
		String followeeId = "followeeId";
		String followerId1 = "followerId1";
		String followerId2 = "followerId2";

		// when
		followPersistenceAdapter.saveFollow(followeeId, followerId1);
		followPersistenceAdapter.saveFollow(followeeId, followerId2);

		// then
		assertThat(followRepository.findAll().size()).isEqualTo(2);
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우 관계를 삭제합니다.")
	void deleteFollow() {
		// given
		String followeeId = "followeeId";
		String followerId = "followerId";
		followPersistenceAdapter.saveFollow(followeeId, followerId);

		// when
		followPersistenceAdapter.deleteFollow(followeeId, followerId);

		// then
		assertThat(followRepository.findAll().size()).isEqualTo(0);
	}
}
