package shop.shopBE.domain.likesitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.likesitem.entity.LikesItem;

public interface LikesItemRepository extends JpaRepository<LikesItem, Long>, LikesItemRepositoryCustom {

}
