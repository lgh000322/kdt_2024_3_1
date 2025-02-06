package shop.shopBE.domain.orderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {
    // 특정 주문 내역(`OrderHistory`) ID로 주문 상품 목록 조회
    List<OrderProduct> findByOrderHistoryId(Long orderHistoryId);
}
