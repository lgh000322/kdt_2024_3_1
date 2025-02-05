package shop.shopBE.domain.member.repository;

import org.springframework.data.domain.Pageable;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.response.MemberInformation;
import shop.shopBE.domain.member.response.MemberListResponse;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<MemberInformation> findMemberInformById(Long memberId);

    Optional<List<MemberListResponse>> findAllByPaging(Pageable pageable, Role role, String email);

}

