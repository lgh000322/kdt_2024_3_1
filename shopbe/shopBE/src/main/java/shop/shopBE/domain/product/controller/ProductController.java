package shop.shopBE.domain.product.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.product.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {
    private final ProductService productService;

    // 메인페이지 상품조회 likeCount로 조회



    // 상품 등록


    // 상품 제거



}
