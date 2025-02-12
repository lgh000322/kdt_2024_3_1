package shop.shopBE.domain.destination.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.domain.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationName;

    private String receiverName;

    private String tel;

    private String address;

    // 우편번호
    private Long zipCode;

    // 기본 배송지: true => 기본 배송지 설정됨
    private boolean isSelectedDestination;

    private String deliveryMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateDestination(UpdateDestinationRequest updateDestinationRequest) {
        this.destinationName= updateDestinationRequest.destinationName();
        this.receiverName= updateDestinationRequest.receiverName();
        this.tel= updateDestinationRequest.tel();
        this.address = updateDestinationRequest.address();
        this.zipCode = updateDestinationRequest.zipCode();
        this.deliveryMessage = updateDestinationRequest.deliverMessage();
        this.isSelectedDestination = updateDestinationRequest.isSelectedDestination();
    }

    public void changeDefaultDestination(boolean isSelected){
        this.isSelectedDestination = isSelected;
    }

    public static Destination createDefaultDestination(String destinationName, String receiverName, String tel, String address, Long zipCode, Member member) {
        return Destination.builder()
                .destinationName(destinationName)
                .receiverName(receiverName)
                .tel(tel)
                .address(address)
                .zipCode(zipCode)
                .isSelectedDestination(true)
                .member(member)
                .build();
    }
}
