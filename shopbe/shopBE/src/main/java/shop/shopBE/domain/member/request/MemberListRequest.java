package shop.shopBE.domain.member.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record MemberListRequest(
        @NotEmpty
        @Min(value = 1)
        int page,

        @NotEmpty
        @Max(value = 10)
        int size
        ) {
}
