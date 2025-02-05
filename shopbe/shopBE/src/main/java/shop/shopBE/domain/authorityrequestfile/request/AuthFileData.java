package shop.shopBE.domain.authorityrequestfile.request;

public record AuthFileData(
        Long fileId, // 파일의 기본키

        String imageUrl // 파일이 저장된 경로
) {
}
