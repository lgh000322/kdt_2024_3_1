package shop.shopBE.domain.destination.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;
import shop.shopBE.domain.destination.response.DestinationListInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationFadeService {

    private final DestinationService destinationService;

    //배송지 조회
    public List<DestinationListInfo> findDestinationList(Long memberId){
        return destinationService.findDestinationList(memberId);
    }

    public void updateDestination(UpdateDestinationRequest updateDestinationRequest,Long destinationId) {
        destinationService.updateDestination(updateDestinationRequest,destinationId);
    }

}
