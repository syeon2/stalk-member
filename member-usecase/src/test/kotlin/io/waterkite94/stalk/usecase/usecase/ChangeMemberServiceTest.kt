package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto
import io.waterkite94.stalk.domain.vo.MemberStatus
import io.waterkite94.stalk.domain.vo.RoleLevel
import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class ChangeMemberServiceTest : IntegrationTestSupport() {
    @InjectMocks
    lateinit var changeMemberService: ChangeMemberService

    @Test
    @DisplayName(value = "회원 정보를 변경하는 dto를 Respository에 전달합니다.")
    fun changeMemberProfile() {
        // given
        val memberId: String = "memberId"
        val updateDto = createUpdateDto()

        doNothing().whenever(memberPersistencePort).updateMemberInformation(memberId, updateDto)

        // when
        val changedMemberDto = changeMemberService.changeMemberProfile(memberId, updateDto)

        // then
        assertThat(changedMemberDto)
            .extracting("username", "introduction")
            .contains(updateDto.username, updateDto.introduction)
    }

    @Test
    @DisplayName(value = "이메일, 현재 비밀번호, 변경할 비밀번호, 변경할 비밀번호 확인을 받아 변경합니다.")
    fun changeMemberPassword() {
        // given
        val email = "test@test.com"
        val currentPassword = "currentPassword"
        val newPassword = "newPassword"
        val checkNewPassword = "newPassword"
        val encryptedPassword = "encryptedPassword"

        val member = createMemberDto(email)

        given(memberPersistencePort.findMemberByEmail(email)).willReturn(member)
        given(securityUtil.matchesPassword(currentPassword, "encryptedCurrentPassword")).willReturn(true)
        given(securityUtil.encryptPassword(newPassword)).willReturn(encryptedPassword)

        // when
        changeMemberService.changeMemberPassword(email, currentPassword, newPassword, checkNewPassword)

        // then
        verify(memberPersistencePort).updatePassword(email, encryptedPassword)
    }

    @Test
    @DisplayName(value = "회원 이메일을 가진 데이터가 Null이면 Exception을 반환합니다.")
    fun changeMemberPassword_findMemberByEmail_null() {
        // given
        val email = "test@example.com"
        val currentPassword = "currentPassword"
        val newPassword = "newPassword"
        val checkNewPassword = "newPassword"

        given(memberPersistencePort.findMemberByEmail(email)).willReturn(null)

        // when  // then
        assertThatThrownBy {
            changeMemberService.changeMemberPassword(email, currentPassword, newPassword, checkNewPassword)
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessage("Unable to find member by email: $email")
    }

    @Test
    @DisplayName(value = "조회한 회원 정보의 비밀번호와 현재 비밀번호가 일치하지 않으면 예외가 발생합니다.")
    fun changeMemberPassword_findMemberByEmail_notEquals() {
        // given
        val email = "test@test.com"
        val currentPassword = "currentPassword"
        val newPassword = "newPassword"
        val checkNewPassword = "newPassword"

        val member = createMemberDto(email)

        given(memberPersistencePort.findMemberByEmail(email)).willReturn(member)
        given(securityUtil.matchesPassword(currentPassword, "encryptedCurrentPassword")).willReturn(false)

        // when  // then
        assertThatThrownBy {
            changeMemberService.changeMemberPassword(email, currentPassword, newPassword, checkNewPassword)
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessage("Wrong password")
    }

    @Test
    @DisplayName(value = "변경할 비밀번호와 변경할 비밀번호를 확인하는 비밀번호가 일치하지 않으면 예외를 반환합니다.")
    fun changeMemberPassword_checkNewPasswordAndCheckNewPassword_notEquals() {
        // given
        val email = "test@test.com"
        val currentPassword = "currentPassword"
        val newPassword = "newPassword"
        val checkNewPassword = "notEqualsNewPassword"

        val member = createMemberDto(email)

        given(memberPersistencePort.findMemberByEmail(email)).willReturn(member)
        given(securityUtil.matchesPassword(currentPassword, "encryptedCurrentPassword")).willReturn(true)

        // when  // then
        assertThatThrownBy {
            changeMemberService.changeMemberPassword(email, currentPassword, newPassword, checkNewPassword)
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessage("check change passwords must match password")
    }

    @Test
    @DisplayName(value = "회원의 프로필 이미지 URL을 변경합니다.")
    fun changeProfileImageUrl() {
        // given
        val memberId = "memberId"
        val profileImageUrl = "profileImageUrl"

        doNothing().whenever(memberPersistencePort).updateProfileImageUrl(memberId, profileImageUrl)

        // when
        changeMemberService.changeProfileImageUrl(memberId, profileImageUrl)

        // then
        verify(memberPersistencePort).updateProfileImageUrl(memberId, profileImageUrl)
    }

    private fun createMemberDto(email: String) =
        Member(
            1L,
            "memberId",
            "username",
            email,
            "encryptedCurrentPassword",
            "00011112222",
            "introduction",
            "profileImageUrl",
            RoleLevel.USER_GENERAL,
            MemberStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

    private fun createUpdateDto() = UpdateMemberInformationDto("username", "introduction")
}
