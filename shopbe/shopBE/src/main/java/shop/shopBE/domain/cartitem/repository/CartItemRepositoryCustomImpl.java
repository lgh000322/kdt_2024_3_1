package shop.shopBE.domain.cartitem.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.cartitem.response.CartItemInform;
import shop.shopBE.domain.productdetail.entity.QProductDetail;
import shop.shopBE.domain.productimage.entity.QProductImage;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.cartitem.entity.QCartItem.cartItem;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productdetail.entity.QProductDetail.productDetail;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@RequiredArgsConstructor
public class CartItemRepositoryCustomImpl implements CartItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<CartItemInform>> findCartItemInfromsById(Long cartId, Pageable pageable) {
        List<CartItemInform> informs = queryFactory
                .select(Projections.constructor(CartItemInform.class,
                       product.id,
                        cartItem.id,
                        productImage.savedName,
                        null,
                        product.productName,
                        productDetail.shoesSize,
                        productDetail.sizeStock,
                        cartItem.itemCount,
                        cartItem.itemPrice))
                .from(cartItem)
                .join(product)
                .on(cartItem.product.eq(product))
                .join(productDetail)
                .on(productDetail.product.eq(product))
                .join(productImage)
                .on(productImage.product.eq(product))
                .where(cartItem.cart.id.eq(cartId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(informs);
    }

}
