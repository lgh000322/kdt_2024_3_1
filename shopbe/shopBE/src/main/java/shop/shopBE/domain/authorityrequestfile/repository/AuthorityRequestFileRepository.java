package shop.shopBE.domain.authorityrequestfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.authorityrequestfile.entity.AuthorityRequestFile;

public interface AuthorityRequestFileRepository extends JpaRepository<AuthorityRequestFile, Long>, AuthorityRequestFileRepositoryCustom {

}
