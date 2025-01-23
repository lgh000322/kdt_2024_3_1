package shop.shopBE.domain.orderproduct.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.destination.exception.DestinationException;
import shop.shopBE.domain.orderproduct.exception.OrderProductException;
import shop.shopBE.domain.orderproduct.repository.OrderProductRepository;
import shop.shopBE.domain.orderproduct.request.OrderProductInfo;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service //서비스
@RequiredArgsConstructor //생성자를 만들어줌 orderRepository를 매개변수로 받아와 this로 넣는 코드를 알아서해줌
public class OrderProductService {

    private final OrderProductRepository orderRepository;

    public List<OrderProductInfo> findOrderProductByHistoryId(Long historyId){
        return orderRepository.findOrderProductByHistoryId(historyId)
                .orElseThrow(() -> new CustomException(OrderProductException.OrderProduct_NOT_FOUND));
    }
}
