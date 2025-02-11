package shop.shopBE.domain.orderproduct.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.orderproduct.exception.OrderProductException;
import shop.shopBE.domain.orderproduct.repository.OrderProductRepository;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;
import java.util.Optional;

@Service //서비스
@RequiredArgsConstructor //생성자를 만들어줌 orderRepository를 매개변수로 받아와 this로 넣는 코드를 알아서해줌
@Transactional(readOnly = true)
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;


    @Transactional
    public List<OrderProductInfo> getOrderInfos(Long orderHistoryId) {
        return orderProductRepository.findOrderProductInfoByOrderHistoryId(orderHistoryId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));
    }


//
//    public List<OrderHistoryInfoResponse> findOrderHistoryInfos(List<Long> orderHistoryIds, Pageable pageable) {
//        return orderProductRepository.findOrderHistoryInfoByIds(orderHistoryIds, pageable)
//                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));
//    }

    public List<OrderHistoryInfoResponse> findOrderHistoryInfos(List<OrderHistory> orderHistories) {
        return orderProductRepository.findOrderHistoryInfoByIds(orderHistories)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));
    }


    @Transactional
    //주문상품의 배송상태를 업데이트
    public void updateOrderProductDeliveryState(Long productDetailId, DeliveryStatus deliveryStatus) {
        OrderProduct orderProduct = orderProductRepository.findOrderProductByProductDetailId(productDetailId)
                .orElseThrow(() -> new CustomException(OrderProductException.ORDER_PRODUCT_NOT_FOUND));


        orderProduct.changeDeliveryStatus(deliveryStatus);
    }


    @Transactional
    public void deleteByOrderProductId(Long orderProductId) {
        orderProductRepository.deleteById(orderProductId);

    }

}
