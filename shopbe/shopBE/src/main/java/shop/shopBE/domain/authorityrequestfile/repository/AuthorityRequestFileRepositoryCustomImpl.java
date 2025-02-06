package shop.shopBE.domain.authorityrequestfile.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.authorityrequestfile.entity.AuthorityRequestFile;
import shop.shopBE.domain.authorityrequestfile.entity.QAuthorityRequestFile;
import shop.shopBE.domain.authorityrequestfile.request.AuthFileData;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.authorityrequestfile.entity.QAuthorityRequestFile.authorityRequestFile;

@RequiredArgsConstructor
public class AuthorityRequestFileRepositoryCustomImpl implements AuthorityRequestFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<AuthFileData>> findByAuthorityId(Long authorityId) {
        List<AuthFileData> result = queryFactory
                .select(Projections.constructor(AuthFileData.class,
                        authorityRequestFile.id,
                        authorityRequestFile.savedName
                ))
                .from(authorityRequestFile)
                .where(authorityRequestFile.authorityRequest.id.eq(authorityId))
                .fetch();

        return Optional.ofNullable(result);

    }
}
