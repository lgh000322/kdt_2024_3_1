package shop.shopBE.domain.member.repository;

import shop.shopBE.domain.member.response.MemberInformation;

import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<MemberInformation> findMemberInformById(Long memberId);
}
