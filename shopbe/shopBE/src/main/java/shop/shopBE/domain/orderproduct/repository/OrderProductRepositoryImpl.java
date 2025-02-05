package shop.shopBE.domain.orderproduct.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.response.DetailOrderProducts;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.domain.productimage.entity.ProductImage;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.orderproduct.entity.QOrderProduct.orderProduct;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    //주문상품번호로 주문상품 상세보기
    @Override
    public Optional<OrderProductInfo> findDetailOrderProductByHistoryId(OrderHistory oHistory, OrderProduct oProduct) {
        //주문상품에 대한 이미지정보 받기
        OrderHistoryInfoResponse productImageInfo = queryFactory
                .select(Projections.constructor(OrderHistoryInfoResponse.class,
                        product.productName,
                        productImage.savedName //해당 상품에 대한 URL 접근방법을 모르겟음.
                ))
                .from(orderProduct)
                .innerJoin(product).on(orderProduct.product.id.eq(product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(product.id))
                .where(orderProduct.id.eq(oProduct.getId()))
                .fetchOne();


        //주문내역과 주문상품 정보로 상세정보 클래스에 매개변수로 전달하여 생성
        OrderProductInfo result = new OrderProductInfo(
                oHistory.getId(),
                oHistory.getMember().getName(),
                oHistory.getDestination().getAddress(),
                oHistory.getDestination().getTel(),
                new DetailOrderProducts(
                    new OrderProductDeliveryInfo(oProduct.getChangedAt(), oProduct.getCurrentDeliveryStatus()), //배송상태
                    productImageInfo.mainProductName(), //record 값을 받아오게 했음...
                    productImageInfo.mainProductName(),
                    oProduct.getProductCount(),
                    oProduct.getProductTotalPrice()
                )
        );

        return Optional.ofNullable(result);
    }

    //주문내역 번호로 대표 이미지 주소와 이름 반환
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
