package io.waterkite94.stalk.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import io.waterkite94.stalk.domain.model.FollowInfoDto;
import io.waterkite94.stalk.persistence.repository.FollowRepository;
import io.waterkite94.stalk.usecase.port.FollowPersistencePort;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowPersistenceAdapter implements FollowPersistencePort {

	private final FollowRepository followRepository;
	private final FollowMapper followMapper;

	@Override
	public void saveFollow(@NotNull String followId, @NotNull String followedId) {
		followRepository.saveByIds(followId, followedId);
	}

	@Override
	public void deleteFollow(@NotNull String followId, @NotNull String followedId) {
		followRepository.deleteByFollowIdAndFollowedId(followId, followedId);
	}

	@Override
	public int countFollowing(@NotNull String memberId) {
		return followRepository.countByFollowId(memberId);
	}

	@Override
	public int countFollower(@NotNull String memberId) {
		return followRepository.countByFollowedId(memberId);
	}

	@NotNull
	@Override
	public List<FollowInfoDto> findFollowings(@NotNull String memberId, int offset, int limit) {
		return followRepository.findFollowings(memberId, offset, limit).stream()
			.map(followMapper::toFollowInfoDto)
			.collect(Collectors.toList());
	}

	@NotNull
	@Override
	public List<FollowInfoDto> findFollowers(@NotNull String memberId, int offset, int limit) {
		return followRepository.findFollowers(memberId, offset, limit).stream()
			.map(followMapper::toFollowInfoDto)
			.collect(Collectors.toList());
	}
}
