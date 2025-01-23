package shop.shopBE.domain.orderhistory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.request.OrderHistoryInfo;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryFadeService {

    private final OrderHistoryService orderHistoryService;

    //주문내역리스트 조회
    public List<OrderHistoryResponse> findOrderHistoryList(Long memberId){
        return orderHistoryService.findOrderHistoryList(memberId);
    }

    //주문내역 추가

    
    //주문내역의 배송조회
    
    
    //주문내역 삭제
}
