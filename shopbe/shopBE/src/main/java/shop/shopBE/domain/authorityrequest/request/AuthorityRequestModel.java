package shop.shopBE.domain.authorityrequest.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorityRequestModel(
        @NotEmpty @NotNull @Size(max = 30) String content// 판매자 등록 사유
) {
}
