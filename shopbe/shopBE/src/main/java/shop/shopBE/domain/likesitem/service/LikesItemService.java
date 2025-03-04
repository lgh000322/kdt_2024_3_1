package shop.shopBE.domain.likesitem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.likesitem.entity.LikesItem;
import shop.shopBE.domain.likesitem.exception.LikesItemExceptionCode;
import shop.shopBE.domain.likesitem.repository.LikesItemRepository;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.exception.ProductExceptionCode;
import shop.shopBE.domain.product.repository.ProductRepository;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesItemService {

    private final LikesItemRepository likesItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void setLikesItems(Likes likes, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));

        product.plusLikeCount();

        LikesItem likesItem = LikesItem.createLikesItem(likes, product);
        likesItemRepository.save(likesItem);
    }

    @Transactional
    public void deleteById(Long likesItemId, Long productId) {
        // 찜 상품을 제거한다.
        likesItemRepository.deleteById(likesItemId);

        // 변경 감지를 위해 영속성 컨텍스트에 Product를 올린다.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.NOT_FOUND));

        // Product의 값이 영속성 컨텍스트의 스냅샷 데이터와 다르기 때문에 업데이트 쿼리가 자동으로 나간다.
        product.minusLikeCount();
    }




}
