package shop.shopBE.domain.orderhistory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.exception.DestinationException;
import shop.shopBE.domain.destination.repository.DestinationRepository;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.exception.MemberExceptionCode;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.exception.OrderHistoryException;
import shop.shopBE.domain.orderhistory.repository.OrderHistoryRepository;
import shop.shopBE.domain.orderhistory.request.OrderRequest;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.orderproduct.repository.OrderProductRepository;
import shop.shopBE.domain.orderproduct.request.OrderProductRequest;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.exception.ProductExceptionCode;
import shop.shopBE.domain.product.repository.ProductRepository;
import shop.shopBE.global.exception.custom.CustomException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final MemberRepository memberRepository;
    private final DestinationRepository destinationRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    
    // 회원의 OrderHistory 리스트를 조회
    public List<OrderHistory> findOrderHistoryByMemberId(Long memberId, Pageable pageable) {
        List<OrderHistory> orderHistories = orderHistoryRepository.findAllByMemberWithPaging(memberId, pageable)
                .orElseThrow(() -> new CustomException(OrderHistoryException.OrderHistory_NOT_FOUND));

        return orderHistories;
    }


    //주문내역 삭제
    @Transactional
    public void deleteOrderHistoryByHistoryId(Long historyId){
        // 1. 주문내역의 배송지 정보를 제거
        OrderHistory deleteOrderHistory = orderHistoryRepository.findByOrderHistoryId(historyId)
                .orElseThrow(() -> new CustomException(OrderHistoryException.OrderHistory_NOT_FOUND));
        destinationRepository.deleteById(deleteOrderHistory.getDestination().getId());

        /*
        받아온 주문내역의 ID를 가진 주문내역 객체의 배송 ID를 통해서 주문내역의 배송지 삭제
        주문내역객체의 배송지를 null 처리하고 해당 배송지정보가 없는 주문내역을 save 시켜서 영속성 처리
        OrderHistory 객체에 Destination 을 caseCadeType.Remove 로 삭제되게 했지만 확실히 처리하기 위함.
         */
        if (deleteOrderHistory.getDestination() != null) {
            destinationRepository.deleteById(deleteOrderHistory.getDestination().getId());
            deleteOrderHistory.removeDestination(); // FK null 처리
            orderHistoryRepository.save(deleteOrderHistory);
        }

        //2. 배송지 정보를 삭제했으므로 주문내역 삭제
        orderHistoryRepository.deleteById(historyId);
    }


    /**
     * 주문기록저장과 주문 아이템 목록저장을 하나의 트랜잭션에서 처리해야 함.
     * @param memberId
     * @param orderRequest
     */
    @Transactional
    public void orderItems(Long memberId, OrderRequest orderRequest) {
        // 회원의 기본키로 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));

        // 배송지 기본키로 배송지 조회
        Destination destination = destinationRepository.findById(orderRequest.destinationId())
                .orElseThrow(() -> new CustomException(DestinationException.DESTINATION_NOT_FOUND));

        // 주문정보 객체 생성
        OrderHistory orderHistory = OrderHistory.builder()
                .orderPrice(orderRequest.totalPrice())
                .createdAt(LocalDateTime.now())
                .member(member)
                .destination(destination)
                .build();

        // 주문정보 저장
        OrderHistory savedOrderHistory = orderHistoryRepository.save(orderHistory);

        // 주문에 딸린 주문 목록 데이터
        List<OrderProductRequest> orderProductRequests = orderRequest.orderProductRequests();

        // 주문 목록 데이터 세팅
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductRequest orderProductRequest : orderProductRequests) {
            Product product = productRepository.findById(orderProductRequest.productId())
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));

            // 엔터티에 static 메소드로 따로 구성하는게 좋음
            OrderProduct orderProduct = OrderProduct.builder()
                    .productCount(orderProductRequest.productCount())
                    .productTotalPrice(orderProductRequest.productTotalPrice())
                    .currentDeliveryStatus(DeliveryStatus.BEFORE_PAY) // 결제전으로 저장 ==> 결제 완료되면 재고감소
                    .orderHistory(savedOrderHistory)
                    .product(product)
                    .build();

            OrderProductDeliveryInfo deliveryInfo = OrderProductDeliveryInfo.createDefaultOrderProductDeliveryEntity(DeliveryStatus.BEFORE_PAY);

            orderProduct.getDeliveryStatusHistory().add(deliveryInfo);
            orderProducts.add(orderProduct);

        }

        // List<Entity>를 한번에 저장함
        orderProductRepository.saveAll(orderProducts);
    }

}
