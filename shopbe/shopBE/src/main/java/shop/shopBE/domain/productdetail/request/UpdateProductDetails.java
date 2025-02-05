package shop.shopBE.domain.productdetail.request;

public record UpdateProductDetails(
        Long productDetailId,
        int quantity
) {
}
