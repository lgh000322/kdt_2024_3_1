package shop.shopBE.domain.product.repository;

import shop.shopBE.domain.product.response.ProductCardsViewModel;
import shop.shopBE.domain.product.response.ProductListViewModel;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    // 상품 카드 데이터를 리턴해주는 메소드
    ProductListViewModel getProductListViewModels(Long productId);

    // 메인페이지에 보여줄 상품정보 - 인기순 (likeCount로 내림차순)
    Optional<List<ProductCardsViewModel>> findMainProductCardsOderByLikeCountDesc();
}
