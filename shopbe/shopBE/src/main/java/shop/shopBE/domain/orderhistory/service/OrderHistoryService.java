package shop.shopBE.domain.orderhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.exception.MemberExceptionCode;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.exception.OrderHistoryException;
import shop.shopBE.domain.orderhistory.repository.OrderHistoryRepository;
import shop.shopBE.domain.orderhistory.request.OrderRequest;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.orderproduct.exception.OrderProductException;
import shop.shopBE.domain.orderproduct.repository.OrderProductRepository;
import shop.shopBE.domain.orderproduct.request.OrderProductRequest;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.repository.ProductRepository;
import shop.shopBE.domain.productdetail.entity.ProductDetail;
import shop.shopBE.domain.productdetail.exception.ProductDetailExceptionCustom;
import shop.shopBE.domain.productdetail.repository.ProductDetailRepository;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final MemberRepository memberRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    // 회원의 OrderHistory 리스트를 조회
    public List<OrderHistory> findOrderHistoryByMemberId(Long memberId, Pageable pageable) {
        List<OrderHistory> orderHistories = orderHistoryRepository.findAllByMemberWithPaging(memberId, pageable)
                .orElseThrow(() -> new CustomException(OrderHistoryException.OrderHistory_NOT_FOUND));

        return orderHistories;
    }


    //주문내역 삭제
    @Transactional
    public void deleteOrderHistoryByHistoryId(Long orderHistoryId){
        OrderHistory orderHistory = orderHistoryRepository.findByOrderHistoryId(orderHistoryId)
                .orElseThrow(() -> new CustomException(OrderHistoryException.OrderHistory_NOT_FOUND));

        orderHistory.delete();
    }


    /**
     * 주문기록저장과 주문 아이템 목록저장을 하나의 트랜잭션에서 처리해야 함.
     * @param memberId
     * @param orderRequest
     */
    @Transactional
    public Long orderItems(Long memberId, OrderRequest orderRequest) {
        // 회원의 기본키로 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));

        // 주문정보 객체 생성
        OrderHistory orderHistory = OrderHistory.createDefaultOrderHistory(
                        orderRequest.totalPrice(),
                        orderRequest.orderProductCount(),
                        orderRequest.address(),
                        orderRequest.destinationName(),
                        orderRequest.receiverName(),
                        orderRequest.tel(),
                        orderRequest.zipCode(),
                        orderRequest.deliveryMessage(), member
        );


        // 주문정보 저장
        OrderHistory savedOrderHistory = orderHistoryRepository.save(orderHistory);

        // 주문에 딸린 주문 목록 데이터
        List<OrderProductRequest> orderProductRequests = orderRequest.orderProductRequests();

        // 주문 목록 데이터 세팅
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductRequest orderProductRequest : orderProductRequests) {
            ProductDetail productDetail = productDetailRepository.findByProductIdAndSize(orderProductRequest.productId(), orderProductRequest.shoesSize())
                    .orElseThrow(() -> new CustomException(ProductDetailExceptionCustom.PRODUCT_DETAIL_NOT_FOUND));


            OrderProduct orderProduct = OrderProduct.createDefaultOrderProduct(
                    orderProductRequest.productCount(),
                    orderProductRequest.productTotalPrice(),
                    DeliveryStatus.BEFORE_PAY,
                    savedOrderHistory,
                    productDetail
            );

            orderProducts.add(orderProduct);
        }

        // List<Entity>를 한번에 저장함
        orderProductRepository.saveAll(orderProducts);

        return savedOrderHistory.getId();
    }

    @Transactional
    public void updateOrderHistory(Long orderHistoryId, DeliveryStatus deliveryStatus) {
        List<OrderProduct> orderProducts = orderProductRepository.findOrderProductByOrderHistoryId(orderHistoryId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));

        for (OrderProduct orderProduct : orderProducts) {
            // 주문상품의 상태를 변경
            orderProduct.changeDeliveryStatus(deliveryStatus);

            // 해당 상품의 총 재고 수량 감소
            Product product = orderProduct.getProductDetail().getProduct();
            product.minusTotalStock(orderProduct.getProductCount());

            // 해당 사이즈의 재고 감소
            ProductDetail productDetail = orderProduct.getProductDetail();
            productDetail.minusSizeStock(orderProduct.getProductCount());
        }
    }

}
