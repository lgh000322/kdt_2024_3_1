package shop.shopBE.domain.cartitem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.cart.entity.Cart;
import shop.shopBE.domain.cart.service.CartService;
import shop.shopBE.domain.cartitem.request.CartsPaging;
import shop.shopBE.domain.cartitem.response.CartItemInform;

import shop.shopBE.domain.cartitem.service.CartItemService;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/cart")
@RestController
@Tag(name = "장바구니 아이템", description = "장바구니 아이템 관련 API")
public class CartItemController {

    private final CartItemService cartItemService;


    @GetMapping("/items")
    @Operation(summary = "찜 아이템 조회", description = "현재 로그인 한 회원의 장바구니 아이템 목록을 조회한다.")
    public ResponseEntity<ResponseFormat<List<CartItemInform>>> getCartItems(@RequestBody @Valid CartsPaging cartsPaging,
                                                                             @AuthenticationPrincipal AuthToken authToken) {

        List<CartItemInform> cartItemInforms= cartItemService.findCartItemInformsByCartId(authToken.getId(), cartsPaging);


        return ResponseEntity.ok().body(ResponseFormat.of("장바구니 아이템 조회 성공.", cartItemInforms));
    }



}
