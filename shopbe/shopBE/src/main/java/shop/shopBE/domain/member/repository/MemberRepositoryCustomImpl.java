package shop.shopBE.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.member.response.MemberInformation;
import shop.shopBE.domain.member.response.MemberListResponse;

import java.util.List;
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

    @Override
    public Optional<List<MemberListResponse>> findAllByPaging(Pageable pageable) {
        List<MemberListResponse> result = queryFactory
                .select(Projections.constructor(MemberListResponse.class,
                        member.name,
                        member.email,
                        member.createdAt,
                        member.role
                ))
                .from(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(result);
    }
}
