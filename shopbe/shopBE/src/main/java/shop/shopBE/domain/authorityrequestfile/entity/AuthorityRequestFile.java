package shop.shopBE.domain.authorityrequestfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthorityRequestFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originName;

    private String savedName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_request_id")
    private AuthorityRequest authorityRequest;

    public static AuthorityRequestFile createDefaultAuthorityRequestFile(String originName, String savedName, AuthorityRequest authorityRequest) {
        return AuthorityRequestFile.builder()
                .originName(originName)
                .savedName(savedName)
                .authorityRequest(authorityRequest)
                .build();
    }
}
