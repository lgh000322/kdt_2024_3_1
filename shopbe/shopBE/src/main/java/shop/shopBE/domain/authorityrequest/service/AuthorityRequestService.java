package shop.shopBE.domain.authorityrequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;
import shop.shopBE.domain.authorityrequest.exception.AuthorityExceptionCode;
import shop.shopBE.domain.authorityrequest.repository.AuthorityRequestRepository;
import shop.shopBE.domain.authorityrequest.request.AuthorityRequestListViewModel;
import shop.shopBE.domain.authorityrequest.response.AuthorityResponseListViewModel;
import shop.shopBE.domain.authorityrequestfile.request.FileData;
import shop.shopBE.domain.authorityrequestfile.service.AuthorityRequestFileService;
import shop.shopBE.domain.member.entity.Member;
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

        // Todo 데이터베이스 저장에 실패했을 때 파일을 삭제하는 작업 추가해야 함
        authorityRequestFileService.saveAll(fileData, authorityRequest);
    }

    @Transactional
    public void updateAuthority(Long authorityId) {
        AuthorityRequest authorityRequest = authorityRequestRepository.findById(authorityId)
                .orElseThrow(() -> new CustomException(AuthorityExceptionCode.AUTHORITY_NOT_FOUND));

        authorityRequest.update(true);
    }

    public List<AuthorityResponseListViewModel> findAuthorityRequests(AuthorityRequestListViewModel authorityRequestListViewModel) {
        return authorityRequestRepository.findAuthorityRequests(authorityRequestListViewModel.page(), authorityRequestListViewModel.size())
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
