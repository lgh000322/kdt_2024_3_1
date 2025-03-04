package shop.shopBE.domain.banner.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.shopBE.domain.banner.entity.Banner;
import shop.shopBE.domain.banner.response.BannerResponse;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.service.MemberService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BannerFacadeServiceTest {
    @InjectMocks
    private BannerFacadeService bannerFacadeService;

    @Mock
    private BannerService bannerService;

    @Mock
    private MemberService memberService;

    private Banner banner;
    private Member member;

    @BeforeEach
    void init() {
        member = Member.createDefaultMember("테스트유저네임", "테스트네임", "테스트이메일", Role.USER, true);
        banner = new Banner(1L, "원래 배너 이름", "저장된 배너 이름", member);
    }

    @Test
    @DisplayName("배너 조회 테스트")
    void 배너조회_테스트_성공() {
        BannerResponse bannerResponse = new BannerResponse(banner.getId(), banner.getSavedImageName());
        List<BannerResponse> result = new ArrayList<>(List.of(bannerResponse));

        when(bannerFacadeService.getBanners()).thenReturn(result);

        List<BannerResponse> response = bannerFacadeService.getBanners();

        assertEquals(response.size(), 1);
        assertEquals(response.get(0).bannerId(), result.get(0).bannerId());
        assertEquals(response.get(0).imageUrl(), result.get(0).imageUrl());

        verify(bannerService, times(1)).getBanners();
    }




}