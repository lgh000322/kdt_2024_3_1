package shop.shopBE.domain.cart.response;

public record CartInform(String productStatus,
                         String productName,
                         String imgaddress,
                         int stock,
                         int price) {
}
