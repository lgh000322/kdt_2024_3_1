package shop.shopBE.domain.destination.repository;

import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.request.AddDestinationRequest;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;

import java.util.List;

public interface DestinationRepositoryCustom {
    //배송지 조회
    List<Destination> findDestinationIdByMemberId(Long memberId);


    //배송지 추가
    void addDestinationByMemberId(Long memberId, AddDestinationRequest addDestinationResponse);

    //배송지 삭제
    void updateDestinationByID(Long memberId, UpdateDestinationRequest updateDestinationResponse);

    void deleteDestinationByID(Long destinationId);
}
