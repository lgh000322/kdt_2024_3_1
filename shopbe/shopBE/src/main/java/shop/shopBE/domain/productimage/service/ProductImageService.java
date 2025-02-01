package shop.shopBE.domain.productimage.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.domain.authorityrequestfile.request.FileData;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.productimage.entity.ProductImage;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;
import shop.shopBE.domain.productimage.exception.ProductImageExceptionCode;
import shop.shopBE.domain.productimage.repository.ProductImageRepository;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.utils.s3.S3Utils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final S3Utils s3Utils;

    public List<String> findSideImgsByProductId(Long productId){
        List<String> sideImgUrls =
                productImageRepository.findSideImgUrlsByProductId(productId)
                        .orElseThrow(() -> new CustomException(ProductImageExceptionCode.SIDE_IMAGE_NOT_FOUND));
        return sideImgUrls;
    }


    // productImageService를 호출하는 productService에 트랜잭션을 열어놔서 따로 트랜잭션을 열지않음.
    public void saveMainImg(MultipartFile mainImgFile, Product product) {
        FileData fileData = uploadMainImg(mainImgFile);
        ProductImage productImage = ProductImage.createDefaultProductImage(fileData.originalFileName(),
                fileData.savedFileName(),
                ProductImageCategory.MAIN,
                product);

        productImageRepository.save(productImage);
    }

    // productImageService를 호출하는 productService에 트랜잭션을 열어놔서 따로 트랜잭션을 열지않음.
    public void saveSideImgs(List<MultipartFile> sideImgFiles, Product product) {
        List<FileData> sideImgFileDatas = uploadSideImgs(sideImgFiles);

        for (FileData sideImgFileData : sideImgFileDatas) {
            ProductImage productImage = ProductImage.createDefaultProductImage(sideImgFileData.originalFileName(),
                    sideImgFileData.savedFileName(),
                    ProductImageCategory.SIDE,
                    product);

            productImageRepository.save(productImage);
        }
    }



    //사이드 이미지들을 업로드후 파일데이터리스트를 반환.
    private List<FileData> uploadSideImgs(List<MultipartFile> sideImgFiles) {
        return sideImgFiles.stream().map(file -> {
                    String originalFilename = file.getOriginalFilename();
                    String savedFilename = s3Utils.uploadFile(file);
                    return new FileData(originalFilename, savedFilename);
                }).toList();
    }

    // 메인이미지를 s3에 업로드후 filedata반환.
    private FileData uploadMainImg(MultipartFile mainImgFile) {
        String originalFileName = mainImgFile.getOriginalFilename();
        String savedFileName = s3Utils.uploadFile(mainImgFile);
        return new FileData(originalFileName, savedFileName);
    }


}
