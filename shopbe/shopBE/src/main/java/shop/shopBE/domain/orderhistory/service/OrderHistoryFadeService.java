package shop.shopBE.domain.orderhistory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.request.OrderHistoryInfo;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.service.OrderProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryFadeService {

    private final OrderHistoryService orderHistoryService;
    private final OrderProductService orderProductService;

    //주문내역리스트 조회
    public List<OrderHistoryResponse> findOrderHistoryList(Long memberId, OrderHistoryInfo orderHistoryInfo) {
        // 페이지 객체 생성
        Pageable pageable = PageRequest.of(orderHistoryInfo.page() - 1, orderHistoryInfo.size());

        // 회원의 주문기록을 찾는다
        List<OrderHistory> orderHistories = orderHistoryService.findOrderHistoryByMemberId(memberId, pageable);

        // 상품의 orderHistory로 OrderHistoryResponse를 응답받는다.
        List<OrderHistoryResponse> result = getOrderHistoryResponses(orderHistories);

        return result;
    }

    private List<OrderHistoryResponse> getOrderHistoryResponses(List<OrderHistory> orderHistories) {
        return orderHistories.stream()
                .map(orderHistory -> {
                    OrderHistoryInfoResponse response = orderProductService.findOrderHistoryInfos(orderHistory.getId());

                    return OrderHistoryResponse.builder()
                            .orderId(orderHistory.getId())
                            .createdAt(orderHistory.getCreatedAt())
                            .imageUrl(response.mainImageUrl())
                            .content(response.mainProductName() + "외 " + orderHistory.getOrderCount())
                            .price(orderHistory.getOrderPrice())
                            .build();

                }).toList();
    }
}
