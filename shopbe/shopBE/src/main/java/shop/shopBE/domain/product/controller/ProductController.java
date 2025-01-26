package shop.shopBE.domain.product.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.shopBE.domain.product.request.ProductPaging;
import shop.shopBE.domain.product.response.ProductCardsViewModel;
import shop.shopBE.domain.product.service.ProductService;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {
    private final ProductService productService;

    // 메인페이지 상품조회 likeCount로 조회
    @GetMapping("/main")
    public ResponseEntity<ResponseFormat<List<ProductCardsViewModel>>> getMainPageProductView(@RequestBody @Valid ProductPaging productPaging){
        productService.findMaiPageCardViews();
    }


    // 상품 등록


    // 상품 제거



}
