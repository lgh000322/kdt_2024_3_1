package shop.shopBE.domain.productimage.service;

import lombok.RequiredArgsConstructor;
import org.springdoc.webmvc.core.fn.SpringdocRouteBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.productimage.exception.ProductImageExceptionCode;
import shop.shopBE.domain.productimage.repository.ProductImageRepository;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public List<String> findSideImgsByProductId(Long productId){
        List<String> sideImgUrls =
                productImageRepository.findSideImgUrlsByProductId(productId)
                        .orElseThrow(() -> new CustomException(ProductImageExceptionCode.SIDE_IMAGE_NOT_FOUND));
        return sideImgUrls;
    }
}
