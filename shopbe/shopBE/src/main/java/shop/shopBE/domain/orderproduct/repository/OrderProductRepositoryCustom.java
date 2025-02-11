package shop.shopBE.domain.orderproduct.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepositoryCustom {

    Optional<OrderProduct> findOrderProductByProductDetailId(Long productDetailId);

    Optional<List<OrderHistoryInfoResponse>> findOrderHistoryInfoByIds(List<Long> orderHistoryIds, Pageable pageable);

    Optional<List<OrderProduct>> findOrderProductByOrderHistoryId(Long orderHistoryId);

    Optional<List<OrderProductInfo>> findOrderProductInfoByOrderHistoryId(Long orderHistoryId);
}
