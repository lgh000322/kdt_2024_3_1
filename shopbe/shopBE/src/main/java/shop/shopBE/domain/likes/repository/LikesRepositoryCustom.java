package shop.shopBE.domain.likes.repository;


import shop.shopBE.domain.likes.entity.Likes;

import java.util.Optional;

public interface LikesRepositoryCustom {
    Optional<Likes> findLikesIdByMemberId(Long memberId);
}
