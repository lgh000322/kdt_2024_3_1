package shop.shopBE.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.request.MemberUpdateInfo;
import shop.shopBE.domain.member.response.MemberInformation;

@Service
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;

    public void updateMember(Long memberId, MemberUpdateInfo memberUpdateInfo) {
        memberService.updateMember(memberId, memberUpdateInfo);
    }

    public MemberInformation findMemberInfoById(Long memberId) {
        return memberService.findMemberInfoById(memberId);
    }

}
