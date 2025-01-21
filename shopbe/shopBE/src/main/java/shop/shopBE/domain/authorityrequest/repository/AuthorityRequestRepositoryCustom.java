package shop.shopBE.domain.authorityrequest.repository;

import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListViewModel;

import java.util.List;
import java.util.Optional;

public interface AuthorityRequestRepositoryCustom {
    Optional<List<AuthorityResponseListViewModel>> findAuthorityRequests(int page, int size);
}
