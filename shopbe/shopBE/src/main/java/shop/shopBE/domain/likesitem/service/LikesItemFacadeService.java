package shop.shopBE.domain.likesitem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.likes.exception.LikesExceptionCode;
import shop.shopBE.domain.likes.service.LikesService;
import shop.shopBE.domain.likesitem.entity.LikesItem;
import shop.shopBE.domain.likesitem.request.LikesItemInfo;
import shop.shopBE.domain.likesitem.request.LikesPaging;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.service.MemberService;
import shop.shopBE.domain.product.entity.Product;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.domain.product.service.ProductService;
import shop.shopBE.global.exception.custom.CustomException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesItemFacadeService {

    private final LikesItemService likesItemService;
    private final LikesService likesService;
    private final MemberService memberService;
    private final ProductService productService;

    public void setLikesItems(List<LikesItemInfo> likesItems, Long memberId) {
        //회원의 찜 보관함이 없으면 만들고 있으면 사용한다.(readonly = true) & (readonly = false)
        Likes likes = likesService.findLikesByMemberId(memberId)
                .orElseGet(() -> createLikes(memberId));

        // likesItems의 모든 상품의 기본키로 상품엔터티를 조회한다.
        List<Product> products = findProducts(likesItems); // (readonly = true)

        // 찜 상품 목록 생성
        List<LikesItem> likesItemList = setLikesItemList(products, likes);

        // 찜 상품 목록 저장
        likesItemService.setLikesItems(likesItemList); //(readonly = false)

    }

    public List<ProductListViewModel> findLikesItems(LikesPaging likesPaging, Long memberId) {
        // 페이징 정보
        Pageable pageable = PageRequest.of(likesPaging.page() - 1, likesPaging.size());

        // 찜 보관함 조회
        Likes likes = likesService.findLikesByMemberId(memberId)
                .orElseGet(() -> createLikes(memberId));

        // 찜 보관함으로 찜 아이템들을 조회, 페이징 적용, 상품 아이디의 리스트를 가져옴
        List<Long> productIds = likesItemService.getLikesItems(pageable, likes.getId());

        // 상품 아이디의 리스트로 상품의 데이터 조회
        return productService.getProductListViewModels(productIds);
    }

    private List<LikesItem> setLikesItemList(List<Product> products, Likes likes) {
        return products.stream()
                .map(product -> LikesItem.createLikesItem(likes, product))
                .toList();
    }

    private List<Product> findProducts(List<LikesItemInfo> likesItems) {
        return likesItems.stream()
                .map(likesItemInfo -> productService.findById(likesItemInfo.productId()))
                .toList();
    }

    private Likes createLikes(Long memberId) {
        Likes likes = Likes.builder()
                .member(memberService.findById(memberId))
                .build();

        return likesService.save(likes);
    }

}
