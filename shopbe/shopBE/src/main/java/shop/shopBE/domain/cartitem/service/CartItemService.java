package shop.shopBE.domain.cartitem.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.cart.entity.Cart;
import shop.shopBE.domain.cart.request.CartsPaging;
import shop.shopBE.domain.cart.service.CartService;
import shop.shopBE.domain.cartitem.entity.CartItem;
import shop.shopBE.domain.cartitem.exception.CartItemExceptionCode;
import shop.shopBE.domain.cartitem.repository.CartItemRepository;

import shop.shopBE.domain.cartitem.request.AddCartItemInform;
import shop.shopBE.domain.cartitem.request.UpdateCartItemInform;
import shop.shopBE.domain.cartitem.response.CartItemInform;
import shop.shopBE.domain.cartitem.response.CartItemInformResp;
import shop.shopBE.domain.member.service.MemberService;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.service.ProductService;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    // 카트아이템 조회 메서드
    public List<CartItemInformResp> findCartItemInformsByCartId(Long cartId, Pageable pageable) {

        // 카트아이템을 조회 없으면 404에러 반환
        List<CartItemInform> cartItemInforms = cartItemRepository.findCartItemInfromsById(cartId, pageable)
                .orElseThrow(() -> new CustomException(CartItemExceptionCode.CART_ITEM_EMPTY));

        return setCartItemInformRespData(cartItemInforms);
    }


    // 카트 아이템 추가 메서드
    @Transactional
    public void addCartItem(AddCartItemInform addCartItemInform, Product product, Cart cart) {

        CartItem cartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .itemSize(addCartItemInform.size())
                .itemCount(addCartItemInform.quantity())
                .itemPrice(addCartItemInform.itemPrice())
                .build();
        cartItemRepository.save(cartItem);
    }


    // 장바구니 상품 수정
    @Transactional
    public void updateCartItem(Long cartItemId, UpdateCartItemInform updateCartItemInform) {
        // 장바구니 상품을 찾고 없으면 예외를 터트림
        CartItem cartItem = findById(cartItemId);
        // 변경감지를 통한 장바구니 상품 수정.
        cartItem.updateCartItem(updateCartItemInform);
    }


    // 장바구니 상품 번호로 장바구니 상품 조회
    public CartItem findById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(CartItemExceptionCode.CART_ITEM_NOT_FOUND));
    }


    //장바구니 상품 1개 제거
    @Transactional
    public void deleteOneCartItem(Long cartItemId) {
        // 카트 아이템이 있으면 제거
        cartItemRepository.deleteById(cartItemId);
    }



    // 상품정보(판매중, 품절) 설정로직
    private List<CartItemInformResp> setCartItemInformRespData(List<CartItemInform> cartItemInforms) {

        List<CartItemInformResp> cartItemInformResps = new ArrayList<>();


        for (CartItemInform cartItemInform : cartItemInforms) {
            CartItemInformResp cartItemInformResp = new CartItemInformResp();

            if(cartItemInform.getSizeStock() > 0) {
                cartItemInformResp.addCartItemInformAndStatus(cartItemInform, "판매 중");
            } else {
                cartItemInformResp.addCartItemInformAndStatus(cartItemInform, "품절");
            }
            cartItemInformResps.add(cartItemInformResp);
        }


        return cartItemInformResps;
    }


}
