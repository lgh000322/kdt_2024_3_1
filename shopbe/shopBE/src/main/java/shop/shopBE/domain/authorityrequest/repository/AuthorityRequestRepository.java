package shop.shopBE.domain.authorityrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;

public interface AuthorityRequestRepository extends JpaRepository<AuthorityRequest, Long>, AuthorityRequestRepositoryCustom {

}
