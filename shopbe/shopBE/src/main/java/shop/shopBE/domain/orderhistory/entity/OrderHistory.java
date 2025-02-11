package shop.shopBE.domain.orderhistory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품의 총 가격
    private int orderPrice;

    // 총 주문한 상품 종류의 수
    private int orderCount;

    // 주문 날짜
    private LocalDateTime createdAt;


    private String address; //주소

    private String destinationName; //배송지 이름

    private String receiverName; //받는 사람

    private String tel; //전화번호

    private Long zipCode; //우편번호

    private String deliveryMessage; // 배송 메시지

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static OrderHistory createDefaultOrderHistory(int orderPrice, int orderCount, String address, String destinationName, String receiverName, String tel, Long zipCode, String deliveryMessage, Member member) {
        return OrderHistory.builder()
                .orderPrice(orderPrice)
                .orderCount(orderCount)
                .address(address)
                .destinationName(destinationName)
                .receiverName(receiverName)
                .tel(tel)
                .zipCode(zipCode)
                .deliveryMessage(deliveryMessage)
                .member(member)
                .orderCount(orderCount)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void delete(){
        this.isDeleted = true;
    }

}
