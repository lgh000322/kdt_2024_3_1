package shop.shopBE.domain.banner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originImageName;

    private String savedImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Banner createDefaultBanner(String originImageName, String savedImageName, Member member) {
        return Banner.builder()
                .originImageName(originImageName)
                .savedImageName(savedImageName)
                .member(member)
                .build();
    }
}
