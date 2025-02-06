package shop.shopBE.domain.product.request;

import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.productdetail.request.UpdateProductDetails;
import shop.shopBE.domain.productdetail.response.ProductDetails;
import shop.shopBE.domain.productimage.response.ImgInforms;

import java.util.List;
import java.util.Map;

public record UpdateProductReq(

        String productName, //상품이름
        ImgInforms deletedMainImgInforms, //삭제된 메인이미지 정보. id ,url
        List<ImgInforms> deletedSideImgInforms, //삭제된 사이드 이미지와
        List<Long> deletedProductDetailIds,     // 삭제할 상품상세
        String description,                     // update할 description
        int price,                              // update할 price
        PersonCategory personCategory,          // update할 personcategory
        ProductCategory productCategory,        // update할 productcategory
        SeasonCategory seasonCategory,          // update할 seasoncategory
        List<UpdateProductDetails> updateProductDetailsInforms, // update할 productDetails

        Map<Integer, Integer> sizeAndQuantity// 추가할 productDetails -> productDetails에 추가할 사이즈와 사이즈별 수량
) {
}
