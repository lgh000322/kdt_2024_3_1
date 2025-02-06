package shop.shopBE.domain.authorityrequest.response;

import java.time.LocalDateTime;

public record AuthorityResponseListModel(
        Long authorityId, // 권한 신청의 기본키/번호
        String memberName, // 등록자
        String title, // 제목 => 사유
        LocalDateTime createAt // 등록일
) {
}
