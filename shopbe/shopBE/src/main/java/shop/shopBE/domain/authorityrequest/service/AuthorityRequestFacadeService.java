package shop.shopBE.domain.authorityrequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.authorityrequest.request.AuthorityRequestModel;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListModel;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.service.MemberService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorityRequestFacadeService {

    private final AuthorityRequestService authorityRequestService;
    private final MemberService memberService;


    public void makeAuthorityRequest(List<MultipartFile> files,
                                     AuthorityRequestModel authorityRequestModel,
                                     Long memberId) {
        Member member = memberService.findById(memberId);
        authorityRequestService.save(authorityRequestModel.content(), files, member);
    }

    public void updateAuthority(Long authorityId) {
        authorityRequestService.updateAuthority(authorityId);
    }

    public List<AuthorityResponseListModel> findAuthorityRequests(Pageable pageable,String name) {
        return authorityRequestService.findAuthorityRequests(pageable,name);
    }

    public void deleteById(Long authorityId) {
        authorityRequestService.deleteById(authorityId);
    }
}

