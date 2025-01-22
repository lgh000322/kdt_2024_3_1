package shop.shopBE.domain.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.likesitem.entity.QLikesItem;
import shop.shopBE.domain.product.entity.QProduct;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.domain.productimage.entity.QProductImage;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;

import static shop.shopBE.domain.likesitem.entity.QLikesItem.likesItem;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

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
}
