package shop.shopBE.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.likes.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
}
