package shop.shopBE.domain.cartitem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.cart.entity.Cart;
import shop.shopBE.domain.cartitem.request.UpdateCartItemInform;
import shop.shopBE.domain.product.entity.Product;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int itemCount;

    private int itemPrice;

    private int itemSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // 변경감지 업데이트를 위한 메서드
    public void updateCartItem(UpdateCartItemInform updateCartItemInform) {
        this.itemCount = updateCartItemInform.quantity();
        this.itemSize = updateCartItemInform.size();
    }
}
