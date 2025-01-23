package shop.shopBE.domain.orderhistory.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.QMember;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.request.OrderHistoryInfo;
import shop.shopBE.domain.orderhistory.response.OrderHistoryResponse;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static shop.shopBE.domain.destination.entity.QDestination.destination;
import static shop.shopBE.domain.member.entity.QMember.member;
import static shop.shopBE.domain.orderhistory.entity.QOrderHistory.orderHistory;

@RequiredArgsConstructor
public class OrderHistoryRepositoryCustomImpl implements OrderHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //배송지 조회
    @Override
    public Optional<List<OrderHistoryResponse>> findOrderListListByMemberId(Long memberId) {
        List<OrderHistoryResponse> result = queryFactory
                .select(Projections.constructor(OrderHistoryResponse.class,
                        orderHistory.orderPrice,
                        orderHistory.createdAt,
                        orderHistory.destination,
                        orderHistory.member
                ))
                .from(orderHistory)
                .join(orderHistory.member, member).fetchJoin()
                .join(orderHistory.destination, destination).fetchJoin()
                .where(orderHistory.member.id.eq(memberId))
                .orderBy(orderHistory.createdAt.desc()) // 최신순 정렬
                .fetch();

        return Optional.ofNullable(result);
    }
    
    //주문내역 삭제
    @Override
    public void deleteOrderHistoryByOrderHistoryId(Long historyId) {
        queryFactory
                .delete(orderHistory)
                .where(orderHistory.id.eq(historyId))
                .execute();
    }


    //주문내역 추가(상품 주문시)
    @Override
    public OrderHistory addOrderHistoryByMemberId(Long memberId, OrderProduct orderProduct, Destination destination) {
        Member member = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.id.eq(memberId))
                .fetchOne();

        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        OrderHistory newOrder = new OrderHistory(
                //Id는 자동생성
                orderProduct.getProductTotalPrice(), // 주문 총 가격
                LocalDateTime.now(), // 현재 날짜 (주문 날짜)
                destination, // 배송지 정보
                member // 회원 정보
        );

        return newOrder;
    }

}
