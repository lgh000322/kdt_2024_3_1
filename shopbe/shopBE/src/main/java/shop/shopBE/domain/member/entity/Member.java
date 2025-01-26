package shop.shopBE.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.request.MemberUpdateInfo;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    // 휴대폰 번호로 자신의 정보 조회
    private String tel;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    // jwt 토큰에 넣어줄 값
    @Column(name = "sub", unique = true)
    private UUID sub;

    private LocalDateTime createdAt;

    private boolean authenticated;

    public static Member createDefaultMember(String username, String name, String email, Role role, boolean authenticated) {
        return Member.builder()
                .username(username)
                .name(name)
                .role(role)
                .email(email)
                .sub(UUID.randomUUID())
                .authenticated(authenticated)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void authenticateMember(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void updateMember(MemberUpdateInfo memberUpdateInfo) {
        this.gender=memberUpdateInfo.gender();
        this.tel=memberUpdateInfo.tel();
        this.role = memberUpdateInfo.role();
    }

}
