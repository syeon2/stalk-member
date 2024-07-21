package io.waterkite94.stalk.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.stalk.domain.model.Member;
import io.waterkite94.stalk.domain.type.RoleLevel;
import io.waterkite94.stalk.infrastructure.IntegrationTestSupport;

class MemberPersistenceAdapterTest extends IntegrationTestSupport {

	@Autowired
	private MemberPersistenceAdapter memberPersistenceAdapter;

	@Autowired
	private MemberJpaRepository memberJpaRepository;

	@BeforeEach
	void before() {
		memberJpaRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 정보를 저장합니다.")
	void save() {
		// given
		Member member = createMember("email", "00011112222");

		// when
		Member savedMember = memberPersistenceAdapter.save(member);

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

		Member savedMember = memberPersistenceAdapter.save(member);

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

		Member savedMember = memberPersistenceAdapter.save(member);

		// when
		memberPersistenceAdapter.findMemberByEmailOrPhoneNumber("hello@gmail.com", phoneNumber);

		// then
		assertThat(savedMember)
			.extracting("memberId", "username", "email", "phoneNumber", "introduction")
			.containsExactly(member.getMemberId(), member.getUsername(), member.getEmail(),
				member.getPhoneNumber(), member.getIntroduction());
	}

	private @NotNull Member createMember(String email, String phoneNumber) {
		return new Member(null, "memberId", "username", email, "password", phoneNumber, "introduction",
			null, RoleLevel.USER_GENERAL, LocalDateTime.now(), LocalDateTime.now());
	}
}
