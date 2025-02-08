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
import shop.shopBE.domain.product.response.ProductInformsResp;
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
    public Optional<Product> findNonDeletedProductByProductId(Long productId) {

        Product findProduct = queryFactory.select(product)
                .from(product)
                .where(
                        product.id.eq(productId),
                        product.isDeleted.eq(false)
                ).
                fetchOne();

        return Optional.ofNullable(findProduct);
    }

    @Override
    public Optional<Product> findSellerProductByProductId(Long productId, Long sellerId) {

        Product findSellerProduct = queryFactory.select(product)
                .from(product)
                .where(
                        product.id.eq(productId),
                        product.member.id.eq(sellerId),
                        product.isDeleted.eq(false)
                )
                .fetchOne();
        return Optional.ofNullable(findSellerProduct);
    }

    @Override
    public ProductListViewModel getProductListViewModels(Long productId) {
        return queryFactory
                .select(Projections.constructor(ProductListViewModel.class,
                        product.id,
                        likesItem.id,
                        productImage.savedName,
                        product.productName,
                        product.price
                ))
                .from(product)
                .innerJoin(productImage).on(product.id.eq(productImage.product.id))
                .where(
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        product.isDeleted.eq(false)
                )
                .fetchOne();
    }



    // 카테고리별 상품을 가져오는 메서드.
    @Override
    public Optional<List<ProductCardViewModel>> findProductCardViewsByCategorysOrderByLikeCountDesc(Pageable pageable,
                                                                                                    SeasonCategory seasonCategory,
                                                                                                    PersonCategory personCategory,
                                                                                                    ProductCategory productCategory,
                                                                                                    String keyword) {

        List<ProductCardViewModel> cardViews = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                                product.id,
                                productImage.savedName,
                                product.productName,
                                product.price
                                ))
                .from(product)
                .innerJoin(productImage)
                .on(productImage.product.eq(product))
                .where(andProductCategory(productCategory),
                        andPersonCategory(personCategory),
                        andSeasonCategory(seasonCategory),
                        productNameLike(keyword),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .orderBy(product.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return Optional.ofNullable(cardViews);
    }

    @Override
    public Optional<List<ProductCardViewModel>> findProductCardViewsByCategorysOrderByCreateAtDesc(Pageable pageable,
                                                                                                   SeasonCategory seasonCategory,
                                                                                                   PersonCategory personCategory,
                                                                                                   ProductCategory productCategory,
                                                                                                   String keyword) {

        List<ProductCardViewModel> cardViews = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price
                ))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(andProductCategory(productCategory),
                        andPersonCategory(personCategory),
                        andSeasonCategory(seasonCategory),
                        productNameLike(keyword),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return Optional.ofNullable(cardViews);
    }

    @Override
    public Optional<List<ProductCardViewModel>> findProductCardViewsByCategorysOrderBySalesVolumesDesc(Pageable pageable,
                                                                                                       SeasonCategory seasonCategory,
                                                                                                       PersonCategory personCategory,
                                                                                                       ProductCategory productCategory,
                                                                                                       String keyword) {
        List<ProductCardViewModel> cardViews = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price
                ))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(andProductCategory(productCategory),
                        andPersonCategory(personCategory),
                        andSeasonCategory(seasonCategory),
                        productNameLike(keyword),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .orderBy(product.salesVolume.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return Optional.ofNullable(cardViews);
    }

    @Override
    public Optional<List<ProductCardViewModel>> findProductCardViewsByCategorysOrderByPriceAsc(Pageable pageable,
                                                                                               SeasonCategory seasonCategory,
                                                                                               PersonCategory personCategory,
                                                                                               ProductCategory productCategory,
                                                                                               String keyword) {
        List<ProductCardViewModel> cardViews = queryFactory
                .select(Projections.constructor(ProductCardViewModel.class,
                        product.id,
                        productImage.savedName,
                        product.productName,
                        product.price
                ))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(andProductCategory(productCategory),
                        andPersonCategory(personCategory),
                        andSeasonCategory(seasonCategory),
                        productNameLike(keyword),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .orderBy(product.price.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return Optional.ofNullable(cardViews);
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
                .where(product.member.id.eq(sellerId),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        product.isDeleted.eq(false))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return Optional.ofNullable(salesList);
    }

    @Override
    public Optional<ProductInformsResp> findProductInformsByProductId(Long productId) {

        ProductInformsResp productInform = queryFactory
                .select(Projections.constructor(ProductInformsResp.class,
                        product.id,
                        product.productName,
                        productImage.id,
                        productImage.savedName,
                        product.price,
                        product.personCategory,
                        product.seasonCategory,
                        product.productCategory,
                        product.likeCount,
                        product.createdAt,
                        product.description,
                        product.totalStock
                ))
                .from(product)
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(product.id.eq(productId),
                        product.isDeleted.eq(false),
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .fetchOne();

        return Optional.ofNullable(productInform);
    }


    private BooleanExpression andPersonCategory(PersonCategory personCategory) {
        return personCategory == null
                ? null : product.personCategory.eq(personCategory);
    }

    private BooleanExpression andSeasonCategory(SeasonCategory seasonCategory) {
        return seasonCategory == null
                ? null : product.seasonCategory.eq(seasonCategory);
    }

    private BooleanExpression andProductCategory(ProductCategory productCategory) {
        return productCategory == null
                ? null : product.productCategory.eq(productCategory);
    }

    private BooleanExpression productNameLike(String keyword){
        return keyword == null ? null : product.productName.like("%" + keyword + "%");
    }
}

