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
	public void saveFollow(@NotNull String followeeId, @NotNull String followerId) {
		followRepository.saveByIds(followeeId, followerId);
	}

	@Override
	public void deleteFollow(@NotNull String followeeId, @NotNull String followerId) {
		followRepository.deleteByFolloweeIdAndFollowerId(followeeId, followerId);
	}
}
