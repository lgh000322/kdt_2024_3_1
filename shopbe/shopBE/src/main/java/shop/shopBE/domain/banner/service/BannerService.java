package shop.shopBE.domain.banner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.authorityrequestfile.request.FileData;
import shop.shopBE.domain.banner.entity.Banner;
import shop.shopBE.domain.banner.exception.BannerExceptionCode;
import shop.shopBE.domain.banner.repository.BannerRepository;
import shop.shopBE.domain.banner.response.BannerResponse;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.utils.s3.S3Utils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final S3Utils s3Utils;

    @Transactional
    public void save(List<MultipartFile> files, Member member) {
        List<FileData> fileData = uploadFiles(files);
        List<Banner> banners = setBanners(fileData, member);
        bannerRepository.saveAll(banners);
    }

    @Transactional
    public void update(List<MultipartFile> files, List<Banner> banners, Member member) {
        // 배너 레코드 삭제 및 파일 삭제
        deleteBanners(banners);

        // 파일 저장
        List<Banner> bannersToSave = saveFiles(files, member);

        // 새로운 배너 레코드 추가
        bannerRepository.saveAll(bannersToSave);
    }


    public List<BannerResponse> getBanners() {
        return bannerRepository.getBanners()
                .orElseThrow(() -> new CustomException(BannerExceptionCode.BANNER_EMPTY));
    }

    @Transactional
    public void deleteById(Long bannerId) {
        // 배너 데이터 조회
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new CustomException(BannerExceptionCode.BANNER_NOT_FOUND));

        // s3의 파일 삭제
        s3Utils.deleteFile(banner.getSavedImageName());

        // 배너 데이터 삭제
        bannerRepository.deleteById(bannerId);
    }

    public Banner findById(Long bannerId) {
        return bannerRepository.findById(bannerId)
                .orElseThrow(() -> new CustomException(BannerExceptionCode.BANNER_NOT_FOUND));
    }

    private List<Banner> setBanners(List<FileData> fileDataList, Member member) {
        return fileDataList.stream()
                .map(fileData -> {
                    String originalFileName = fileData.originalFileName();
                    String savedFileName = fileData.savedFileName();

                    return Banner.createDefaultBanner(originalFileName, savedFileName, member);
                }).toList();
    }

    private List<FileData> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    String originFileName = file.getOriginalFilename();
                    String savedName = s3Utils.uploadFile(file);
                    return new FileData(originFileName, savedName);
                })
                .toList();
    }

    private List<Banner> saveFiles(List<MultipartFile> files, Member member) {
        List<Banner> result = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            result = files.stream()
                    .map(file -> {
                        String originalName = file.getOriginalFilename();
                        String savedName = s3Utils.uploadFile(file);
                        return Banner.createDefaultBanner(originalName, savedName, member);
                    })
                    .toList();
        }

        return result;
    }


    private void deleteBanners(List<Banner> banners) {
        for (Banner banner : banners) {
            s3Utils.deleteFile(banner.getSavedImageName());
            bannerRepository.deleteById(banner.getId());
        }
    }

}
