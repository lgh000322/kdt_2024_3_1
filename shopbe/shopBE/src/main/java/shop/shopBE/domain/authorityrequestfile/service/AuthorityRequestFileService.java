package shop.shopBE.domain.authorityrequestfile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.authorityrequest.entity.AuthorityRequest;
import shop.shopBE.domain.authorityrequestfile.entity.AuthorityRequestFile;
import shop.shopBE.domain.authorityrequestfile.repository.AuthorityRequestFileRepository;
import shop.shopBE.domain.authorityrequestfile.request.FileData;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityRequestFileService {

    private final AuthorityRequestFileRepository authorityRequestFileRepository;

    @Transactional
    public void saveAll(List<FileData> fileData, AuthorityRequest authorityRequest) {
        List<AuthorityRequestFile> authorityRequestFiles = getAuthorityRequestFiles(fileData, authorityRequest);
        authorityRequestFileRepository.saveAll(authorityRequestFiles);
    }


    private List<AuthorityRequestFile> getAuthorityRequestFiles(List<FileData> fileDataList, AuthorityRequest authorityRequest) {
        return fileDataList.stream()
                .map(fileData -> {
                    String originFileName = fileData.originalFileName();
                    String savedName = fileData.savedFileName();
                    return AuthorityRequestFile.createDefaultAuthorityRequestFile(originFileName, savedName, authorityRequest);
                }).toList();
    }

}
