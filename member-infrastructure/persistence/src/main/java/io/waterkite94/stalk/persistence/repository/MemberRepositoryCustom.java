package io.waterkite94.stalk.persistence.repository;

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto;

public interface MemberRepositoryCustom {

	void updateInformation(String memberId, UpdateMemberInformationDto updateMemberInformationDto);

	void updatePassword(String email, String password);

	void updateProfileImageUrl(String memberId, String profileImageUrl);
}
