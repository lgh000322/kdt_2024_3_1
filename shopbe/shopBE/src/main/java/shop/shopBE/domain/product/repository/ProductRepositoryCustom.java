package shop.shopBE.domain.product.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.request.SortingOption;
import shop.shopBE.domain.product.response.ProductCardViewModel;
import shop.shopBE.domain.product.response.ProductInformsModelView;
import shop.shopBE.domain.product.response.ProductListViewModel;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {


    Optional<Product> findNonDeletedProductByProductId(Long productId);

    // 상품 카드 데이터를 리턴해주는 메소드
    ProductListViewModel getProductListViewModels(Long productId);

    // 메인페이지에 보여줄 상품정보 - 인기순 (likeCount로 내림차순)
    Optional<List<ProductCardViewModel>> findMainProductCardsOderByLikeCountDesc(Pageable pageable);

    //시즌별 상품 조회메서드 - 인기순
    Optional<List<ProductCardViewModel>> findSeasonProductsOrderByLikeCountDesc(Pageable pageable, SeasonCategory seasonCategory);

    // 시즌별 상품 조회 메서드 - 판매량순
    Optional<List<ProductCardViewModel>> findSeasonProductsOrderBySalesVolumeDesc(Pageable pageable, SeasonCategory seasonCategory);

    // 시즌별 상품 조회 메서드 - 신상품순(입고순)
    Optional<List<ProductCardViewModel>> findSeasonProductsOrderByCreateAtDesc(Pageable pageable, SeasonCategory seasonCategory);

    // 시즌별 상품 조회 메서드 - 낮은가격순
    Optional<List<ProductCardViewModel>> findSeasonProductsOrderByPriceAsc(Pageable pageable, SeasonCategory seasonCategory);

    
    // 사람(남자, 여자) + 전체 상품 카테고리별 조회 , 아동일경우 아동만 - 인기순
    Optional<List<ProductCardViewModel>> findPersonProductsOrderByLikeCountDesc(Pageable pageable, PersonCategory personCategory, ProductCategory productCategory);

    // 사람(남자, 여자) + 전체 상품 카테고리별 조회, 아동일경우 아동만  - 판매량순
    Optional<List<ProductCardViewModel>> findPersonProductsOrderBySalesVolumeDesc(Pageable pageable, PersonCategory personCategory, ProductCategory productCategory);

    // 사람(남자, 여자) + 전체 상품 카테고리별 조회 , 아동일경우 아동만 - 신상품순(입고순)
    Optional<List<ProductCardViewModel>> findPersonProductsOrderByCreateAtDesc(Pageable pageable, PersonCategory personCategory, ProductCategory productCategory);

    // 사람(남자, 여자) + 전체 상품 카테고리별 조회 , 아동일경우 아동만 - 낮은 가격순
    Optional<List<ProductCardViewModel>> findPersonProductsOrderByPriceAsc(Pageable pageable, PersonCategory personCategory, ProductCategory productCategory);

    // 판매자가 등록한 상품 조회
    Optional<List<Long>> findRegisteredProductsBySellerId(Long sellerId);

    // 판매자등록상품중 입력받은 상품과 일치하는 상품 조회하는 메서드
    Optional<Product> findProductIdByProductIdAndSellerId(Long productId, Long sellerId);

    // 판매자의 등록 상품 조회
    Optional<List<ProductCardViewModel>> findSalesListBySellerId(Pageable pageable, Long sellerId);

    // 상품 상세조회의 필드를 찾기위한 메서드
    Optional<ProductInformsModelView> findProductInformsByProductId(Long productId);

    // 검색어(이름)로 상품을 찾는 메서드
    Optional<List<ProductCardViewModel>> findProductCardViewByKeyword(Pageable pageable, String keyword);

    Optional<List<ProductCardViewModel>> findSearchProductsOrderByPriceAsc(Pageable pageable, String keyword);

    Optional<List<ProductCardViewModel>> findSearchProductsOrderByCreateAtDesc(Pageable pageable, String keyword);

    Optional<List<ProductCardViewModel>> findSearchProductsOrderBySalesVolumeDesc(Pageable pageable, String keyword);

    Optional<List<ProductCardViewModel>> findSearchProductsOrderByLikeCountDesc(Pageable pageable, String keyword);
}
