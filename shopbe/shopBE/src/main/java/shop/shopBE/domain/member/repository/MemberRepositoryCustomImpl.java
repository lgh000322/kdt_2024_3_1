package shop.shopBE.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.member.entity.enums.Role;
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
    public Optional<List<MemberListResponse>> findAllByPaging(Pageable pageable, Role role, String email, String name) {
        List<MemberListResponse> result = queryFactory
                .select(Projections.constructor(MemberListResponse.class,
                        member.name,
                        member.email,
                        member.tel,
                        member.gender,
                        member.role
                ))
                .from(member)
                .where(
                        isRoleEq(role),
                        isEmailEq(email),
                        isNameEq(name)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(result);
    }

    private BooleanExpression isRoleEq(Role role) {
        return role == null ? null : member.role.eq(role);
    }

    private BooleanExpression isEmailEq(String email) {
        return email == null ? null : member.email.like("%" + email + "%");
    }

    private BooleanExpression isNameEq(String name) {
        return name == null ? null : member.name.like("%" + name + "%");
    }
}
