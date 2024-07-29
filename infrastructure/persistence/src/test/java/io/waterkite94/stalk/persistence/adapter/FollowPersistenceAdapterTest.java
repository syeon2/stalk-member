package io.waterkite94.stalk.persistence.adapter;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.domain.model.FollowInfoDto;
import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.domain.type.MemberStatus;
import io.waterkite94.stalk.domain.type.RoleLevel;
import io.waterkite94.stalk.persistence.IntegrationTestSupport;
import io.waterkite94.stalk.persistence.entity.FollowEntity;
import io.waterkite94.stalk.persistence.repository.FollowRepository;
import io.waterkite94.stalk.persistence.repository.MemberRepository;

class FollowPersistenceAdapterTest extends IntegrationTestSupport {

	@Autowired
	private FollowPersistenceAdapter followPersistenceAdapter;

	@Autowired
	private MemberPersistenceAdapter memberPersistenceAdapter;

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void before() {
		followRepository.deleteAllInBatch();
		memberRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우 관계를 저장합니다.")
	void save() {
		// given
		String followId = "followId";
		String followedId = "followedId";

		// when
		followPersistenceAdapter.saveFollow(followId, followedId);

		// then
		List<FollowEntity> findAll = followRepository.findAll();

		assertThat(findAll.size()).isEqualTo(1);
		assertThat(findAll.get(0))
			.extracting("followId", "followedId")
			.containsExactly(followId, followedId);
	}

	@Test
	@Transactional
	@DisplayName(value = "이미 저장된 팔로우 관계라면 예외를 반환합니다. (follower & followed Unique)")
	void save_unique() {
		// given
		String followId = "followId";
		String followedId = "followedId";

		// when // then
		followPersistenceAdapter.saveFollow(followId, followedId);

		assertThatThrownBy(() -> followPersistenceAdapter.saveFollow(followId, followedId))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@Transactional
	@DisplayName(value = "같은 followerId는 여려명의 followedId를 저장할 수 있습니다.")
	void save_diff_followerId() {
		// given
		String followId = "followId";
		String followedId1 = "followedId1";
		String followedId2 = "followedId2";

		// when
		followPersistenceAdapter.saveFollow(followId, followedId1);
		followPersistenceAdapter.saveFollow(followId, followedId2);

		// then
		assertThat(followRepository.findAll().size()).isEqualTo(2);
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우 관계를 삭제합니다.")
	void deleteFollow() {
		// given
		String followId = "followId";
		String followedId = "followedId";
		followPersistenceAdapter.saveFollow(followId, followedId);

		// when
		followPersistenceAdapter.deleteFollow(followId, followedId);

		// then
		assertThat(followRepository.findAll().size()).isEqualTo(0);
	}

	@Test
	@Transactional
	@DisplayName(value = "팔로우한 회원의 수를 조회합니다.")
	void countFollowing() {
		// given
		String memberId = "memberId";
		String followedId1 = "followedId1";
		String followedId2 = "followedId2";
		followPersistenceAdapter.saveFollow(memberId, followedId1);
		followPersistenceAdapter.saveFollow(memberId, followedId2);

		// when
		int countFollowers = followPersistenceAdapter.countFollowing(memberId);

		// then
		assertThat(countFollowers).isEqualTo(2);
	}

	@Test
	@Transactional
	@DisplayName(value = "회원을 팔로우한 수를 조회합니다.")
	void countFollower() {
		// given
		String memberId = "memberId";
		String followId1 = "followId1";
		String followId2 = "followId2";
		followPersistenceAdapter.saveFollow(followId1, memberId);
		followPersistenceAdapter.saveFollow(followId2, memberId);

		// when
		int countFollowers = followPersistenceAdapter.countFollower(memberId);

		// then
		assertThat(countFollowers).isEqualTo(2);
	}

	@Test
	@Transactional
	@DisplayName(value = "회원이 팔로우한 회원들의 정보를 조회합니다.")
	void findFollowings() {
		// given
		String username = "username";

		String memberId = "memberId";
		memberPersistenceAdapter.saveMember(createMember(memberId, 0, username));

		Integer num1 = 1;
		String followedId1 = "followedId1";
		memberPersistenceAdapter.saveMember(createMember(followedId1, num1, username));

		Integer num2 = 2;
		String followedId2 = "followedId2";
		memberPersistenceAdapter.saveMember(createMember(followedId2, num2, username));

		assertThat(memberRepository.findAll().size()).isEqualTo(3);

		followPersistenceAdapter.saveFollow(memberId, followedId1);
		followPersistenceAdapter.saveFollow(memberId, followedId2);

		// when
		List<FollowInfoDto> findFollowInfoDtos = followPersistenceAdapter.findFollowings(memberId, 0, 10);

		// then
		assertThat(findFollowInfoDtos).hasSize(2)
			.extracting("memberId", "username")
			.containsExactlyInAnyOrder(
				tuple(followedId1, username + num1),
				tuple(followedId2, username + num2));
	}

	@Test
	@Transactional
	@DisplayName(value = "회원을 팔로우한 회원들의 정보를 조회합니다.")
	void findFollowers() {
		// given
		String username = "username";

		String memberId = "memberId";
		memberPersistenceAdapter.saveMember(createMember(memberId, 0, username));

		Integer num1 = 1;
		String followId1 = "followId1";
		memberPersistenceAdapter.saveMember(createMember(followId1, num1, username));

		Integer num2 = 2;
		String followId2 = "followId2";
		memberPersistenceAdapter.saveMember(createMember(followId2, num2, username));

		assertThat(memberRepository.findAll().size()).isEqualTo(3);

		followPersistenceAdapter.saveFollow(followId1, memberId);
		followPersistenceAdapter.saveFollow(followId2, memberId);

		// when
		List<FollowInfoDto> findFollowers = followPersistenceAdapter.findFollowers(memberId, 0, 10);

		// then
		assertThat(findFollowers).hasSize(2)
			.extracting("memberId", "username")
			.containsExactlyInAnyOrder(
				tuple(followId1, username + num1),
				tuple(followId2, username + num2));
	}

	public Member createMember(String memberId, Integer num, String username) {
		return new Member(
			null, memberId, username + num, memberId, "password", "0001111222" + num, "introduction", null,
			RoleLevel.USER_GENERAL, MemberStatus.ACTIVE, null, null
		);
	}
}
