package shop.shopBE.domain.cartitem.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.cartitem.response.CartItemInform;


import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryCustom {

    Optional<List<CartItemInform>> findCartItemInfromsById(Long memberId, Pageable pageable);
}
