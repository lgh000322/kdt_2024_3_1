package shop.shopBE.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.exception.MemberExceptionCode;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.domain.member.request.MemberUpdateInfo;
import shop.shopBE.domain.member.response.MemberInformation;
import shop.shopBE.domain.member.response.MemberListResponse;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(Long memberId,MemberUpdateInfo memberUpdateInfo) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND)); // 변경 감지에 의한 업데이트

        findMember.updateMember(memberUpdateInfo.email(), memberUpdateInfo.name(), memberUpdateInfo.gender(), memberUpdateInfo.phone(), true);
    }

    @Transactional
    public void updateMemberRole(Role role, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));

        member.changeRole(role);
    }

    public MemberInformation findMemberInfoById(Long memberId) {
        return memberRepository.findMemberInformById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));
    }

    public List<MemberListResponse> getMemberList(Pageable pageable) {
        return memberRepository.findAllByPaging(pageable)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));
    }

}
