package shop.shopBE.domain.authorityrequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;
import shop.shopBE.domain.authorityrequest.exception.AuthorityExceptionCode;
import shop.shopBE.domain.authorityrequest.repository.AuthorityRequestRepository;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListModel;
import shop.shopBE.domain.authorityrequestfile.request.FileData;
import shop.shopBE.domain.authorityrequestfile.service.AuthorityRequestFileService;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.utils.s3.S3Utils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorityRequestService {

    private final AuthorityRequestRepository authorityRequestRepository;
    private final AuthorityRequestFileService authorityRequestFileService;
    private final S3Utils s3Utils;

    @Transactional
    public void save(String content,  List<MultipartFile> files, Member member) {
        AuthorityRequest authorityRequest = authorityRequestRepository.save(AuthorityRequest.createDefaultAuthorityRequest(content, member));
        List<FileData> fileData = uploadFiles(files);
        authorityRequestFileService.saveAll(fileData, authorityRequest);
    }

    @Transactional
    public void updateAuthority(Long authorityId) {
        // 페치 조인하지 않으면 회원을 가져올 때 N+1 문제가 발생함..
        AuthorityRequest authorityRequest = authorityRequestRepository.findByIdFetchJoin(authorityId)
                .orElseThrow(() -> new CustomException(AuthorityExceptionCode.AUTHORITY_NOT_FOUND));

        Member member = authorityRequest.getMember();
        member.changeRole(Role.SELLER);
        authorityRequest.update(true);
    }

    public List<AuthorityResponseListModel> findAuthorityRequests(Pageable pageable) {
        return authorityRequestRepository.findAuthorityRequests(pageable)
                .orElseThrow(() -> new CustomException(AuthorityExceptionCode.AUTHORITY_NOT_EXISTS));
    }

    private List<FileData> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    String originFileName = file.getOriginalFilename();
                    String savedName = s3Utils.uploadFile(file);
                    return new FileData(originFileName, savedName);
                })
                .toList();
    }
}
