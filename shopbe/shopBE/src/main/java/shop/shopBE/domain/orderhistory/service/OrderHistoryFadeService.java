package shop.shopBE.domain.orderhistory.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.service.OrderProductService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 문제상황
     * orderHistories : 10개
     * OrderHistoryInfoResponse response = orderProductService.findOrderHistoryInfos(orderHistory.getId());
     * 10번 호출 (좋은 방법이 아님)
     * page, size 조인해서 10개 한번 쿼리로 가져와서
     * 밑에서 List<OrderHistoryResponse> 이거를 조합해서 리턴해야 함
     */

    private List<OrderHistoryResponse> getOrderHistoryResponses(List<OrderHistory> orderHistories, Pageable pageable) {

        // orderHistories에서 ID 목록을 추출
        List<Long> orderIds = orderHistories.stream()
                .map(OrderHistory::getId)
                .toList();

        // 한 번의 쿼리로 모든 OrderHistoryInfoResponse 조회
        Map<Long, List<OrderHistoryInfoResponse>> responseMap = orderProductService.findOrderHistoryInfos(orderIds, pageable)
                .stream()
                .collect(Collectors.groupingBy(OrderHistoryInfoResponse::orderId)); // orderId 기준으로 그룹화

        // OrderHistory와 OrderHistoryInfoResponse를 매핑하여 변환
        List<OrderHistoryResponse> result = orderHistories.stream()
                .map(orderHistory -> {
                    List<OrderHistoryInfoResponse> responses = responseMap.getOrDefault(orderHistory.getId(), Collections.emptyList());

                    // 대표 상품 1개 선택 (판매량 높은 순 정렬 후 첫 번째 상품 선택)
                    OrderHistoryInfoResponse representativeProduct = responses.stream()
                            .findFirst() // 첫 번째 상품 선택
                            .orElse(null);

                    return OrderHistoryResponse.builder()
                            .orderId(orderHistory.getId())
                            .createdAt(orderHistory.getCreatedAt())
                            .imageUrl(representativeProduct != null ? representativeProduct.mainImageUrl() : null)
                            .content(representativeProduct != null
                                    ? representativeProduct.mainProductName() + " 외 " + (orderHistory.getOrderCount() - 1) + "개"
                                    : "정보 없음")
                            .price(orderHistory.getOrderPrice())
                            .build();
                })
                .toList();

        return result;
    }
}
