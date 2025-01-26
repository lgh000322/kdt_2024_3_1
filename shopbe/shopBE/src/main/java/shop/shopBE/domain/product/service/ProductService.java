package shop.shopBE.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.exception.ProductExceptionCode;
import shop.shopBE.domain.product.repository.ProductRepository;
import shop.shopBE.domain.product.response.ProductCardsViewModel;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));
    }


    public List<ProductCardsViewModel> findMaiPageCardViews() {
        Optional<List<ProductCardsViewModel>> mainProductCardsOderByLikeCountDesc = productRepository.findMainProductCardsOderByLikeCountDesc();

    }

    public List<ProductListViewModel> getProductListViewModels(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getProductListViewModels)
                .toList();
    }

}
