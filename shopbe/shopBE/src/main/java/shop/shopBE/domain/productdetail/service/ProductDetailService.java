package shop.shopBE.domain.productdetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.exception.ProductDetailExceptionCustom;
import shop.shopBE.domain.productdetail.repository.ProductDetailRepository;
import shop.shopBE.domain.productdetail.request.UpdateProductDetails;
import shop.shopBE.domain.productdetail.response.ProductDetails;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;


    // 프로덕트 조회메서드
    public ProductDetail findProductDetailById(Long productDetailId){
        return productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new CustomException(ProductDetailExceptionCustom.PRODUCT_DETAIL_NOT_FOUND));
    }

    // 상품상세 조회시 같이 보내줄 사이즈별 id와 size, stock정보를 리스트로 반환.
    public List<ProductDetails> findProductDetailsByProductId(Long productId) {
        return productDetailRepository
                .findProductDetailsByProductId(productId)
                .orElseThrow(() -> new CustomException(ProductDetailExceptionCustom.PRODUCT_DETAIL_NOT_FOUND));
    }



    // 상품상세정보 저장 메서드
    public void saveProductDetails(Product product, Map<Integer, Integer> sizeAndQuantity) {
        sizeBySave(product, sizeAndQuantity);
    }


    @Transactional
    //사이즈별 수량 변경메서드
    public void updateSizeAndStock(List<UpdateProductDetails> updateProductDetails) {
        for (UpdateProductDetails updateProductDetail : updateProductDetails) {
            ProductDetail productDetail = findProductDetailById(updateProductDetail.productDetailId());
            productDetail.plusSizeStock(updateProductDetail.quantityBySize());
        }
    }


    // 아이디별로 productDetail제거.
    public void deleteProductDetailByIds(List<Long> productDetailIds){

        if(productDetailIds == null) {
            return;
        }

        for (Long productDetailId : productDetailIds) {
            productDetailRepository.deleteById(productDetailId);
        }
    }


    // 사이즈별로 저장하는 메서드
    private void sizeBySave(Product product, Map<Integer, Integer> sizeAndQuantity) {
        Set<Integer> sizes = sizeAndQuantity.keySet();
        for (Integer size : sizes) {
            Integer quantity = sizeAndQuantity.get(size);
            ProductDetail productDetail = ProductDetail.createDefaultProductDetail(product, size, quantity);
            productDetailRepository.save(productDetail);
        }
    }





}
