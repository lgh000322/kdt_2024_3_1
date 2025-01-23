package shop.shopBE.domain.cartitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.cartitem.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemRepositoryCustom {

}
