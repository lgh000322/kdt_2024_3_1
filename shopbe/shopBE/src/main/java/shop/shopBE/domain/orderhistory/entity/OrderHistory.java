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

    private int orderPrice;

    // 주문 날짜
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public OrderHistory(int orderPrice, LocalDateTime createdAt, Destination destination, Member member) {
        this.orderPrice = orderPrice;
        this.createdAt = createdAt;
        this.destination = destination;
        this.member = member;
    }
}
