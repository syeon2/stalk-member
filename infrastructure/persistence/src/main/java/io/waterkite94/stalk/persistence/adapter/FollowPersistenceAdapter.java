package io.waterkite94.stalk.persistence.adapter;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import io.waterkite94.stalk.persistence.repository.FollowRepository;
import io.waterkite94.stalk.usecase.port.FollowPersistencePort;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowPersistenceAdapter implements FollowPersistencePort {

	private final FollowRepository followRepository;

	@Override
	public void saveFollow(@NotNull String followerId, @NotNull String followedId) {
		followRepository.saveByIds(followerId, followedId);
	}

	@Override
	public void deleteFollow(@NotNull String followerId, @NotNull String followedId) {
		followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
	}

	@Override
	public int countFollower(@NotNull String memberId) {
		return followRepository.countByFollowerId(memberId);
	}

	@Override
	public int countFollowed(@NotNull String memberId) {
		return followRepository.countByFollowedId(memberId);
	}
}
