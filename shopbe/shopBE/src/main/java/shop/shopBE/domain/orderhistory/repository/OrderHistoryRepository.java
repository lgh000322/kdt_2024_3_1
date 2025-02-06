package shop.shopBE.domain.orderhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long>, OrderHistoryRepositoryCustom{

}
