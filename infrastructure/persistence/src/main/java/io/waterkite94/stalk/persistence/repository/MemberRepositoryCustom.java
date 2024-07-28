package io.waterkite94.stalk.persistence.repository;

import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto;

public interface MemberRepositoryCustom {

	void updateInformation(String memberId, UpdateMemberProfileDto updateMemberProfileDto);

	void updatePassword(String email, String password);

	void updateProfileImageUrl(String memberId, String profileImageUrl);

	void updateInactiveStatus(String memberId);
}
