package shop.shopBE.domain.product.repository;

import shop.shopBE.domain.product.response.ProductListViewModel;

import java.util.List;

public interface ProductRepositoryCustom {

    // 상품 카드 데이터를 리턴해주는 메소드
    ProductListViewModel getProductListViewModels(Long productId);
}
