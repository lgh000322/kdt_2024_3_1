package shop.shopBE.domain.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.banner.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryCustom {

}
