package shop.shopBE.domain.productdetail.response;

public record ProductDetails(
        Long productDetailsId,
        int size,
        int quantityBySize
) {
}
