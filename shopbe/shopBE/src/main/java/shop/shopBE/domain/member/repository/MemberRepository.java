package shop.shopBE.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopBE.domain.member.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByUsername(String username);

    Optional<Member> findBySub(UUID sub);
}
