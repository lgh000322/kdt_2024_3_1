package shop.shopBE.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.member.response.MemberInformation;

import java.util.Optional;

import static shop.shopBE.domain.member.entity.QMember.*;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {


    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<MemberInformation> findMemberInformById(Long memberId) {
        MemberInformation memberInformation = queryFactory
                .select(Projections.constructor(MemberInformation.class,
                        member.username,
                        member.name,
                        member.email,
                        member.tel,
                        member.gender))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(memberInformation);
    }
}
