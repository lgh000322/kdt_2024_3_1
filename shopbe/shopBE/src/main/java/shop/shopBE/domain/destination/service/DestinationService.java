package shop.shopBE.domain.destination.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.exception.DestinationException;
import shop.shopBE.domain.destination.repository.DestinationRepository;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    //배송지 조회
    public List<Destination> findDestinationList(Long memberId){
        return Optional.of(destinationRepository.findDestinationIdByMemberId(memberId))
                .filter(list -> !list.isEmpty()) // 리스트가 비어있지 않으면 그대로 반환
                .orElseThrow(() -> new CustomException(DestinationException.Destination_NOT_FOUND));
    }

    //배송지 추가(트랜잭션 적용)
    @Transactional
    public void addDestinationList(Long memberId, AddDestinationRequest addDestinationResponse) {
        destinationRepository.addDestinationByMemberId(memberId, addDestinationResponse);
    }

    //배송지 수정(트랜잭션 적용)
    @Transactional
    public void updateDestination(Long memberId, UpdateDestinationRequest updateDestinationRequest) {

        destinationRepository.updateDestinationByID(memberId, updateDestinationRequest);
    }

    //배송지 삭제(트랜잭션 적용)
    @Transactional
    public void deleteDestination(Long DestinationId) {
        destinationRepository.deleteDestinationByID(DestinationId);
    }
}
