package shop.shopBE.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.request.MemberListRequest;
import shop.shopBE.domain.member.request.MemberUpdateInfo;
import shop.shopBE.domain.member.response.MemberInformation;
import shop.shopBE.domain.member.response.MemberListResponse;
import shop.shopBE.domain.member.response.MemberListResponseView;

import java.util.List;

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

    public void updateMemberRole(Role role, Long memberId) {
        memberService.updateMemberRole(role, memberId);
    }

    public List<MemberListResponseView> getMemberList(Pageable pageable, Role role, String email, String name) {
        List<MemberListResponse> memberList = memberService.getMemberList(pageable, role, email, name);
        return getViewResponse(memberList);
    }

    private List<MemberListResponseView> getViewResponse(List<MemberListResponse> memberList) {
        return memberList.stream()
                .map(res -> {
                    String role = getRoleName(res.role());
                    String gender = getGenderName(res.gender());

                    return MemberListResponseView.builder()
                            .name(res.name())
                            .email(res.email())
                            .gender(gender)
                            .role(role)
                            .tel(res.tel())
                            .build();

                }).toList();
    }

    private String getGenderName(Gender gender) {
        switch (gender){
            case MALE -> {
                return "남성";
            }
            case FEMALE -> {
                return "여성";
            }

            default -> {
                return "성이 없음";
            }
        }
    }
    private String getRoleName(Role role) {
        switch (role) {
            case ADMIN -> {
                return "관리자";
            }

            case USER -> {
                return "일반 회원";
            }

            case SELLER -> {
                return "판매자";
            }

            default -> {
                return "권한이 없는 회원";
            }
        }


    }

}
