package shop.shopBE.domain.likesitem.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.likesitem.entity.LikesItem;

import java.util.List;
import java.util.Optional;

public interface LikesItemRepositoryCustom {
    Optional<List<Long>> getLikesItems(Pageable pageable, Long likesId);
    Optional<Long> findOneProductIdByLikesId(Long likesId);
}
