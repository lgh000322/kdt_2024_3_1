package shop.shopBE.domain.orderhistory.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;

import java.time.LocalDateTime;

@Builder
public record OrderHistoryInfo(
        @NotNull
        @Min(value = 1)
        int page,

        @NotNull
        @Max(value = 10)
        int size
) {}

