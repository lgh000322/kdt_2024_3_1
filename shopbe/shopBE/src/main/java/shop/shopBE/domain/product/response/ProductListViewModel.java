package shop.shopBE.domain.product.response;

public record ProductListViewModel(
        Long productId,
        Long likesId,
        String imageUrl,
        String title
) {
}
