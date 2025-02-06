package shop.shopBE.domain.orderproduct.repository;

import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepositoryCustom {

    Optional<OrderProductInfo> findDetailOrderProductByHistoryId(OrderHistory orderHistory);

    Optional<OrderHistoryInfoResponse> findOrderHistoryInfoById(Long orderHistoryId);

}
