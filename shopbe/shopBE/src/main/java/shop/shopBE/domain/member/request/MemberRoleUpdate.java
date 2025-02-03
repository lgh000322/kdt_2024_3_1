package shop.shopBE.domain.member.request;

import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.member.entity.enums.Role;

public record MemberRoleUpdate(
        @NotNull Role role
) {
}
