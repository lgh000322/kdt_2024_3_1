package shop.shopBE.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.QProduct;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.request.SortingOption;
import shop.shopBE.domain.product.response.ProductCardViewModel;
import shop.shopBE.domain.product.response.ProductInformsModelView;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.domain.productimage.entity.QProductImage;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.likesitem.entity.QLikesItem.likesItem;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ProductListViewModel getProductListViewModels(Long productId) {
        return queryFactory
                .select(Projections.constructor(ProductListViewModel.class,
                        product.id,
                        likesItem.id,
                        productImage.savedName,
                        product.productName
                ))
                .from(product)
                .innerJoin(productImage).on(product.id.eq(productImage.product.id))
                .where(productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .fetchOne();
    }


    // 메인페이지 프로덕트뷰 - 인기순으로 desc하여 반환.
    @Override
    public Optional<List<ProductCardViewModel>> findMainProductCardsOderByLikeCountDesc(Pageable pageable) {
        List<ProductCardViewModel> productCardViewModels = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(productImage.productImageCategory.eq(ProductImageCategory.MAIN), product.isDeleted.eq(false))
                .orderBy(product.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(productCardViewModels);
    }

    // 시즌별 상품 조회 메서드 - 인기순 desc
    @Override
    public Optional<List<ProductCardViewModel>> findSeasonProductsOrderByLikeCountDesc(Pageable pageable,
                                                                                       SeasonCategory seasonCategory) {

        List<ProductCardViewModel> productCardVies = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(seasonCategoryCondition(seasonCategory),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        product.isDeleted.eq(false))
                .orderBy(product.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return Optional.ofNullable(productCardVies);
    }

    // 시즌별 상품 조회 메서드 - 판매량순
    @Override
    public Optional<List<ProductCardViewModel>> findSeasonProductsOrderBySalesVolumeDesc(Pageable pageable,
                                                                                         SeasonCategory seasonCategory) {
        List<ProductCardViewModel> productCardVies = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(seasonCategoryCondition(seasonCategory),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        product.isDeleted.eq(false))
                .orderBy(product.salesVolume.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return Optional.ofNullable(productCardVies);
    }

    // 시즌별 상품 조회 메서드 - 신상품순(입고순)
    @Override
    public Optional<List<ProductCardViewModel>> findSeasonProductsOrderByCreateAtDesc(Pageable pageable,
                                                                                      SeasonCategory seasonCategory) {
        List<ProductCardViewModel> productCardVies = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(seasonCategoryCondition(seasonCategory),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        product.isDeleted.eq(false))
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return Optional.ofNullable(productCardVies);
    }

    // 시즌별 상품 조회 메서드 - 낮은가격순
    @Override
    public Optional<List<ProductCardViewModel>> findSeasonProductsOrderByPriceAsc(Pageable pageable, SeasonCategory seasonCategory) {
        List<ProductCardViewModel> productCardVies = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(seasonCategoryCondition(seasonCategory),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        product.isDeleted.eq(false))
                .orderBy(product.price.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return Optional.ofNullable(productCardVies);
    }


    // 사람 카테고리별 상품조회 - 좋아요순
    @Override
    public Optional<List<ProductCardViewModel>> findPersonProductsOrderByLikeCountDesc(Pageable pageable,
                                                                                       PersonCategory personCategory,
                                                                                       ProductCategory productCategory) {

        List<ProductCardViewModel> productCardViewModels = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(personCategoryCondition(personCategory)
                        ,(product.productCategory.eq(productCategory))
                        ,(productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                        ,product.isDeleted.eq(false))
                .orderBy(product.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(productCardViewModels);
    }

    // 사람 카테고리별 상품조회 - 판매량
    @Override
    public Optional<List<ProductCardViewModel>> findPersonProductsOrderBySalesVolumeDesc(Pageable pageable,
                                                                                         PersonCategory personCategory,
                                                                                         ProductCategory productCategory) {
        List<ProductCardViewModel> productCardViewModels = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(personCategoryCondition(personCategory),
                        (product.productCategory.eq(productCategory)),
                        (productImage.productImageCategory.eq(ProductImageCategory.MAIN)),
                        product.isDeleted.eq(false))
                .orderBy(product.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(productCardViewModels);
    }

    // 사람 카테고리별 상품조회 - 신상품순
    @Override
    public Optional<List<ProductCardViewModel>> findPersonProductsOrderByCreateAtDesc(Pageable pageable, PersonCategory personCategory, ProductCategory productCategory) {
        List<ProductCardViewModel> productCardViewModels = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(personCategoryCondition(personCategory),
                        (product.productCategory.eq(productCategory)),
                        (productImage.productImageCategory.eq(ProductImageCategory.MAIN)),
                        product.isDeleted.eq(false))
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(productCardViewModels);
    }

    // 사람 카테고리별 상품조회 - 낮은가격순
    @Override
    public Optional<List<ProductCardViewModel>> findPersonProductsOrderByPriceAsc(Pageable pageable, PersonCategory personCategory, ProductCategory productCategory) {
        List<ProductCardViewModel> productCardViewModels = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(personCategoryCondition(personCategory),
                        (product.productCategory.eq(productCategory)),
                        (productImage.productImageCategory.eq(ProductImageCategory.MAIN)),
                        product.isDeleted.eq(false))
                .orderBy(product.price.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(productCardViewModels);
    }

    // 판매자 아이디로 등록된 상품 리스트를 찾아옴.
    @Override
    public Optional<List<Long>> findRegisteredProductsBySellerId(Long sellerId) {

        List<Long> productIds = queryFactory.select(Projections.constructor(Long.class,
                        product.id))
                .from(product)
                .where(product.member.id.eq(sellerId),
                        product.isDeleted.eq(false))
                .fetch();

        return Optional.ofNullable(productIds);
    }


    // 등록된 상품의 판매자id와 입력받은 판매자 아이디가 일치하는 상품을 가져옴.
    @Override
    public Optional<Product> findProductIdByProductIdAndSellerId(Long productId, Long sellerId) {
        Product findProduct = queryFactory.select(product)
                .from(product)
                .where(product.id.eq(productId), product.member.id.eq(sellerId), product.isDeleted.eq(false))
                .fetchOne();

        return Optional.ofNullable(findProduct);
    }

    @Override
    public Optional<List<ProductCardViewModel>> findSalesListBySellerId(Pageable pageable, Long sellerId) {

        List<ProductCardViewModel> salesList = queryFactory.select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(product.member.id.eq(sellerId), product.isDeleted.eq(false))
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return Optional.ofNullable(salesList);
    }

    @Override
    public Optional<ProductInformsModelView> findProductInformsByProductId(Long productId) {

        ProductInformsModelView productInform = queryFactory
                .select(Projections.constructor(ProductInformsModelView.class,
                        product.id,
                        product.productName,
                        productImage.savedName,
                        null,
                        product.price,
                        product.personCategory,
                        product.likeCount,
                        product.createdAt,
                        product.description,
                        product.totalStock,
                        null
                ))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .where(product.id.eq(productId), product.isDeleted.eq(false))
                .fetchOne();

        return Optional.ofNullable(productInform);
    }


    // 시즌 카테고리가 null 일경우 모든상품, null이 아닐경우 여름과 겨울중 하나와 모든시즌 조회.
    private BooleanExpression seasonCategoryCondition(SeasonCategory seasonCategory) {
        if (seasonCategory == null) {
            return product.seasonCategory.eq(SeasonCategory.ALL_SEASON);
        }
        return product.seasonCategory.eq(seasonCategory)
                .or(product.seasonCategory.eq(SeasonCategory.ALL_SEASON));
    }

    // 사람 카테고리가 null 일경우 남여공용의 상품,
    // 아동일 경우 아동 카테고리만,
    // 남성 또는 여성일경우 남성 또는 여성 + 남여공용 카테고리.
    private BooleanExpression personCategoryCondition(PersonCategory personCategory) {
        if (personCategory == null) {
            return product.personCategory.eq(PersonCategory.ALL_PERSON);
        }

        if (personCategory == PersonCategory.CHILDREN) {
            return product.personCategory.eq(PersonCategory.CHILDREN);
        }

        return product.personCategory.eq(personCategory)
                .or(product.personCategory.eq(PersonCategory.ALL_PERSON));
    }
}
