package shop.shopBE.domain.orderproduct.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.request.OrderProductInfo;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.orderhistory.entity.QOrderHistory.orderHistory;
import static shop.shopBE.domain.orderproduct.entity.QOrderProduct.orderProduct;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<List<OrderProductInfo>> findOrderProductByHistoryId(Long historyId) {
        List<OrderProductInfo> result = queryFactory
                .select(Projections.constructor(OrderProductInfo.class,
                        orderProduct.id,
                        orderProduct.productCount,
                        orderProduct.productTotalPrice,
                        orderProduct.currentDeliveryStatus,
                        orderProduct.changedAt,
                        orderProduct.product
                ))
                .from(orderProduct)
                .join(orderProduct.orderHistory, orderHistory).fetchJoin() // ✅ 주문 내역 조인
                .join(orderProduct.product, product).fetchJoin() // ✅ 상품 정보 조인
                .where(orderProduct.orderHistory.id.eq(historyId)) // ✅ 주문 내역 ID로 필터링
                .fetch();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<OrderHistoryInfoResponse> findOrderHistoryInfoById(Long orderHistoryId) {
        OrderHistoryInfoResponse result = queryFactory
                .select(Projections.constructor(OrderHistoryInfoResponse.class,
                        product.productName,
                        productImage.savedName
                        ))
                .from(orderProduct)
                .innerJoin(product).on(orderProduct.product.id.eq(product.id))
                .innerJoin(productImage).on(productImage.product.id.eq(product.id))
                .where(orderProduct.orderHistory.id.eq(orderHistoryId))
                .fetchOne();

        return Optional.ofNullable(result);
    }


}
