package shop.shopBE.domain.authorityrequest.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListModel;

import java.util.List;
import java.util.Optional;

public interface AuthorityRequestRepositoryCustom {
    Optional<List<AuthorityResponseListModel>> findAuthorityRequests(Pageable pageable,String name);

    Optional<AuthorityRequest> findByIdFetchJoin(Long authorityId);
}
