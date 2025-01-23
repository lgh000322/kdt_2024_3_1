package shop.shopBE.domain.productdetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.exception.ProductDetailExceptionCustom;
import shop.shopBE.domain.productdetail.repository.ProductDetailRepository;
import shop.shopBE.global.exception.custom.CustomException;

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
}
