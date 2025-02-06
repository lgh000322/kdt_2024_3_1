package shop.shopBE.domain.authorityrequest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthorityRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reasonToRegister;

    private boolean isAccepted;

    private LocalDateTime createAt;

    // 회원 권한 상승을 요청한 유저의 번호
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public static AuthorityRequest createDefaultAuthorityRequest(String reasonToRegister, Member member) {
        return AuthorityRequest.builder()
                .reasonToRegister(reasonToRegister)
                .isAccepted(false)
                .member(member)
                .createAt(LocalDateTime.now())
                .build();
    }

}
