package shop.shopBE.domain.likesitem.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.likes.repository.LikesRepository;
import shop.shopBE.domain.likesitem.entity.LikesItem;
import shop.shopBE.domain.likesitem.request.LikesItemInfo;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.repository.ProductRepository;

import java.awt.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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

        Product product = Product.builder()
                .productCategory(ProductCategory.AQUA_SHOES)
                .personCategory(PersonCategory.ALL_PERSON)
                .seasonCategory(SeasonCategory.ALL_SEASON)
                .productName("상품 1")
                .totalStock(100)
                .price(10000)
                .description("테스트 상품입니다.")
                .createdAt(LocalDateTime.now())
                .build();

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