package shop.shopBE.domain.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;

import java.util.*;

public record AddProductInforms(
        @NotNull
        String productName,     //상품 이름
        @NotNull
        int price,             //상품 가격
        @NotNull
        ProductCategory productCategory,  //상품 종류
        @NotNull
        PersonCategory personCategory,    //사람 종류 - 아동, 여성, 남성, 남여공용
        @NotNull
        SeasonCategory seasonCategory,    // 시즌 종류 - 여름, 겨울 , 모든 시즌
        String description, // 상품설명 -> 무조건 받지 않아도 됨.
        @NotNull
        Map<Integer, Integer> sizeAndQuantity  // 사이즈와 사이즈별 수량 (키 - 사이즈 , 밸류 - 사이즈별 수량)
) {
}
