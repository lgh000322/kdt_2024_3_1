package shop.shopBE.domain.banner.repository;

import shop.shopBE.domain.banner.response.BannerResponse;

import java.util.List;
import java.util.Optional;

public interface BannerRepositoryCustom {
    Optional<List<BannerResponse>> getBanners();
}
