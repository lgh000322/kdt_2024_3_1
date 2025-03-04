package shop.shopBE.domain.likesitem.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import shop.shopBE.TestConfig;
import shop.shopBE.domain.likesitem.request.LikesItemInfo;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.repository.ProductRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@Slf4j
class LikesItemFacadeServiceTest {

    @Autowired
    LikesItemFacadeService likesItemFacadeService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    Long memberIdGlobal = null;
    Long productIdGlobal = null;

    @BeforeEach
    void init() {
        Member member = Member.createDefaultMember("usernameTest", "nameTest", "aaa@aaa.com", Role.USER, false);
        Member savedMember = memberRepository.save(member);

        Product product = Product.createDefaultProduct(savedMember,
                ProductCategory.AQUA_SHOES,
                PersonCategory.ALL_PERSON,
                SeasonCategory.ALL_SEASON,
                "테스트 상품",
                100,
                10000,
                "테스트 상품 설명",
                LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        memberIdGlobal = savedMember.getId();
        productIdGlobal = savedProduct.getId();
    }

    @Test
    @DisplayName(value = "찜 상품 테스트")
    void 찜_상품_테스트_상품의_찜_카운트가_1만큼_증가해야한다() {
        //given
        LikesItemInfo likesItemInfo = new LikesItemInfo(productIdGlobal);
        int originLikeCount = productRepository.findById(productIdGlobal).get().getLikeCount();
        log.info("원래 찜 수={}", originLikeCount);

        //when
        likesItemFacadeService.setLikesItems(likesItemInfo, memberIdGlobal);

        //then
        int likeCount = productRepository.findById(productIdGlobal).get().getLikeCount();
        log.info("likeCount = {}", likeCount);

        assertEquals(likeCount, 1);
    }
}