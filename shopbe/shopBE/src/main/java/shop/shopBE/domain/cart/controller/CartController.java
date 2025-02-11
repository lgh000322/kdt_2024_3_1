package shop.shopBE.domain.cart.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.cart.service.CartService;
import shop.shopBE.domain.cartitem.request.AddCartItemInform;
import shop.shopBE.domain.cartitem.request.DeleteCartItems;
import shop.shopBE.domain.cartitem.request.UpdateCartItemInform;
import shop.shopBE.domain.cartitem.response.CartItemInformResp;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "장바구니", description = "장바구니 관련 API")
public class CartController {

    private final CartService cartService;

    // 장바구니 조회 메서드
    @GetMapping
    @Operation(summary = "장바구니 조회", description = "현재 로그인 한 회원의 장바구니 목록을 조회한다.")
    public ResponseEntity<ResponseFormat<List<CartItemInformResp>>> findAllCartItems(@PageableDefault Pageable pageable,
                                                                                     @AuthenticationPrincipal AuthToken authToken) {
        List<CartItemInformResp> cartItemList = cartService.findCartItemList(pageable, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("장바구니 아이템 조회 성공.", cartItemList));
    }

    // 장바구니 추가 메서드1
    @PostMapping
    @Operation(summary = "장바구니 아이템 추가", description = "현재 로그인 한 회원의 장바구니 상품을 추가한다.")
    public ResponseEntity<ResponseFormat<Void>> addCartItem(@RequestBody @Valid AddCartItemInform addCartItemInform,
                                                            @AuthenticationPrincipal AuthToken authToken) {
        cartService.addCartItem(addCartItemInform, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("장바구니 상품 추가 성공."));
    }

    // 장바구니 수정 메서드
    @PutMapping("/{cartItemId}")
    @Operation(summary = "장바구니 상품 정보 수정", description = "현재 로그인 한 회원의 장바구니 상품 정보를 변경한다.")
    public ResponseEntity<ResponseFormat<Void>> updateCartItem(@PathVariable("cartItemId") Long cartItemId,
                                                               @RequestBody @Valid UpdateCartItemInform updateCartItemInform) {
        cartService.updateCartItem(cartItemId, updateCartItemInform);
        return ResponseEntity.ok().body(ResponseFormat.of("장바구니 상품 정보 수정 성공."));
    }


    // 장바구니 상품 제거 (1개) 메서드
    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "단일 장바구니 상품 제거", description = "현재 로그인 한 회원의 단일 장바구니 상품을 제거한다.")
    public ResponseEntity<ResponseFormat<Void>> deleteOneCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteOneCartItem(cartItemId);
        return ResponseEntity.ok().body(ResponseFormat.of("장바구니 상품 제거 성공."));
    }

    // 장바구니 상품 제거 (여러개) requestBody에 장바구니ID를 여러개 받아옴.
    @DeleteMapping("/items")
    @Operation(summary = "복수의 장바구니 상품 제거", description = "현재 로그인 한 회원의 1개 이상의 장바구니 상품을 제거한다.")
    public ResponseEntity<ResponseFormat<Void>> deleteMultipleCartItems(@RequestBody @Valid DeleteCartItems deleteCartItems) {
        cartService.deleteMultipleCartItems(deleteCartItems);
        return ResponseEntity.ok().body(ResponseFormat.of("복수의 장바구니 상품 제거 성공."));
    }


}
