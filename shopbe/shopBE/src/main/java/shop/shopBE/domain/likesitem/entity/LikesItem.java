package shop.shopBE.domain.likesitem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.product.entity.Product;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LikesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likes_id")
    private Likes likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static LikesItem createLikesItem(Likes likes, Product product) {
        return LikesItem.builder()
                .likes(likes)
                .product(product)
                .build();
    }
}
