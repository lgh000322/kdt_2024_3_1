package shop.shopBE.domain.authorityrequestfile.repository;

import shop.shopBE.domain.authorityrequestfile.request.AuthFileData;

import java.util.List;
import java.util.Optional;

public interface AuthorityRequestFileRepositoryCustom {
    Optional<List<AuthFileData>> findByAuthorityId(Long authorityId);
}
