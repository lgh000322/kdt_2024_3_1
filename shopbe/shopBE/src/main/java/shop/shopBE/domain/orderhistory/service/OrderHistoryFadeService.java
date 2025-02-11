package shop.shopBE.domain.orderhistory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
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
    public List<OrderHistoryResponse> findOrderHistoryList(Long memberId, Pageable pageable) {
        // 회원의 주문기록을 찾는다
        List<OrderHistory> orderHistories = orderHistoryService.findOrderHistoryByMemberId(memberId, pageable);

        // 상품의 orderHistory로 OrderHistoryResponse를 응답받는다.
        List<OrderHistoryResponse> result = getOrderHistoryResponses(orderHistories,pageable);

        return result;
    }

    private List<OrderHistoryResponse> getOrderHistoryResponses(List<OrderHistory> orderHistories,Pageable pageable) {

        /**
         * 문제상황
         * orderHistories : 10개
         * OrderHistoryInfoResponse response = orderProductService.findOrderHistoryInfos(orderHistory.getId());
         * 10번 호출 (좋은 방법이 아님)
         * page, size 조인해서 10개 한번 쿼리로 가져와서
         * 밑에서 List<OrderHistoryResponse> 이거를 조합해서 리턴해야 함
         */

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
