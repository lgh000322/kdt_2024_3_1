package shop.shopBE.domain.orderhistory.response;

import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.member.entity.Member;

import java.time.LocalDateTime;

public record OrderHistoryDto(
        Long id, //주문내역 ID
        int orderPrice, //총 주문 가격
        LocalDateTime createdAt, //주문 날짜
        Destination destination, //배송지 정보
        Member member //주문자ID
) {
}
