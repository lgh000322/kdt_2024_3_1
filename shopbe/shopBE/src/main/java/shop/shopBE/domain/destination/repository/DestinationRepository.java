package shop.shopBE.domain.destination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.destination.entity.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long>, DestinationRepositoryCustom {
}
