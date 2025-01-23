package shop.shopBE.domain.orderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {
}
