package shop.shopBE.domain.likesitem.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.likesitem.entity.LikesItem;
import shop.shopBE.domain.likesitem.entity.QLikesItem;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.likesitem.entity.QLikesItem.*;

@RequiredArgsConstructor
public class LikesItemRepositoryCustomImpl implements LikesItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<Long>> getLikesItems(Pageable pageable, Long likesId) {
        List<Long> productIds = queryFactory
                .select(likesItem.product.id)
                .from(likesItem)
                .where(likesItem.likes.id.eq(likesId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(productIds);
    }

    @Override
    public Optional<Long> findOneProductIdByLikesId(Long likesId) {

        Long productId = queryFactory
                .select(likesItem.product.id)
                .from(likesItem)
                .where(likesItem.likes.id.eq(likesId))
                .fetchOne();

        return Optional.ofNullable(productId);
    }


}
