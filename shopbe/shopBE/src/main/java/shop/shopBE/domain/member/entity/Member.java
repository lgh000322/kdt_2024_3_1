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

    @Column(name = "sub", unique = true)
    private String sub;

    private LocalDateTime createdAt;

    private boolean authenticated;

    public static Member createDefaultMember(String username, String name, String email, Role role, boolean authenticated) {
        return Member.builder()
                .username(username)
                .name(name)
                .role(role)
                .email(email)
                .sub(UUID.randomUUID().toString())
                .authenticated(authenticated)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void updateMember(String email, String name, Gender gender, String tel, boolean authenticated) {
        this.email=email;
        this.name = name;
        this.gender = gender;
        this.tel = tel;
        this.authenticated = authenticated;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

}
