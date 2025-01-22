package shop.shopBE.domain.banner.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.banner.entity.QBanner;
import shop.shopBE.domain.banner.response.BannerResponse;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.banner.entity.QBanner.banner;

@RequiredArgsConstructor
public class BannerRepositoryCustomImpl implements BannerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<BannerResponse>> getBanners() {
        List<BannerResponse> result = queryFactory
                .select(Projections.constructor(BannerResponse.class,
                        banner.id,
                        banner.savedImageName))
                .from(banner)
                .fetch();

        return Optional.ofNullable(result);
    }
}
