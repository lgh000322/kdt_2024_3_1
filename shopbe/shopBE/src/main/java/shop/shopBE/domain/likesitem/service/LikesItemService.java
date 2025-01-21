package shop.shopBE.domain.likesitem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.likesitem.entity.LikesItem;
import shop.shopBE.domain.likesitem.repository.LikesItemRepository;
import shop.shopBE.domain.product.entity.Product;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesItemService {

    private final LikesItemRepository likesItemRepository;

    @Transactional
    public void setLikesItems(List<LikesItem> likesItemList) {
        likesItemRepository.saveAll(likesItemList);
    }



}
