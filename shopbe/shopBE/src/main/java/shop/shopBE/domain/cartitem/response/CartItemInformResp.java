package shop.shopBE.domain.cartitem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.cart.response.CartInform;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemInformResp {
    private Long productId;         // 프로덕트 아이디  ->  상품구매시 필요
    private Long cartItemId;        // 장바구니 상품 번호 -> 장바구니 상품 정보 수정시 필요
    private String imgUrl;          //이미지 주소
    private String productStatus; // 프로덕트 스테이스 (판매중, 품절)
    private String productName;  // 프로덕트 이름
//    private int shoesSize;      // 프로덕트 디테일 사이즈
//    private int sizeStock;      // 프로덕트 디테일 수량
    private int cartItemCount; // 장바구니 아이템 수량  -> 프로덕트 디테일 수량 - 장바구니 아이템 수량이  0 보다 작을경우 구매시 옵션변경요청.
    private int cartItemPrice;  // 장바구니 아이템 가격(수량 X 상품가격)


    public void addCartItemInformAndStatus(CartItemInform cartItemInform, String productStatus){
        this.productId = cartItemInform.getProductId();
        this.cartItemId = cartItemInform.getCartItemId();
        this.imgUrl = cartItemInform.getImgUrl();
        this.productName = cartItemInform.getProductName();
//        this.shoesSize = cartItemInform.getShoesSize();
//        this.sizeStock = cartItemInform.getSizeStock();
        this.cartItemCount = cartItemInform.getCartItemCount();
        this.cartItemPrice = cartItemInform.getCartItemPrice();
        this.productStatus = productStatus;
    }
}
