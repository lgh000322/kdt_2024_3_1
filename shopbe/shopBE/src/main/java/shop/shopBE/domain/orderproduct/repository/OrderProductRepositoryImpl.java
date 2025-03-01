package shop.shopBE.domain.orderproduct.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.member.entity.QMember;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.entity.QOrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.orderproduct.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.exception.OrderProductException;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.domain.productdetail.entity.QProductDetail;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;
import shop.shopBE.global.exception.custom.CustomException;

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
    public Optional<List<OrderHistoryInfoResponse>> findOrderHistoryInfoByIds(List<OrderHistory> orderHistories) {
        List<OrderHistoryInfoResponse> result = queryFactory
                .select(Projections.constructor(OrderHistoryInfoResponse.class,
                        orderHistory.id,
                        product.productName,
                        productImage.savedName
                ))
                .from(orderHistory)
                .leftJoin(orderProduct).on(orderProduct.orderHistory.id.eq(orderHistory.id))
                .leftJoin(productDetail).on(orderProduct.productDetail.id.eq(productDetail.id))
                .leftJoin(product).on(productDetail.product.id.eq(product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(product.id))
                .where(
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        orderHistory.in(orderHistories),
                        orderProduct.id.in(
                                orderProductSubQuery()
                        )
                )
                .fetch();

        return Optional.ofNullable(result);
    }

    private JPQLQuery<Long> orderProductSubQuery() {
        return JPAExpressions.select(orderProduct.id.min())
                .from(orderProduct)
                .groupBy(orderProduct.orderHistory.id);
    }


//    @Override
//    public Optional<List<OrderHistoryInfoResponse>> findOrderHistoryInfoByIds(List<Long> orderHistoryIds, Pageable pageable) {
//        List<OrderHistoryInfoResponse> result = queryFactory
//                .select(Projections.constructor(OrderHistoryInfoResponse.class,
//                        orderProduct.orderHistory.id,
//                        product.productName,  // 대표 상품명
//                        productImage.savedName // 대표 이미지 (없으면 NULL 반환)
//                ))
//                .from(orderProduct)
//                .join(orderProduct.productDetail, productDetail)
//                .join(productDetail.product, product)
//                .join(productImage).on(productImage.product.id.eq(product.id)
//                        .and(productImage.productImageCategory.eq(ProductImageCategory.MAIN))) // 대표 이미지 필터링 (JOIN 조건에서)
//                .where(orderProduct.orderHistory.id.in(orderHistoryIds)) // WHERE 에서는 주문 ID 필터링만 적용
//                .offset(pageable.getOffset()) // 페이징 적용
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        return Optional.ofNullable(result);
//    }



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
                        orderHistory.id,
                        orderProduct.productCount,
                        product.productName,
                        member.name,
                        orderHistory.address,
                        orderHistory.tel,
                        orderProduct.currentDeliveryStatus,
                        productImage.savedName,
                        orderProduct.productTotalPrice
                ))
                .from(orderProduct)
                .leftJoin(orderHistory).on(orderProduct.orderHistory.id.eq(orderHistory.id))
                .leftJoin(productDetail).on(orderProduct.productDetail.id.eq(productDetail.id))
                .leftJoin(member).on(orderHistory.member.id.eq(member.id))
                .leftJoin(product).on(productDetail.product.id.eq(product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(product.id))
                .where(
                        productImage.productImageCategory.eq(ProductImageCategory.MAIN),
                        orderHistory.id.eq(orderHistoryId)
                )
                .fetch();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<OrderProduct> findByCurrentDeliveryStatus(DeliveryStatus deliveryStatus, Pageable pageable) {
        List<OrderProduct> fetch = queryFactory
                .select(orderProduct)
                .from(orderProduct)
                .join(orderProduct.orderHistory, orderHistory).fetchJoin()
                .where(orderProduct.currentDeliveryStatus.eq(deliveryStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(orderProduct.count())
                .from(orderProduct)
                .where(orderProduct.currentDeliveryStatus.eq(deliveryStatus))
                .fetchOne();

        long size = count == null ? 0 : count;

        return new PageImpl<>(fetch, pageable, size);
    }

//    @Override
//    public Page<OrderProduct> findByDeliveryStatus(DeliveryStatus deliveryStatus, Pageable pageable) {
//        List<OrderProduct> fetch = queryFactory
//                .select(orderProduct)
//                .from(orderProduct)
//                .where(orderProduct.currentDeliveryStatus.eq(deliveryStatus))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Long total = queryFactory
//                .select(orderProduct.count())
//                .from(orderProduct)
//                .where(orderProduct.currentDeliveryStatus.eq(deliveryStatus))
//                .fetchOne();
//
//        long totalCount = (total != null) ? total : 0L;  // NullPointerException 방지
//
//        return new PageImpl<>(fetch, pageable, totalCount);
//    }


}
