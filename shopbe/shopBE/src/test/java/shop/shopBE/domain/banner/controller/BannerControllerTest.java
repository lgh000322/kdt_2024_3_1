package shop.shopBE.domain.banner.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.shopBE.TestConfig;
import shop.shopBE.domain.banner.entity.Banner;
import shop.shopBE.domain.banner.response.BannerResponse;
import shop.shopBE.domain.banner.service.BannerFacadeService;
import shop.shopBE.global.filter.JwtAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BannerController.class)
@AutoConfigureMockMvc(addFilters = false)
class BannerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BannerFacadeService bannerFacadeService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;


    @Test
    @DisplayName("배너 사진을 조회한다")
//    @WithMockUser
    void 배너_사진_조회() throws Exception {
        BDDMockito.given(bannerFacadeService.getBanners())
                .willReturn(new ArrayList<BannerResponse>(Arrays.asList(new BannerResponse(123L, "이미지경로"))));

        mockMvc.perform(MockMvcRequestBuilders.get("/banners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].bannerId").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].imageUrl").value("이미지경로"));
//                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bannerFacadeService,Mockito.times(1)).getBanners();

    }
}