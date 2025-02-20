package shop.shopBE.domain.orderhistory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderproduct.response.OrderProductInfo;

import java.util.List;
import java.util.Optional;
import static shop.shopBE.domain.orderhistory.entity.QOrderHistory.orderHistory;

@RequiredArgsConstructor
public class OrderHistoryRepositoryCustomImpl implements OrderHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<OrderHistory>> findAllByMemberWithPaging(Long memberId, Pageable pageable) {
        List<OrderHistory> result = queryFactory
                .select(orderHistory)
                .from(orderHistory)
                .where(
                        orderHistory.member.id.eq(memberId),
                        orderHistory.isDeleted.eq(false)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(result);
    }

    public Optional<OrderHistory> findByOrderHistoryId(Long historyId){
        OrderHistory result = queryFactory
                .select(orderHistory)
                .from(orderHistory)
                .where(orderHistory.id.eq(historyId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
