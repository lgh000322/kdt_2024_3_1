package shop.shopBE.domain.orderhistory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.exception.OrderHistoryException;
import shop.shopBE.domain.orderhistory.repository.OrderHistoryRepository;
import shop.shopBE.domain.orderhistory.request.OrderHistoryInfo;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    
    //주문내역리스트 조회
    public List<OrderHistoryResponse> findOrderHistoryList(Long memberId){
        return orderHistoryRepository.findOrderListListByMemberId(memberId)
                .orElseThrow(() -> new CustomException(OrderHistoryException.OrderHistory_NOT_FOUND));
    }

    //주문내역 삭제
    public void deleteOrderHistoryByHistoryId(Long historyId){
        orderHistoryRepository.deleteOrderHistoryByOrderHistoryId(historyId);
    }

    //주문내역 추가
    public void addOrderHistory(Long memberId, OrderProduct orderProduct, Destination destination){
        OrderHistory addOrderHistory = orderHistoryRepository.addOrderHistoryByMemberId(memberId, orderProduct, destination);
        //Repository에서 save시키려하면 repository와 CustomIpml간에 순환의존이 생겨서 service에서 save시키도록 구현.
        orderHistoryRepository.save(addOrderHistory);
    }
}
