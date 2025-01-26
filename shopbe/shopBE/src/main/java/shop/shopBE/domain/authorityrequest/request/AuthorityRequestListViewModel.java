package shop.shopBE.domain.authorityrequest.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AuthorityRequestListViewModel(
        @Min(value = 1) int page,
        @Max(value = 10) int size
) {
}
