package shop.shopBE.domain.product.service;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.likes.exception.LikesExceptionCode;
import shop.shopBE.domain.likesitem.exception.LikesItemExceptionCode;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.domain.member.service.MemberService;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.exception.ProductExceptionCode;
import shop.shopBE.domain.product.repository.ProductRepository;
import shop.shopBE.domain.product.request.*;
import shop.shopBE.domain.product.response.ProductCardViewModel;
import shop.shopBE.domain.product.response.ProductInformsModelView;
import shop.shopBE.domain.product.response.ProductInformsResp;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.request.UpdateProductDetails;
import shop.shopBE.domain.productdetail.response.ProductDetails;
import shop.shopBE.domain.productdetail.service.ProductDetailService;
import shop.shopBE.domain.productimage.response.ImgInforms;
import shop.shopBE.domain.productimage.service.ProductImageService;
import shop.shopBE.global.exception.custom.CustomException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailService productDetailService;
    private final ProductImageService productImageService;
    private final MemberService memberService;

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));
    }

    public Product findNonDeletedProductByProductId(Long productId) {
        return productRepository.findNonDeletedProductByProductId(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));
    }



    // 조회시 상품카드를 조회하는 메서드
    public List<ProductCardViewModel> findProductCardViewsByCategorys(Pageable pageable, SeasonCategory seasonCategory, PersonCategory personCategory, ProductCategory productCategory, SortingOption sortingOption, String keyword) {

        return getFilteredProductCardViewsByCategorys(pageable, seasonCategory, personCategory, productCategory, sortingOption, keyword);
    }




    // 상품의 상세페이지 정보들을 조회하는 메서드
    // 리스트로 받아야하는 sideImageUrl과 productDetail의 사이즈별 id, 사이즈, 사이즈별 재고는 외부에서 입력받음
    public ProductInformsModelView findProductDetailsByProductId(Long productId) {
        ProductInformsResp productInformsResp = productRepository
                .findProductInformsByProductId(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));

        List<ImgInforms> sideImgInforms = productImageService.findSideImgInformsByProductId(productId);  // 사이드 이미지 정보들 이미지 아이디, 이미지 url 가져옴
        List<ProductDetails> productDetailsList = productDetailService.findProductDetailsByProductId(productId);

        return setProductInformsModelView(productInformsResp, sideImgInforms, productDetailsList);
    }


    // 판매자의 상품등록리스트를 가져온다.
    public List<ProductCardViewModel> findSalesListCardView(Pageable pageable, Long sellerId) {

        return productRepository
                .findSalesListBySellerId(pageable, sellerId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
    }

    // 판매자의 등록 상품id 조회 메서드
    public List<Long> findRegisteredProductsBySellerId(Long sellerId) {
        return productRepository.findRegisteredProductsBySellerId(sellerId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND_PRODUCT_BY_SELLER));
    }

    // 판매자가 등록한 상품Id와 판매자 id가 일치하는 상품을 조회하는 메서드
    public Product findProductByProductIdAndSellerId(Long productId, Long sellerId) {
        return productRepository.findProductIdByProductIdAndSellerId(productId, sellerId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND_PRODUCT_BY_SELLER));
    }



    public List<ProductListViewModel> getProductListViewModels(Long likesId) {
        return productRepository.getProductListViewModels(likesId)
                .orElseThrow(() -> new CustomException(LikesItemExceptionCode.LIKES_ITEM_EMPTY));
    }


    // 상품 추가 메서드
    @Transactional
    public void addProduct(MultipartFile mainImgFile, List<MultipartFile> sideImgFile, AddProductInforms addProductInforms, Long sellerId) {
        //seller를 찾음
        Member seller = memberService.findById(sellerId);

        //product저장.
        Product product = Product
                .createDefaultProduct(seller,
                        addProductInforms.productCategory(),
                        addProductInforms.personCategory(),
                        addProductInforms.seasonCategory(),
                        addProductInforms.productName(),
                        checkTotalStock(addProductInforms),
                        addProductInforms.price(),
                        addProductInforms.description(),
                        LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        //product이미지 저장
        productImageService.saveMainImg(mainImgFile, savedProduct);
        productImageService.saveSideImgs(sideImgFile, savedProduct);

        //product details 저장.
        productDetailService.saveProductDetails(savedProduct, addProductInforms.sizeAndQuantity());

    }

    //상품 하나 제거 메서드. - 논리적 삭제.
    @Transactional
    public void deleteOneProduct(Long productId, Long sellerId) {
        //상품의 판매자 정보와 일치하는 상품을 찾음 , 없으면 400 에러반환
        Product product = findProductByProductIdAndSellerId(productId, sellerId);
        // product의 isDeleted를 true로  바꿔준다.
        product.deleteProduct(true);
    }

    //여러개의 상품 제거 - 논리적 삭제.
    @Transactional
    public void deleteMultipleProducts(List<Long> productIds, Long sellerId) {
        // 리스트를 순회하면서 product삭제 isDeleted 필드 true로 변환.
        for (Long productId : productIds) {
            Product product  = findProductByProductIdAndSellerId(productId, sellerId);
            product.deleteProduct(true);
        }
    }


    //  상품 구매시 상품의 총수량과, 사이즈별 수량 감소, 판매량 증가.  -> 상품 주문시 사용
    public void minusTotalStockAndSizeStock (Long productDetailId, int sizeStock) {

        // 상품 상세와 상품을 가져옴.
        ProductDetail productDetail = productDetailService.findProductDetailById(productDetailId);
        Product product = productDetail.getProduct();

        productDetail.minusSizeStock(sizeStock);
        product.minusTotalStock(sizeStock);

        // 위에서 오류를 안터트리고 정상적으로 수량 감소할경우 판매량을 증가시킴.
        product.plusSalesVolume(sizeStock);
    }

    // 상품정보 update로직
    @Transactional
    public void updateProductInforms(Long productId,
                                     Long sellerId,
                                     MultipartFile updateMainImg,
                                     List<MultipartFile> updateSideImgs,
                                     UpdateProductReq updateProductReq) {

        int totalStock = 0;

        Product product = productRepository
                .findSellerProductByProductId(productId, sellerId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.INVALID_PRODUCT_BY_SELLER));


        // 프로덕트 디테일 업데이트 후, 총수량 반환.
        totalStock = updateProductDetailsAndReturnTotalStock(productId, updateProductReq, totalStock, product);

        // product업데이트
        product.updateProduct(updateProductReq.productName(),
                updateProductReq.description(),
                updateProductReq.price(),
                updateProductReq.personCategory(),
                updateProductReq.productCategory(),
                updateProductReq.seasonCategory(),
                totalStock
                );

        //productImg 업데이트
        productImageService.updateMainImg(product, updateMainImg, updateProductReq.deletedMainImgInforms());
        productImageService.updateSideImgs(product, updateSideImgs, updateProductReq.deletedSideImgInforms());
    }



    // =======================================검증 로직 ========================================================//


    private ProductInformsModelView setProductInformsModelView(ProductInformsResp productInformsResp, List<ImgInforms> sideImgInforms, List<ProductDetails> productDetailsList) {
        ProductInformsModelView productInformsModelView = new ProductInformsModelView();

        productInformsModelView.setProductInformsResp(productInformsResp);
        productInformsModelView.setSideImgAndDetails(sideImgInforms, productDetailsList);

        return productInformsModelView;
    }


    private int seekTotalStock(Long productId, int totalStock) {
        List<ProductDetails> productDetailsByProductId = productDetailService.findProductDetailsByProductId(productId);
        for (ProductDetails productDetails : productDetailsByProductId) {
            totalStock += productDetails.quantityBySize();
        }

        return totalStock;
    }


    // totalstock확인.
    private int checkTotalStock(AddProductInforms addProductInforms) {
        int totalStock = 0;
        Collection<Integer> quantitys = addProductInforms.sizeAndQuantity().values();
        for (Integer quantity : quantitys) {
            totalStock += quantity;
        }
        return totalStock;
    }


    private List<ProductCardViewModel> getFilteredProductCardViewsByCategorys(Pageable pageable, SeasonCategory seasonCategory, PersonCategory personCategory, ProductCategory productCategory, SortingOption sortingOption, String keyword) {
        // 낮은가격순 조회일경우
        if(sortingOption.equals(SortingOption.LOW_PRICE)){
            return productRepository
                    .findProductCardViewsByCategorysOrderByPriceAsc(pageable, seasonCategory, personCategory, productCategory, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 신상품순 조회일 경우
        if(sortingOption.equals(SortingOption.NEW_PRODUCT)){
            return productRepository
                    .findProductCardViewsByCategorysOrderByCreateAtDesc(pageable, seasonCategory, personCategory, productCategory, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        // 판매량순 조회일경우
        if(sortingOption.equals(SortingOption.BEST_SELLERS)){
            return productRepository
                    .findProductCardViewsByCategorysOrderBySalesVolumesDesc(pageable, seasonCategory, personCategory, productCategory, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 인기순 조회일경우
        if(sortingOption.equals(SortingOption.POPULAR)){
            return productRepository
                    .findProductCardViewsByCategorysOrderByLikeCountDesc(pageable, seasonCategory, personCategory, productCategory, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        //위 조건에 걸리지 않으면 예외처리
        throw new CustomException(ProductExceptionCode.INVALID_OPTION);
    }


    private int updateProductDetailsAndReturnTotalStock(Long productId, UpdateProductReq updateProductReq, int totalStock, Product product) {

            // 프덕트 디테일 상품 사이즈별 수량 수정
            productDetailService.updateSizeAndStock(updateProductReq.updateProductDetailsInforms());

            // 업데이트한 상품 total stock확인.
            totalStock = seekTotalStock(productId, totalStock);

        return totalStock;
    }



}



    /*

    // 시즌 상품 옵션별 상품조회
    public List<ProductCardViewModel> findSeasonProductInformsByOption(ProductPaging productPaging,
                                                                       SeasonCategory seasonCategory,
                                                                       String option) {
        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());


        return getFilteredSeasonProductsByOption(seasonCategory, option, pageable);
    }

*/




    /*

    //사람(남, 여, 아동)아래 상품카테고리(슬리퍼, 부츠, 운동화 등등)아래 정렬조건(낮은가격, 인기순, 판매순 등) 조회
    public List<ProductCardViewModel> findPersonProductInformsByOption(ProductPaging productPaging,
                                                                       PersonCategory personCategory,
                                                                       String productCategory,
                                                                       String option) {

        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        ProductCategory checkedProductCategory = validProductCategory(productCategory);

        return getFilteredPersonProductsByOption(personCategory, option, pageable, checkedProductCategory);
    }

*/


      /*  public List<ProductCardViewModel> findProductBySearchAndOption(SearchProductReq searchProductReq, String option) {

        Pageable pageable = PageRequest.of(searchProductReq.page() - 1, searchProductReq.size());

        return getFilteredSearchProductByOption(searchProductReq.keyword(), option, pageable);
    }
*/

/*

    // 옵션(낮은가격순, 인기순등) 으로 필터링한 시즌상품을 반환
    private List<ProductCardViewModel> getFilteredSeasonProductsByOption(SeasonCategory seasonCategory, String option, Pageable pageable) {
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

    // 옵션으로 필터링한 사람카테고리별 상품 반환
    private List<ProductCardViewModel> getFilteredPersonProductsByOption(PersonCategory personCategory,
                                                                         String option,
                                                                         Pageable pageable,
                                                                         ProductCategory checkedProductCategory) {
        // 낮은가격순 조회일경우
        if(option.equals(SortingOption.LOW_PRICE.toString())){
            return productRepository
                    .findPersonProductsOrderByPriceAsc(pageable, personCategory, checkedProductCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 신상품순 조회일 경우
        if(option.equals(SortingOption.NEW_PRODUCT.toString())){
            return productRepository
                    .findPersonProductsOrderByCreateAtDesc(pageable, personCategory, checkedProductCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 판매량순 조회일경우
        if(option.equals(SortingOption.BEST_SELLERS.toString())){
            return productRepository
                    .findPersonProductsOrderBySalesVolumeDesc(pageable, personCategory, checkedProductCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 인기순 조회일경우
        if(option.equals(SortingOption.POPULAR.toString())){
            return productRepository
                    .findPersonProductsOrderByLikeCountDesc(pageable, personCategory, checkedProductCategory)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        //위 조건에 걸리지 않으면 예외처리
        throw new CustomException(ProductExceptionCode.INVALID_OPTION);
    }


    // 옵션으로 필터링한 검색어관련 상품
    private List<ProductCardViewModel> getFilteredSearchProductByOption(String keyword, String option, Pageable pageable) {
        // 낮은가격순 조회일경우
        if(option.equals(SortingOption.LOW_PRICE.toString())){
            return productRepository
                    .findSearchProductsOrderByPriceAsc(pageable, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 신상품순 조회일 경우
        if(option.equals(SortingOption.NEW_PRODUCT.toString())){
            return productRepository
                    .findSearchProductsOrderByCreateAtDesc(pageable, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 판매량순 조회일경우
        if(option.equals(SortingOption.BEST_SELLERS.toString())){
            return productRepository
                    .findSearchProductsOrderBySalesVolumeDesc(pageable, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }
        // 인기순 조회일경우
        if(option.equals(SortingOption.POPULAR.toString())){
            return productRepository.findSearchProductsOrderByLikeCountDesc(pageable, keyword)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
        }

        //위 조건에 걸리지 않으면 예외처리
        throw new CustomException(ProductExceptionCode.INVALID_OPTION);
    }

*/

/* // 시즌(여름, 겨울) 클릭시 조회 메서드 (인기순 조회)
    public List<ProductCardViewModel> findSeasonProductInforms(ProductPaging productPaging, SeasonCategory seasonCategory) {
        Pageable pageable = PageRequest.of(productPaging.page() - 1, productPaging.size());

        return productRepository
                .findSeasonProductsOrderByLikeCountDesc(pageable, seasonCategory)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
    }


    //사람(남, 여, 아동) 카테고리 - 상품카테고리별 상품 조회(인기순)
    public List<ProductCardViewModel> findPersonProductInformsByProductCategory(ProductPaging productPaging, PersonCategory personCategory, String productCategory) {

        Pageable pageable =  PageRequest.of(productPaging.page() - 1, productPaging.size());

        ProductCategory checkedProductCategory = validProductCategory(productCategory);

        return productRepository
                .findPersonProductsOrderByLikeCountDesc(pageable, personCategory, checkedProductCategory)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
    }*/


/*

    // 상품 검색시 사용 메서드
    public List<ProductCardViewModel> findProductBySearch(SearchProductReq searchProductReq) {
        Pageable pageable = PageRequest.of(searchProductReq.page() - 1, searchProductReq.size());

        return productRepository
                .findProductCardViewByKeyword(pageable, searchProductReq.keyword())
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));
    }


*/


/*

    // 메인페이지 프로덕트 카드뷰 - 인기순(좋아요 숫자) 내림차순
    public List<ProductCardViewModel> findMainPageCardViews(Pageable pageable) {

        List<ProductCardViewModel> mainProductCardViews = productRepository.findMainProductCardsOderByLikeCountDesc(pageable)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_EMPTY));

        return mainProductCardViews;
    }

   */


/*   // 상품 카테고리 확인 메서드
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

*/