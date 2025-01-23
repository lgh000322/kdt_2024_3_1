package shop.shopBE.domain.destination.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.exception.DestinationException;
import shop.shopBE.domain.destination.repository.DestinationRepository;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DestinationService {

    @Lazy
    private final DestinationRepository destinationRepository;

    //배송지 조회
    public List<DestinationListInfo> findDestinationList(Long memberId){
        return destinationRepository.findDestinationListByMemberId(memberId)
                .orElseThrow(() -> new CustomException(DestinationException.Destination_NOT_FOUND));
    }

    //배송지 추가(트랜잭션 적용)
    @Transactional
    public void addDestinationList(Long memberId, AddDestinationRequest addDestinationResponse) {
        // ✅ Repository에서 Destination 객체 생성 (저장 X)
        Destination newDestination = destinationRepository.addDestinationByMemberId(memberId, addDestinationResponse);

        // ✅ 여기서 save() 실행
        destinationRepository.save(newDestination);
    }

    //배송지 수정(트랜잭션 적용)
    @Transactional
    public void updateDestination(UpdateDestinationRequest updateDestinationRequest) {

        Destination destination = destinationRepository.findById(updateDestinationRequest.id())
                .orElseThrow(() -> new CustomException(DestinationException.Destination_NOT_FOUND));

        destination.updateDestination(updateDestinationRequest);
    }

    //배송지 삭제(트랜잭션 적용)
    @Transactional
    public void deleteDestination(Long DestinationId) {
        destinationRepository.deleteDestinationByID(DestinationId);
    }
}
