package shop.shopBE.domain.product.service;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.exception.ProductExceptionCode;
import shop.shopBE.domain.product.repository.ProductRepository;
import shop.shopBE.domain.product.request.ProductPaging;
import shop.shopBE.domain.product.request.SortingOption;
import shop.shopBE.domain.product.response.ProductCardViewModel;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));
    }

    // 메인페이지 프로덕트 카드뷰 - 인기순(좋아요 숫자) 내림차순
    public List<ProductCardViewModel> findMainPageCardViews(ProductPaging productPaging) {

        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        List<ProductCardViewModel> mainProductCardViews = productRepository.findMainProductCardsOderByLikeCountDesc(pageable)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return mainProductCardViews;
    }

    // 시즌(여름, 겨울) 클릭시 조회 메서드 (인기순 조회)
    public List<ProductCardViewModel> findSeasonProductInforms(ProductPaging productPaging, SeasonCategory seasonCategory) {
        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        List<ProductCardViewModel> productCardViewModels = productRepository
                .findSeasonProductsOrderByLikeCountDesc(pageable, seasonCategory)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return productCardViewModels;
    }

    // 시즌 상품 옵션별 상품조회
    public List<ProductCardViewModel> findSeasonProductInformsByOption(ProductPaging productPaging,
                                                                       SeasonCategory seasonCategory,
                                                                       String option) {
        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        // 낮은가격순 조회일경우
        if(option.equals(SortingOption.LOW_PRICE.toString())){
            return productRepository
                    .findSeasonProductsOrderByPriceAsc(pageable, seasonCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 신상품순 조회일 경우
        if(option.equals(SortingOption.NEW_PRODUCT.toString())){
            return productRepository
                    .findSeasonProductsOrderByCreateAtDesc(pageable, seasonCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 판매량순 조회일경우
        if(option.equals(SortingOption.BEST_SELLERS.toString())){
            return productRepository
                    .findSeasonProductsOrderBySalesVolumeDesc(pageable, seasonCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 인기순 조회일경우
        if(option.equals(SortingOption.POPULAR.toString())){
            return productRepository.findSeasonProductsOrderByLikeCountDesc(pageable, seasonCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        //위 조건에 걸리지 않으면 예외처리
        throw new CustomException(ProductExceptionCode.INVALID_OPTION);
    }


    //사람(남, 여, 아동) 카테고리 - 상품카테고리별 상품 조회(인기순)
    public List<ProductCardViewModel> findPersonProductInformsByProductCategory(ProductPaging productPaging, PersonCategory personCategory, String productCategory) {

        Pageable pageable =  PageRequest.of(productPaging.page() - 1, productPaging.size());

        ProductCategory checkedProductCategory = validProductCategory(productCategory);

        List<ProductCardViewModel> productCardViewModels = productRepository
                .findPersonProductsOrderByLikeCountDesc(pageable, personCategory, checkedProductCategory)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return productCardViewModels;
    }


    public List<ProductListViewModel> getProductListViewModels(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getProductListViewModels)
                .toList();
    }

    // 상품 카테고리 확인 메서드
    private ProductCategory validProductCategory(String productCategory) {

        //프로덕트 카테고리 이넘값으로 배열을 만듦.
        ProductCategory[] productCategories = ProductCategory.values();

        // 파라미터로 받은 문자열과 일치하는 productCategory를 찾음
        for (ProductCategory category : productCategories) {
            if(category.toString().equals(productCategory)) {
                return category;
            }
        }

        // 없으면 에러를 터트려줌.
        throw new CustomException(ProductExceptionCode.INVALID_PRODUCT_CATEGORY);
    }







/*

    // 남자상품 - 상품카테고리별 조회(인기순)
    public List<ProductCardViewModel> findMenProductInformsByProductCategory(ProductPaging productPaging, String productCategory) {

        Pageable pageable =  PageRequest.of(productPaging.page() - 1, productPaging.size());

        ProductCategory checkedProductCategory = validProductCategory(productCategory);

        List<ProductCardViewModel> productCardViewModels = productRepository
                .findPersonProductsOrderByLikeCountDesc(pageable, PersonCategory.MEN, checkedProductCategory)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return productCardViewModels;
    }

    // 여성 상품 - 상품 카테고리별 조회 (인기순0
    public List<ProductCardViewModel> findWomenProductInformsByProductCategory(ProductPaging productPaging, String productCategory) {

        Pageable pageable =  PageRequest.of(productPaging.page() - 1, productPaging.size());

        ProductCategory checkedProductCategory = validProductCategory(productCategory);

        List<ProductCardViewModel> productCardViewModels = productRepository
                .findPersonProductsOrderByLikeCountDesc(pageable, PersonCategory.WOMEN, checkedProductCategory)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return productCardViewModels;
    }


    // 여름상품 옵션별 조회
    public List<ProductCardViewModel> findSummerProductInformsByOption(ProductPaging productPaging, String option) {

        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        // 낮은가격순 조회일경우
        if(option.equals(SortingOption.LOW_PRICE.toString())){
            return productRepository
                    .findSeasonProductsOrderByPriceAsc(pageable, SeasonCategory.SUMMER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 신상품순 조회일 경우
        if(option.equals(SortingOption.NEW_PRODUCT.toString())){
            return productRepository
                    .findSeasonProductsOrderByCreateAtDesc(pageable, SeasonCategory.SUMMER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 판매량순 조회일경우
        if(option.equals(SortingOption.BEST_SELLERS.toString())){
            return productRepository
                    .findSeasonProductsOrderBySalesVolumeDesc(pageable, SeasonCategory.SUMMER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 인기순 조회일경우
        if(option.equals(SortingOption.POPULAR.toString())){
            return productRepository.findSeasonProductsOrderByLikeCountDesc(pageable, SeasonCategory.SUMMER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        //위 조건에 걸리지 않으면 예외처리
        throw new CustomException(ProductExceptionCode.INVALID_OPTION);
    }


    public List<ProductCardViewModel> findWinterProductInformsByOption(ProductPaging productPaging, String option) {

        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        // 낮은가격순 조회일경우
        if(option.equals(SortingOption.LOW_PRICE.toString())){
            return productRepository
                    .findSeasonProductsOrderByPriceAsc(pageable, SeasonCategory.WINTER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 신상품순 조회일 경우
        if(option.equals(SortingOption.NEW_PRODUCT.toString())){
            return productRepository
                    .findSeasonProductsOrderByCreateAtDesc(pageable, SeasonCategory.WINTER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 판매량순 조회일경우
        if(option.equals(SortingOption.BEST_SELLERS.toString())){
            return productRepository
                    .findSeasonProductsOrderBySalesVolumeDesc(pageable, SeasonCategory.WINTER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 인기순 조회일경우
        if(option.equals(SortingOption.POPULAR.toString())){
            return productRepository.findSeasonProductsOrderByLikeCountDesc(pageable, SeasonCategory.WINTER)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        //위 조건에 걸리지 않으면 예외처리
        throw new CustomException(ProductExceptionCode.INVALID_OPTION);
    }


    // 여름 카테고리 클릭시 조회 메서드 (인기순 조회)
    public List<ProductCardViewModel> findSummerProductInforms(ProductPaging productPaging) {
        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        List<ProductCardViewModel> summerProductsOrderByLikeCountDesc = productRepository
                .findSeasonProductsOrderByLikeCountDesc(pageable, SeasonCategory.SUMMER)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return summerProductsOrderByLikeCountDesc;
    }

    // 겨울 카테고리 클릭시 조회 메서드 (인기순 조회)
    public List<ProductCardViewModel> findWinterProductInforms(ProductPaging productPaging) {
        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        List<ProductCardViewModel> productCardViewModels = productRepository
                .findSeasonProductsOrderByLikeCountDesc(pageable, SeasonCategory.WINTER)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return productCardViewModels;
    }
*/


}
