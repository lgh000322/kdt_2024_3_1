package shop.shopBE.domain.authorityrequest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListViewModel;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.authorityrequest.entity.QAuthorityRequest.*;
import static shop.shopBE.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class AuthorityRequestRepositoryCustomImpl implements AuthorityRequestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<AuthorityResponseListViewModel>> findAuthorityRequests(int page, int size) {
        List<AuthorityResponseListViewModel> result = queryFactory
                .select(Projections.constructor(AuthorityResponseListViewModel.class,
                        authorityRequest.id,
                        member.name,
                        authorityRequest.reasonToRegister,
                        authorityRequest.createAt
                ))
                .from(authorityRequest).innerJoin(member).on(authorityRequest.member.id.eq(member.id))
                .offset(page)
                .limit(size)
                .fetch();

        return Optional.ofNullable(result);
    }

}
