package shop.shopBE.domain.productdetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.exception.ProductDetailExceptionCustom;
import shop.shopBE.domain.productdetail.repository.ProductDetailRepository;
import shop.shopBE.domain.productdetail.response.ProductDetails;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;


    // 프로덕트 조회메서드
    public ProductDetail findSizeStockById(Long productDetailId){
        return productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new CustomException(ProductDetailExceptionCustom.PRODUCT_DETAIL_NOT_FOUND));
    }

    // 상품상세 조회시 같이 보내줄 사이즈별 id와 size, stock정보를 리스트로 반환.
    public List<ProductDetails> findProductDetailsByProductId(Long productId) {
        return productDetailRepository
                .findProductDetailsByProductId(productId)
                .orElseThrow(() -> new CustomException(ProductDetailExceptionCustom.PRODUCT_DETAIL_NOT_FOUND));
    }

}
