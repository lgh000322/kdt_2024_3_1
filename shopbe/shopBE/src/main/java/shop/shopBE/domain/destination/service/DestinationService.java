package shop.shopBE.domain.destination.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.exception.DestinationException;
import shop.shopBE.domain.destination.repository.DestinationRepository;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.exception.MemberExceptionCode;
import shop.shopBE.domain.member.repository.MemberRepository;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DestinationService {
    private final DestinationRepository destinationRepository;
    private final MemberRepository memberRepository;

    //배송지 조회
    public List<DestinationListInfo> findDestinationList(Long memberId) {
        return destinationRepository.findDestinationListByMemberId(memberId)
                .orElseThrow(() -> new CustomException(DestinationException.DESTINATION_NOT_FOUND));
    }

    //배송지 추가(트랜잭션 적용)
    @Transactional
    public void addDestinationList(Long memberId, AddDestinationRequest addDestinationResponse) {
        // 회원의 아이디로 회원 객체를 찾는다
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberExceptionCode.MEMBER_NOT_FOUND));

        // 추가되는 기본 배송지 여부가 true라면 다른 배송지들의 기본배송지 여부를 false시킨다.
        if (addDestinationResponse.isSelectedDestination()) {
            setAllDefaultDestinationsFalse(memberId);
        }

        // ✅ Repository에서 Destination 객체 생성 (저장 X)
        Destination destination = Destination.createDefaultDestination(
                addDestinationResponse.destinationName(),
                addDestinationResponse.receiverName(),
                addDestinationResponse.tel(),
                addDestinationResponse.address(),
                addDestinationResponse.zipCode(),
                member
        );

        // ✅ 여기서 save() 실행
        destinationRepository.save(destination);
    }

    //배송지 수정(트랜잭션 적용)
    @Transactional
    public void updateDestination(UpdateDestinationRequest updateDestinationRequest,Long destinationId) {

        //1. 수정할 배송지 정보를 찾는다.
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new CustomException(DestinationException.DESTINATION_NOT_FOUND));

        // 2. 기존 기본 배송지들을 모두 false로 변경
        if (updateDestinationRequest.isSelectedDestination()) {
            setAllDefaultDestinationsFalse(destination.getMember().getId());
        }
        
        //3. 수정할 배송지 내용을 업데이트
        destination.updateDestination(updateDestinationRequest);
    }

    //배송지 삭제(트랜잭션 적용)
    @Transactional
    public void deleteDestination(Long destinationId) {
        destinationRepository.deleteById(destinationId);
    }


    //유저의 기본 배송지들을 모두 false 시키는 메소드(추가 and 수정 될때 사용)
    private void setAllDefaultDestinationsFalse(Long memberId) {
        List<Destination> existingDestinations = destinationRepository.findDestinationsByMemberId(memberId);

        for (Destination destination : existingDestinations) {
            if (destination.isSelectedDestination()) {
                destination.changeDefaultDestination(false); // 기본 배송지 해제
//                destinationRepository.save(destination); // 변경 저장
            }
        }
    }

}
