package shop.shopBE.domain.product.response;


// 메인페이지 상품리스트 DTO
public record ProductCardViewModel(
        Long productId,
        String imgUrl,
        String productName,
        int price
) {
}
