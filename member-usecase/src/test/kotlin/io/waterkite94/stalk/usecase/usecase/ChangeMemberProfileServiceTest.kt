package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto
import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.whenever

class ChangeMemberProfileServiceTest : IntegrationTestSupport() {
    @InjectMocks
    lateinit var changeMemberProfileService: ChangeMemberProfileService

    @Test
    @DisplayName(value = "회원 정보를 변경하는 dto를 Respository에 전달합니다.")
    fun changeMemberProfile() {
        // given
        val memberId: String = "memberId"
        val updateDto = createUpdateDto()

        doNothing().whenever(memberPersistencePort).updateMemberInformation(memberId, updateDto)

        // when
        val changedMemberDto = changeMemberProfileService.changeMemberProfile(memberId, updateDto)

        // then
        assertThat(changedMemberDto)
            .extracting("username", "introduction")
            .contains(updateDto.username, updateDto.introduction)
    }

    private fun createUpdateDto() = UpdateMemberInformationDto("username", "introduction")
}
