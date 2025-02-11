package shop.shopBE.domain.orderproduct.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.shopBE.domain.member.entity.QMember;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.entity.QOrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.domain.productdetail.entity.QProductDetail;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.member.entity.QMember.member;
import static shop.shopBE.domain.orderhistory.entity.QOrderHistory.orderHistory;
import static shop.shopBE.domain.orderproduct.entity.QOrderProduct.orderProduct;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productdetail.entity.QProductDetail.productDetail;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@Slf4j
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<OrderProduct> findOrderProductByProductDetailId(Long productDetailId) {
        OrderProduct result = queryFactory
                .select(orderProduct)
                .from(orderProduct)
                .where(orderProduct.productDetail.id.eq(productDetailId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<OrderHistoryInfoResponse> findOrderHistoryInfoById(Long orderHistoryId) {
        return Optional.empty();
    }


    @Override
    public Optional<List<OrderProduct>> findOrderProductByOrderHistoryId(Long orderHistoryId) {
        List<OrderProduct> result = queryFactory
                .select(orderProduct)
                .from(orderProduct)
                .join(orderProduct.productDetail,productDetail).fetchJoin()
                .join(productDetail.product,product).fetchJoin()
                .where(orderProduct.orderHistory.id.eq(orderHistoryId))
                .fetch();

        return Optional.ofNullable(result);
    }



    @Override
    public Optional<List<OrderProductInfo>> findOrderProductInfoByOrderHistoryId(Long orderHistoryId) {
        List<OrderProductInfo> result = queryFactory
                .select(Projections.constructor(OrderProductInfo.class,
                        product.id,
                        product.productName,
                        member.name,
                        orderHistory.address,
                        orderHistory.tel,
                        orderProduct.currentDeliveryStatus,
                        productImage.savedName,
                        orderProduct.productTotalPrice
                ))
                .from(orderProduct)
                .leftJoin(orderHistory).on(orderProduct.orderHistory.id.eq(orderHistoryId))
                .leftJoin(productDetail).on(orderProduct.productDetail.id.eq(productDetail.id))
                .leftJoin(member).on(orderHistory.member.id.eq(member.id))
                .leftJoin(product).on(productDetail.product.id.eq(product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(product.id))
                .where(productImage.productImageCategory.eq(ProductImageCategory.MAIN))
                .fetch();

        return Optional.ofNullable(result);
    }


}
