package shop.shopBE.domain.cartitem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.cart.service.CartService;
import shop.shopBE.domain.cartitem.exception.CartItemExceptionCode;
import shop.shopBE.domain.cartitem.repository.CartItemRepository;
import shop.shopBE.domain.cartitem.request.CartsPaging;
import shop.shopBE.domain.cartitem.response.CartItemInform;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;


    // 카트아이템 조회 서비스
    public List<CartItemInform> findCartItemInformsByCartId(Long memberId, CartsPaging cartsPaging) {

        Long cartId = cartService.findByMemberId(memberId).getId();

        // 페이징 정보
        Pageable pageable = PageRequest.of(cartsPaging.page() - 1, cartsPaging.size());

        List<CartItemInform> cartItemInforms = cartItemRepository.findCartItemInfromsById(cartId, pageable)
                .orElseThrow(() -> new CustomException(CartItemExceptionCode.CART_ITEM_NOT_FOUND));

        setProductStatus(cartItemInforms);

        return cartItemInforms;
    }

    private void setProductStatus(List<CartItemInform> cartItemInforms) {
        for (CartItemInform cartItemInform : cartItemInforms) {
            if(cartItemInform.getSizeStock() > 0) {
                cartItemInform.setProductStatus("판매 중");
            } else {
                cartItemInform.setProductStatus("품절");
            }
        }
    }


}
