package io.waterkite94.stalk.persistence.entity;

import io.waterkite94.stalk.domain.vo.RoleLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "member_id", columnDefinition = "varchar(60)", nullable = false, unique = true)
	private String memberId;

	@Column(name = "username", columnDefinition = "varchar(60)", nullable = false)
	private String username;

	@Column(name = "email", columnDefinition = "varchar(255)", nullable = false, unique = true)
	private String email;

	@Column(name = "password", columnDefinition = "varchar(60)", nullable = false)
	private String password;

	@Column(name = "phone_number", columnDefinition = "varchar(60)", nullable = false, unique = true)
	private String phoneNumber;

	@Column(name = "introduction", columnDefinition = "varchar(255)", nullable = false)
	private String introduction;

	@Column(name = "profile_image_url", columnDefinition = "varchar(255)")
	private String profileImageUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_level", columnDefinition = "varchar(30)", nullable = false)
	private RoleLevel roleLevel;

	@Builder
	private MemberEntity(String memberId, String username, String email, String password, String phoneNumber,
		String introduction, String profileImageUrl, RoleLevel roleLevel) {
		this.memberId = memberId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.introduction = introduction;
		this.profileImageUrl = profileImageUrl;
		this.roleLevel = roleLevel;
	}
}
