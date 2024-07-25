package io.waterkite94.stalk.persistence.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto;
import io.waterkite94.stalk.domain.type.MemberStatus;
import io.waterkite94.stalk.domain.type.RoleLevel;
import io.waterkite94.stalk.persistence.IntegrationTestSupport;
import io.waterkite94.stalk.persistence.entity.MemberEntity;

class MemberPersistenceAdapterTest extends IntegrationTestSupport {

	@Autowired
	private MemberPersistenceAdapter memberPersistenceAdapter;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void before() {
		memberRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 정보를 저장합니다.")
	void saveMember() {
		// given
		Member member = createMember("email", "00011112222");

		// when
		Member savedMember = memberPersistenceAdapter.saveMember(member);

		// then
		assertThat(savedMember)
			.extracting("memberId", "username", "email", "phoneNumber", "introduction")
			.containsExactly(member.getMemberId(), member.getUsername(), member.getEmail(),
				member.getPhoneNumber(), member.getIntroduction());
	}

	@Test
	@Transactional
	@DisplayName(value = "이메일을 매개변수로 같은 회원 아이디를 조회합니다.")
	void findMemberByEmailOrPhoneNumber_email() {
		// given
		String email = "email";
		Member member = createMember(email, "00011112222");

		Member savedMember = memberPersistenceAdapter.saveMember(member);

		// when
		memberPersistenceAdapter.findMemberByEmailOrPhoneNumber(email, "11122223333");

		// then
		assertThat(savedMember)
			.extracting("memberId", "username", "email", "phoneNumber", "introduction")
			.containsExactly(member.getMemberId(), member.getUsername(), member.getEmail(),
				member.getPhoneNumber(), member.getIntroduction());
	}

	@Test
	@Transactional
	@DisplayName(value = "전화번호를 매개변수로 같은 회원 아이디를 조회합니다.")
	void findMemberByEmailOrPhoneNumber_phoneNumber() {
		// given
		String phoneNumber = "00011112222";
		Member member = createMember("email", phoneNumber);

		Member savedMember = memberPersistenceAdapter.saveMember(member);

		// when
		memberPersistenceAdapter.findMemberByEmailOrPhoneNumber("hello@gmail.com", phoneNumber);

		// then
		assertThat(savedMember)
			.extracting("memberId", "username", "email", "phoneNumber", "introduction")
			.containsExactly(member.getMemberId(), member.getUsername(), member.getEmail(),
				member.getPhoneNumber(), member.getIntroduction());
	}

	@Test
	@Transactional
	@DisplayName(value = "이메일을 통해 회원 엔티티를 조회합니다.")
	void findMemberByEmail() {
		// given
		String email = "email";
		Member member = createMember(email, "00011112222");
		Member savedMember = memberPersistenceAdapter.saveMember(member);

		assertThat(memberRepository.findAll().size()).isEqualTo(1);

		// when
		Member findMember = memberPersistenceAdapter.findMemberByEmail(email);

		// then
		assertThat(findMember).isEqualTo(savedMember);
	}

	@Test
	@DisplayName(value = "회원 정보를 수정합니다.")
	void updateMemberInformation() {
		// given
		String username = "username";
		String introduction = "introduction";
		Member member = createMemberUsernameAndIntroduction(username, introduction);

		Member savedMember = memberPersistenceAdapter.saveMember(member);
		//
		String changeUsername = "changeUsername";
		String changeInformation = "changeInformation";
		UpdateMemberInformationDto updatedMemberInformationDto = new UpdateMemberInformationDto(changeUsername,
			changeInformation);

		// when
		memberPersistenceAdapter.updateMemberInformation(Objects.requireNonNull(savedMember.getMemberId()),
			updatedMemberInformationDto);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findById(
			Objects.requireNonNull(savedMember.getId()));

		assertThat(findMemberOptional).isNotEmpty();
		assertThat(findMemberOptional.get())
			.extracting("username", "introduction")
			.containsExactly(changeUsername, changeInformation);
	}

	@Test
	@DisplayName(value = "비밀번호를 변경합니다.")
	void updatePassword() {
		// given
		Member member = createMember("waterkite94@gmail.com", "00011112222");
		Member savedMember = memberPersistenceAdapter.saveMember(member);

		assertThat(memberRepository.findAll().size()).isEqualTo(1);

		// when
		memberPersistenceAdapter.updatePassword(savedMember.getEmail(), "changedPassword");

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findById(
			Objects.requireNonNull(savedMember.getId()));

		assertThat(findMemberOptional).isPresent();
		assertThat(findMemberOptional.get().getPassword()).isNotEqualTo(savedMember.getPassword());
	}

	@Test
	@DisplayName(value = "회원 프로필 URL을 변경합니다.")
	void updateProfileImageUrl() {
		// given
		Member member = createMember("waterkite94@gmail.com", "00011112222");
		Member savedMember = memberPersistenceAdapter.saveMember(member);

		assertThat(memberRepository.findAll().size()).isEqualTo(1);

		// when
		memberPersistenceAdapter.updateProfileImageUrl(
			Objects.requireNonNull(savedMember.getMemberId()),
			"changedProfileImageUrl");

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findById(
			Objects.requireNonNull(savedMember.getId()));

		assertThat(findMemberOptional).isPresent();
		assertThat(findMemberOptional.get().getProfileImageUrl()).isNotEqualTo(savedMember.getProfileImageUrl());
	}

	@Test
	@DisplayName(value = "계정 상태를 비활성화로 변경합니다.")
	void updateStatusInactive() {
		// given
		Member member = createMember("waterkite94@gmail.com", "00011112222");
		Member savedMember = memberPersistenceAdapter.saveMember(member);

		assertThat(memberRepository.findAll().size()).isEqualTo(1);

		// when
		memberPersistenceAdapter.updateStatusInactive(Objects.requireNonNull(savedMember.getMemberId()));

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findById(
			Objects.requireNonNull(savedMember.getId()));

		assertThat(findMemberOptional).isPresent();
		assertThat(findMemberOptional.get().getMemberStatus()).isEqualTo(MemberStatus.INACTIVE);
	}

	private @NotNull Member createMember(String email, String phoneNumber) {
		return new Member(null, "memberId", "username", email, "password", phoneNumber, "introduction",
			null, RoleLevel.USER_GENERAL, MemberStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
	}

	private @NotNull Member createMemberUsernameAndIntroduction(String username, String introduction) {
		return new Member(null, "memberId", username, "waterkite94@gmail.com", "password", "00011112222", introduction,
			null, RoleLevel.USER_GENERAL, MemberStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
	}
}
