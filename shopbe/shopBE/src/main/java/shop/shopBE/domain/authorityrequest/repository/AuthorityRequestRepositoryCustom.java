package shop.shopBE.domain.authorityrequest.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListViewModel;

import java.util.List;
import java.util.Optional;

public interface AuthorityRequestRepositoryCustom {
    Optional<List<AuthorityResponseListViewModel>> findAuthorityRequests(Pageable pageable);

    Optional<AuthorityRequest> findByIdFetchJoin(Long authorityId);
}
