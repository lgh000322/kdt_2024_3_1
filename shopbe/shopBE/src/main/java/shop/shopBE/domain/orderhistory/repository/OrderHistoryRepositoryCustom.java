package shop.shopBE.domain.orderhistory.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepositoryCustom {

    Optional<List<OrderHistory>> findAllByMemberWithPaging(Long memberId, Pageable pageable);

    Optional<OrderHistory> findByOrderHistoryId(Long historyId);

}
