package shop.shopBE.domain.banner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.banner.entity.Banner;
import shop.shopBE.domain.banner.request.BannerUpdateRequestForDelete;
import shop.shopBE.domain.banner.response.BannerResponse;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.service.MemberService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerFacadeService {
    private final BannerService bannerService;
    private final MemberService memberService;

    public void saveBanners(List<MultipartFile> files, Long memberId) {
        Member member = memberService.findById(memberId);
        bannerService.save(files, member);
    }

    public List<BannerResponse> getBanners() {
        return bannerService.getBanners();
    }

    public void deleteById(Long bannerId) {
        bannerService.deleteById(bannerId);
    }

    public void updateFiles(List<MultipartFile> saveFiles, List<BannerUpdateRequestForDelete> bannerUpdateRequestForDeletes, Long memberId) {
        // 삭제할 배너를 조회 => 읽기 트랜잭션 1
        List<Banner> banners = getBanners((bannerUpdateRequestForDeletes));

        // 현재 로그인한 관리자 회원을 조회 => 읽기 트랜잭션 2
        Member member = memberService.findById(memberId);

        // 파일 삭제, 파일 ㅇ
        bannerService.update(saveFiles, banners, member);
    }

    private List<Banner> getBanners(List<BannerUpdateRequestForDelete> bannerUpdateRequestForDeletes) {
        List<Banner> list = bannerUpdateRequestForDeletes.stream()
                .map(bannerUpdateRequestForDelete -> {
                    return bannerService.findById(bannerUpdateRequestForDelete.bannerId());
                })
                .toList();

        return list;
    }




}
