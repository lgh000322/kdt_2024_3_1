package shop.shopBE.domain.authorityrequestfile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.authorityrequestfile.request.AuthFileData;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityRequestFileFacadeService {

    private final AuthorityRequestFileService authorityRequestFileService;

    public List<AuthFileData> getFiles(Long authorityId) {
        return authorityRequestFileService.findFilesByAuthorityId(authorityId);
    }

}
