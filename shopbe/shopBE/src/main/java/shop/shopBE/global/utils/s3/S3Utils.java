package shop.shopBE.global.utils.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.utils.s3.exception.S3ExceptionCode;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Utils {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile) {
        // UUID를 사용하여 파일 이름 생성
        String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename());

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            // 파일 URL 반환 (S3의 기본 URL 형식 사용)
            return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
        } catch (AmazonServiceException e) {
            throw new CustomException(S3ExceptionCode.AWS_SERVICE_EXCEPTION);
        } catch (AmazonClientException e) {
            throw new CustomException(S3ExceptionCode.AWS_CLIENT_EXCEPTION);
        } catch (IOException e) {
            throw new CustomException(S3ExceptionCode.AWS_IOE);
        }
    }

    //fileName: 파일 객체의 이름
    public void deleteFile(String fileName) {
        try {
            amazonS3.deleteObject(bucket, fileName);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("AWS 서비스 오류: " + e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new RuntimeException("AWS 클라이언트 오류: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 실패: " + e.getMessage(), e);
        }
    }

}
