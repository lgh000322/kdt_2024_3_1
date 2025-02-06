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
                       product.id,        // 상품 번호
                        cartItem.id,            // 장바구니 상품 번호
                        productImage.savedName, // 이미지 url
                        null,                   // 프로덕트 상태 (판매중, 품절) 해당 데이터는 서비스에서 설정
                        product.productName,    // 상품이름
                        productDetail.shoesSize, // 상품(신발)사이즈
                        productDetail.sizeStock, // 상품 사이즈별 수량
                        cartItem.itemCount,      // 장바구니 아이템수량 (사용자가 장바구니에 담은 상품수량)
                        cartItem.itemPrice))     // 장바구니 아이템 가격(상품수량 X 가격)
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
