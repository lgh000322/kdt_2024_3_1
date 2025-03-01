package shop.shopBE.domain.orderproduct.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {

}
