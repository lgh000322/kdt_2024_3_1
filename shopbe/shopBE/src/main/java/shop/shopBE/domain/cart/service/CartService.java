package shop.shopBE.domain.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.cart.entity.Cart;
import shop.shopBE.domain.cart.exception.CartExceptionCode;
import shop.shopBE.domain.cart.repository.CartRepository;
import shop.shopBE.global.exception.custom.CustomException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;


    // 카트를 찾는 메서드
    public Cart findByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId).orElseThrow(() -> new CustomException(CartExceptionCode.CART_NOT_FOUND));
    }
}
