package shop.shopBE.domain.destination.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.request.UpdateDestinationRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationFadeService {

    private final DestinationService destinationService;

    //배송지 조회
    public List<Destination> findDestinationList(Long memberId){
        return destinationService.findDestinationList(memberId);
    }

    public void updateDestination(Long memberId, UpdateDestinationRequest updateDestinationRequest) {
        destinationService.updateDestination(memberId, updateDestinationRequest);
    }

}
