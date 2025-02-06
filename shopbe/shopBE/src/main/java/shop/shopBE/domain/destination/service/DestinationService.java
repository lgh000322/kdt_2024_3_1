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

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new CustomException(DestinationException.DESTINATION_NOT_FOUND));

        destination.updateDestination(updateDestinationRequest);
    }

    //배송지 삭제(트랜잭션 적용)
    @Transactional
    public void deleteDestination(Long destinationId) {
        destinationRepository.deleteById(destinationId);
    }
}
