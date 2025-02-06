package shop.shopBE.domain.destination.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.QMember;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.destination.entity.QDestination.*;
import static shop.shopBE.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class DestinationRepositoryCustomImpl implements DestinationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    //배송지 조회(리스트)
    @Override
    public Optional<List<DestinationListInfo>>findDestinationListByMemberId(Long memberId) {
        List<DestinationListInfo> result = queryFactory
                .select(Projections.constructor(DestinationListInfo.class,
                                destination.destinationName,
                                destination.receiverName,
                                destination.tel,
                                destination.address,
                                destination.zipCode,
                                destination.deliveryMessage))
                        .from(destination) // FROM 절 추가
                        .where(destination.member.id.eq(memberId)) // 조건 추가
                        .fetch();

        return Optional.ofNullable(result);
    }
}
