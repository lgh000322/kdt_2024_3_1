package shop.shopBE.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.shopBE.domain.cart.entity.Cart;


import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom  {

    Optional<Cart> findByMemberId(Long MemberId);
}
