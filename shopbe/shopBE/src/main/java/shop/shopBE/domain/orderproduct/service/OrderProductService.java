package shop.shopBE.domain.orderproduct.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.exception.OrderHistoryException;
import shop.shopBE.domain.orderhistory.repository.OrderHistoryRepository;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.request.OrderProductDeliveryInfo;
import shop.shopBE.domain.orderproduct.exception.OrderProductException;
import shop.shopBE.domain.orderproduct.repository.OrderProductRepository;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.global.exception.custom.CustomException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service //서비스
@RequiredArgsConstructor //생성자를 만들어줌 orderRepository를 매개변수로 받아와 this로 넣는 코드를 알아서해줌
@Transactional(readOnly = true)
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    @Transactional
    public OrderProductInfo findDetailOrderProductByHistoryId(Long orderHistoryId) {

        OrderHistory findOrderHistory = orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(()-> new CustomException(OrderHistoryException.OrderHistory_NOT_FOUND));

        return orderProductRepository.findDetailOrderProductByHistoryId(findOrderHistory)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));

    }

    @Transactional
    // 주문기록 중 대표 상품 정보로 쓰기 위한 상품 조회
    public OrderHistoryInfoResponse findOrderHistoryInfos(Long orderHistoryId) {
        return orderProductRepository.findOrderHistoryInfoById(orderHistoryId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));
    }

    @Transactional(readOnly = false)
    //주문상품의 배송상태를 업데이트
    public void UpdateOrderProductDeliveryState(Long orderProductId, OrderProductDeliveryInfo orderProductDeliveryInfo){
        OrderProduct findOrderProduct  = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));

        //받아온 업데이트 상태만 갖고 시간은 현재시간으로 업데이트 시킨다.
        OrderProductDeliveryInfo update = OrderProductDeliveryInfo.createDefaultOrderProductDeliveryEntity(orderProductDeliveryInfo.deliveryStatus(), LocalDateTime.now());
        findOrderProduct.updateDeliveryStatus(update);
    }

    @Transactional
    //주문상품의 배송상태 기록 리스트를 반환
    public List<OrderProductDeliveryInfo> getOrderProductDeliveryListByHistoryId(Long orderProductId){
        OrderProduct findOrderProduct  = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));

        return findOrderProduct.getDeliveryStatusHistory();
    }

    @Transactional
    //주문내역이 추가될때 주문상품 추가
    public void addOrderProduct(OrderProduct addOrderProduct){
        orderProductRepository.save(addOrderProduct);
    }

    @Transactional
    public Optional<Void> deleteByOrderHistoryId(Long orderHistoryId) {
        return Optional.ofNullable(orderProductRepository.deleteByOrderHistoryId(orderHistoryId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND)));
    }
}
