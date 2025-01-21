package shop.shopBE.domain.likes.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.likes.entity.QLikes;
import shop.shopBE.domain.member.entity.QMember;

import java.util.Optional;

import static shop.shopBE.domain.likes.entity.QLikes.*;
import static shop.shopBE.domain.member.entity.QMember.*;

@RequiredArgsConstructor
public class LikesRepositoryCustomImpl implements LikesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Likes> findLikesIdByMemberId(Long memberId) {
        Likes likes = queryFactory
                .select(QLikes.likes)
                .from(QLikes.likes)
                .innerJoin(member).on(QLikes.likes.member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(likes);
    }
}
