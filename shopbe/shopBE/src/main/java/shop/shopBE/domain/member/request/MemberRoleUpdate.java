package shop.shopBE.domain.member.request;

import jakarta.validation.constraints.NotEmpty;
import shop.shopBE.domain.member.entity.enums.Role;

public record MemberRoleUpdate(
        @NotEmpty Role role
) {
}
