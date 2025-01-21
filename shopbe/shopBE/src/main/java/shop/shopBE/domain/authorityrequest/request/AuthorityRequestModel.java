package shop.shopBE.domain.authorityrequest.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record AuthorityRequestModel(
        @NotEmpty @NotNull String content// 판매자 등록 사유
) {
}
