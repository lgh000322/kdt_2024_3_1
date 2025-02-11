package shop.shopBE.domain.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.cart.entity.Cart;
import shop.shopBE.domain.cart.repository.CartRepository;
import shop.shopBE.domain.cartitem.request.AddCartItemInform;
import shop.shopBE.domain.cartitem.request.DeleteCartItems;
import shop.shopBE.domain.cartitem.request.UpdateCartItemInform;
import shop.shopBE.domain.cartitem.response.CartItemInformResp;
import shop.shopBE.domain.cartitem.service.CartItemService;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.service.MemberService;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final MemberService memberService;
    private final ProductService productService;

    //카트 아이템 조회 메서드
    public List<CartItemInformResp> findCartItemList(Pageable pageable, Long memberId) {
        Member member = memberService.findById(memberId);

        //멤버의 장바구니가 없으면 만들어서반환 있으면있는것 반환
        Cart cart = findCartByMemberId(memberId).orElseGet(() -> createCart(member));

        //카트아이템 리스트 반환
        return cartItemService.findCartItemInformsByCartId(cart.getId(), pageable);
    }



    @Transactional
    // 장바구니에 상품추가 메서드 - 카트 서비스에서 관련정보를 모두 찾고 카트아이템서비스에서 장바구니에 상품추가.
    public void addCartItem(AddCartItemInform addCartItemInform, Long memberId) {

        //멤버아이디로 멤버찾음
        Member member = memberService.findById(memberId);
        //멤버의 장바구니가 없으면 만들어서반환 있으면있는것 반환
        Cart cart = findCartByMemberId(memberId).orElseGet(() -> createCart(member));
        // 상품 아이디로 상품을 찾음
        Product product = productService.findNonDeletedProductByProductId(addCartItemInform.productId());
        //장바구니에 상품 추가.
        cartItemService.addCartItem(addCartItemInform, product, cart);
    }


    @Transactional
    // 장바구니 상품 업데이트 트랜잭션은 cartItem에서 연다.
    public void updateCartItem(Long cartItemId,
                               UpdateCartItemInform updateCartItemInform) {

        // 장바구니 상품 정보수정
        cartItemService.updateCartItem(cartItemId, updateCartItemInform);
    }


    @Transactional
    // 장바구니 상품 제거 메서드 (1개)
    public void deleteOneCartItem(Long cartItemId) {
        // 카트아이템 이없을경우 예외를 터트림
        cartItemService.findById(cartItemId);
        // 아이템이 있을경우 카트아이템 제거
        cartItemService.deleteOneCartItem(cartItemId);
    }

    @Transactional
    // 장바구니 상줌 제거 메서드 (2개 이상)
    public void deleteMultipleCartItems(DeleteCartItems deleteCartItems) {
        findItemAndDelete(deleteCartItems);
    }


    // 멤버아이디를 통해서 카트를 찾는 메서드
    public Optional<Cart> findCartByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    //장바구니 생성 메서드
    @Transactional
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    //카트 생성
    private Cart createCart(Member member) {
        Cart cart = Cart.builder()
                .member(member)
                .build();

        saveCart(cart);
        return cart;
    }

    // 장바구니 상품들 제거 로직
    private void findItemAndDelete(DeleteCartItems deleteCartItems) {
        List<Long> cartItemIds = deleteCartItems.cartItemIds();
        // 리스트로 받아온 장바구니 상품 아이디를 순회하면서 장바구니 상품 제거.
        for (Long cartItemId : cartItemIds) {
            // 카트아이템 이없을경우 예외를 터트림
            cartItemService.findById(cartItemId);
            // 아이템이 있을경우 카트아이템 제거
            cartItemService.deleteOneCartItem(cartItemId);
        }
    }



}
