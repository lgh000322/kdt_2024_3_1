package shop.shopBE.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.product.entity.enums.PersonCategory;
import shop.shopBE.domain.product.entity.enums.ProductCategory;
import shop.shopBE.domain.product.entity.enums.SeasonCategory;
import shop.shopBE.domain.product.exception.ProductExceptionCode;
import shop.shopBE.global.exception.custom.CustomException;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    private PersonCategory personCategory;

    @Enumerated(EnumType.STRING)
    private SeasonCategory seasonCategory;

    private String productName;

    private int totalStock;

    private int price;

    @Builder.Default
    private int salesVolume = 0; // 상품 판매량 (1월 25일 추가) -> 상품 판매량(인기순) 조회시 사용, default = 0;

    @Builder.Default
    private int likeCount = 0; // 인기순 확인(좋아요숫자) 을 위한 count 디폴트 0;

    private String description;

    // 상품 등록 날짜
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;   // seller

    @Builder.Default
    // 논리적 삭제를 위한 필드 추가.
    private boolean isDeleted = false;


    public static Product createDefaultProduct(Member member,
                                               ProductCategory productCategory,
                                               PersonCategory personCategory,
                                               SeasonCategory seasonCategory,
                                               String productName,
                                               int totalStock,
                                               int price,
                                               String description,
                                               LocalDateTime createdAt) {
        return Product.builder()
                .productCategory(productCategory)
                .personCategory(personCategory)
                .seasonCategory(seasonCategory)
                .productName(productName)
                .totalStock(totalStock)
                .price(price)
                .description(description)
                .createdAt(createdAt)
                .build();
    }


    // 상품 논리적 제거 메서드
    public void deleteProduct(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 상품 재고감소 (업데이트) 메서드 - 상품재고가 0인 상태에서 minusTotalStock을 한다면 에러를 보내줌
    public void minusTotalStock(int stock) {


        // 현재 stock이 0일경우 재고를 줄일수 없음
        if(this.totalStock < 0){
            // 400에러를 날려준다. 재고 감소할수없다는 에러 메세지를 보내줌.
            throw new CustomException(ProductExceptionCode.OUT_OF_STOCK);
        }
        this.totalStock -= stock;
    }

    // 찜숫자 감소(업데이트) 메서드 - 찜숫자가 0인 상태에서 minusLikeCOunt를 한다면 에러를 보내줌.
    public void minusLikeCount() {
        if(this.likeCount < 0) {
            // 400에러를 날려준다. 좋아요 감소할수없다는 에러 메세지를 보내줌.
            throw new CustomException(ProductExceptionCode.INVALID_MINUS_LIKE_REQUEST);
        }
        this.likeCount -= 1;
    }

    // 판매량 증가 (업데이트) 메서드
    public void plusSalesVolume(int salesVolume) {
        this.salesVolume += salesVolume;
    }
    // 찜숫자 증가 (업데이트) 메서드
    public void plusLikeCount() {
        this.likeCount += 1;
    }

    // 상품 재고증가 (업데이트) 메서드
    public void plusTotalStock(int stock) {
        this.totalStock += stock;
    }
}
