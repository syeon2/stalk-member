package io.waterkite94.stalk.persistence.repository;

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto;

public interface MemberRepositoryCustom {

	void updateInformation(String memberId, UpdateMemberInformationDto updateMemberInformationDto);
}
