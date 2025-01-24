package shop.shopBE.domain.orderhistory.repository;

import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepositoryCustom {
    Optional<List<OrderHistoryResponse>> findOrderListListByMemberId(Long memberId);

    void deleteOrderHistoryByOrderHistoryId(Long historyId);

    OrderHistory addOrderHistoryByMemberId(Long memberId, OrderProduct orderProduct, Destination destination);

}
