package shop.shopBE.domain.authorityrequest.response;

import java.time.LocalDateTime;

public record AuthorityResponseListViewModel(
        Long authorityId, // 권한 신청의 기본키/번호
        String memberName, // 등록자
        String title, // 제목
        LocalDateTime createAt // 등록일
) {
}
