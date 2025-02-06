package shop.shopBE.domain.orderproduct.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.response.DetailOrderProducts;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.orderproduct.entity.QOrderProduct.orderProduct;
import static shop.shopBE.domain.product.entity.QProduct.product;
import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@Slf4j
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    //주문내역번호로 주문상품 상세보기
    @Override
    public Optional<OrderProductInfo> findDetailOrderProductByHistoryId(OrderHistory oHistory) {

        // 1. 주문 상품(OrderProduct) 리스트 조회
        List<OrderProduct> orderProducts = queryFactory
                .selectFrom(orderProduct)
                .where(orderProduct.orderHistory.id.eq(oHistory.getId()))
                .fetch();

        // 2. 각 주문 상품에 대한 상세 정보 생성
        List<DetailOrderProducts> orderDetailProducts = new ArrayList<>();

        for (OrderProduct oProduct : orderProducts) {

            // 2-1. 상품 이미지 정보 가져오기
            OrderHistoryInfoResponse productImageInfo = queryFactory
                    .select(Projections.constructor(OrderHistoryInfoResponse.class,
                            product.productName,
                            productImage.savedName
                    ))
                    .from(orderProduct)
                    .innerJoin(product).on(orderProduct.product.id.eq(product.id))
                    .leftJoin(productImage).on(productImage.product.id.eq(product.id))
                    .where(orderProduct.id.eq(oProduct.getId())
                            .and(productImage.productImageCategory.eq(ProductImageCategory.MAIN))) //상품의 Main이미지만 가져오도록
                    .fetchOne();

            //2-2. 상품 상세 정보들 객체 생성
            DetailOrderProducts details = new DetailOrderProducts(
                    new OrderProductDeliveryInfo(oProduct.getChangedAt(), oProduct.getCurrentDeliveryStatus()), // 배송 상태
                    productImageInfo.mainProductName(),    // 대표 상품 이름
                    productImageInfo.mainImageUrl(),       // 대표 이미지 URL
                    oProduct.getProductCount(),            // 구매 수량
                    oProduct.getProductTotalPrice()        // 총 가격
            );

            //2-2 리스트에 하나씩 삽입.
            orderDetailProducts.add(details);
        }

        //3. 반환할 객체 생성
        OrderProductInfo result = new OrderProductInfo(
                oHistory.getId(),                            // 주문 내역 ID
                oHistory.getReceiverName(),             // 주문자 이름
                oHistory.getAddress(),     // 배송 주소
                oHistory.getTel(),         // 전화번호
                orderDetailProducts                         // 주문 상세 목록
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
                    .where(orderProduct.orderHistory.id.eq(orderHistoryId)
                        .and(productImage.productImageCategory.eq(ProductImageCategory.MAIN)))
                    .fetchFirst();

        return Optional.ofNullable(result);
    }



}
