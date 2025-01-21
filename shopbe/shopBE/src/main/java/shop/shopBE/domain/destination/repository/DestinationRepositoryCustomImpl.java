package shop.shopBE.domain.destination.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.entity.QDestination;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.QMember;

import static shop.shopBE.domain.member.entity.QMember.*;

import java.util.List;


@RequiredArgsConstructor
public class DestinationRepositoryCustomImpl implements DestinationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    private final QDestination destination = QDestination.destination;

    @Override
    public List<Destination> findDestinationIdByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(destination)
                .where(destination.member.eq(member))
                .fetch();
    }

    @Override
    public void addDestinationByMemberId(Long memberId, AddDestinationRequest addDestinationResponse) {
        QDestination destination = QDestination.destination;
        QMember member = QMember.member;

        // 회원 조회
        Member memberEntity = queryFactory
                .selectFrom(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        if (memberEntity == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 새로운 배송지 엔티티 생성 후 persist
        Destination newDestination = Destination.builder()
                .member(memberEntity)
                .destinationName(addDestinationResponse.destinationName())
                .receiverName(addDestinationResponse.receiverName())
                .tel(addDestinationResponse.tel())
                .address(addDestinationResponse.address())
                .zipCode(addDestinationResponse.zipCode())
                .isSelectedDestination(addDestinationResponse.isSelectedDestination())
                .deliveryMessage("")
                .build();
        entityManager.persist(newDestination);
    }

    
    //배송지 업데이트
    @Override
    public void updateDestinationByID(Long memberId, UpdateDestinationRequest updateDestinationRequest) {
        QDestination destination = QDestination.destination;
        QMember member = QMember.member;

        // 회원 조회 (memberId로)
        Member memberEntity = queryFactory
                .selectFrom(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        if (memberEntity == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 배송지 업데이트 (QueryDSL)
        long updatedCount = queryFactory
                .update(destination)
                .set(destination.destinationName, updateDestinationRequest.destinationName())
                .set(destination.receiverName, updateDestinationRequest.receiverName())
                .set(destination.tel, updateDestinationRequest.tel())
                .set(destination.address, updateDestinationRequest.address())
                .set(destination.zipCode, updateDestinationRequest.zipCode())
                .set(destination.isSelectedDestination, updateDestinationRequest.isSelectedDestination())
                .set(destination.deliveryMessage, updateDestinationRequest.deliverMessage() != null ? updateDestinationRequest.deliverMessage() : "")
                .where(destination.member.id.eq(memberId))
                .execute();

        if (updatedCount == 0) {
            throw new IllegalArgumentException("업데이트할 배송지가 존재하지 않습니다.");
        }
    }

    // 특정 배송지 삭제 (QueryDSL)
    @Override
    public void deleteDestinationByID(Long destinationId) {
        Destination destinationEntity = entityManager.find(Destination.class, destinationId);
        if (destinationEntity != null) {
            entityManager.remove(destinationEntity);
        }
    }
}
