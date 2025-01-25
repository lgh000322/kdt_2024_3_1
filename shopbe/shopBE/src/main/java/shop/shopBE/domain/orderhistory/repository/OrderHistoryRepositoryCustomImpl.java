package shop.shopBE.domain.orderhistory.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.QMember;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.entity.QOrderHistory;
import shop.shopBE.domain.orderhistory.response.OrderHistoryInfoResponse;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.destination.entity.QDestination.destination;
import static shop.shopBE.domain.member.entity.QMember.member;
import static shop.shopBE.domain.orderhistory.entity.QOrderHistory.orderHistory;

@RequiredArgsConstructor
public class OrderHistoryRepositoryCustomImpl implements OrderHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<OrderHistory>> findAllByMemberWithPaging(Long memberId, Pageable pageable) {
        List<OrderHistory> result = queryFactory
                .select(orderHistory)
                .from(orderHistory)
                .where(orderHistory.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(result);
    }

}
