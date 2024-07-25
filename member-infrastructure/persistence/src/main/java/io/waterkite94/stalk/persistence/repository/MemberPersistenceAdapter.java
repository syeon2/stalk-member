package io.waterkite94.stalk.persistence.repository;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto;
import io.waterkite94.stalk.persistence.entity.MemberEntity;
import io.waterkite94.stalk.usecase.port.MemberPersistencePort;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPersistencePort {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	@NotNull
	@Override
	public Member save(@NotNull Member member) {
		MemberEntity savedMember = memberRepository.save(memberMapper.toEntity(member));

		return memberMapper.toDomain(savedMember);
	}

	@Nullable
	@Override
	public Member findMemberByEmailOrPhoneNumber(@NotNull String email, @NotNull String phoneNumber) {
		Optional<MemberEntity> findMemberOptional =
			memberRepository.findMemberByEmailOrPhoneNumber(email, phoneNumber);

		return findMemberOptional.map(memberMapper::toDomain).orElse(null);
	}

	@Nullable
	@Override
	public Member findMemberByEmail(@NotNull String email) {
		Optional<MemberEntity> findMemberOptional = memberRepository.findMemberByEmail(email);

		return findMemberOptional.map(memberMapper::toDomain).orElse(null);
	}

	@Override
	public void updateMemberInformation(@NotNull String memberId,
		@NotNull UpdateMemberInformationDto memberInformationDto) {
		memberRepository.updateInformation(memberId, memberInformationDto);
	}

	@Override
	public void updatePassword(@NotNull String email, @NotNull String password) {
		memberRepository.updatePassword(email, password);
	}

	@Override
	public void updateProfileImageUrl(@NotNull String memberId, @NotNull String profileImageUrl) {
		memberRepository.updateProfileImageUrl(memberId, profileImageUrl);
	}

	@Override
	public void updateStatusInactive(@NotNull String memberId) {
		memberRepository.updateInactiveStatus(memberId);
	}
}
