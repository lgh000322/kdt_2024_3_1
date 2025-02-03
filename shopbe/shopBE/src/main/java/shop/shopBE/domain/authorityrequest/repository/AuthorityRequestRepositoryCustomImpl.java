package shop.shopBE.domain.authorityrequest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListViewModel;
import shop.shopBE.domain.member.entity.QMember;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.authorityrequest.entity.QAuthorityRequest.*;
import static shop.shopBE.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class AuthorityRequestRepositoryCustomImpl implements AuthorityRequestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<AuthorityResponseListViewModel>> findAuthorityRequests(Pageable pageable) {
        List<AuthorityResponseListViewModel> result = queryFactory
                .select(Projections.constructor(AuthorityResponseListViewModel.class,
                        authorityRequest.id,
                        member.name,
                        authorityRequest.reasonToRegister,
                        authorityRequest.createAt
                ))
                .from(authorityRequest).innerJoin(member).on(authorityRequest.member.id.eq(member.id))
                .where(authorityRequest.isAccepted.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<AuthorityRequest> findByIdFetchJoin(Long authorityId) {
        AuthorityRequest result = queryFactory
                .select(authorityRequest)
                .from(authorityRequest.member, member).fetchJoin()
                .where(authorityRequest.id.eq(authorityId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
