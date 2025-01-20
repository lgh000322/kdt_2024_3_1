package shop.shopBE.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    // jwt 토큰에 넣어줄 값
    @Column(name = "sub", unique = true)
    private UUID sub;

    public static Member createDefaultMember(String username, String name, String email, Role role) {
        return Member.builder()
                .username(username)
                .name(name)
                .role(role)
                .email(email)
                .sub(UUID.randomUUID())
                .build();
    }

}
