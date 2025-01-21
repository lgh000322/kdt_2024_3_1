package shop.shopBE.domain.product.response;

public record ProductListViewModel(
        Long productId,
        String imageUrl,
        String title
) {
}
