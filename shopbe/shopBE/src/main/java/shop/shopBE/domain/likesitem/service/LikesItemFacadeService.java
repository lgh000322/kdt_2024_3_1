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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesItemFacadeService {

    private final LikesItemService likesItemService;
    private final LikesService likesService;
    private final MemberService memberService;
    private final ProductService productService;

    public void setLikesItems(LikesItemInfo likesItemInfo, Long memberId) {
        //회원의 찜 보관함이 없으면 만들고 있으면 사용한다.(readonly = true) & (readonly = false)
        Likes likes = likesService.findLikesByMemberId(memberId)
                .orElseGet(() -> createLikes(memberId));

        //찜 상품 저장 (readonly=false)
        likesItemService.setLikesItems(likes, likesItemInfo.productId());
    }

    public List<ProductListViewModel> findLikesItems(Pageable pageable, Long memberId) {
        // 찜 보관함 조회 or 추가
        Likes likes = likesService.findLikesByMemberId(memberId)
                .orElseGet(() -> createLikes(memberId));

        // 상품 아이디의 리스트로 상품의 데이터 조회
        return productService.getProductListViewModels(likes.getId());
    }


    private Likes createLikes(Long memberId) {
        Likes likes = Likes.builder()
                .member(memberService.findById(memberId))
                .build();

        return likesService.save(likes);
    }

    public void deleteById(Long likesItemId,Long productId) {
        likesItemService.deleteById(likesItemId, productId);
    }

}
