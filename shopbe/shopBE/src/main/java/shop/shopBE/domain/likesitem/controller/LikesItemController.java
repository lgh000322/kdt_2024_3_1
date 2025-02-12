package shop.shopBE.domain.likesitem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.shopBE.domain.likesitem.request.LikesItemDeleteInfo;
import shop.shopBE.domain.likesitem.request.LikesItemInfo;
import shop.shopBE.domain.likesitem.request.LikesPaging;
import shop.shopBE.domain.likesitem.service.LikesItemFacadeService;
import shop.shopBE.domain.likesitem.service.LikesItemService;
import shop.shopBE.domain.product.response.ProductListViewModel;
import shop.shopBE.global.config.security.mapper.token.AuthToken;
import shop.shopBE.global.response.ResponseFormat;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "찜 아이템", description = "찜 아이템 관련 API")
public class LikesItemController {

    private final LikesItemFacadeService likesItemFacadeService;

    @PostMapping("/likes/item")
    @Operation(summary = "찜 아이템 저장", description = "현재 로그인 한 회원의 찜아이템 목록을 추가한다.")
    public ResponseEntity<ResponseFormat<Void>> addLikesItems(@RequestBody @Valid LikesItemInfo likesItemInfo,
                                                              @AuthenticationPrincipal AuthToken authToken) {
        likesItemFacadeService.setLikesItems(likesItemInfo, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("찜 아이템 등록에 성공했습니다."));
    }

    @GetMapping("/likes/items")
    @Operation(summary = "찜 아이템 조회", description = "현재 로그인 한 회원의 찜아이템 목록을 조회한다.")
    public ResponseEntity<ResponseFormat<List<ProductListViewModel>>> getLikesItems(@PageableDefault Pageable pageable,
                                                                                    @AuthenticationPrincipal AuthToken authToken) {
        List<ProductListViewModel> likesItems = likesItemFacadeService.findLikesItems(pageable, authToken.getId());
        return ResponseEntity.ok().body(ResponseFormat.of("찜 아이템 조회 성공.", likesItems));
    }

    @DeleteMapping("/likes/item/{likesItemId}")
    @Operation(summary = "찜 아이템 삭제", description = "현재 로그인 한 회원의 찜 아이템 1개를 삭제한다.")
    public ResponseEntity<ResponseFormat<Void>> deleteLikesItem(@PathVariable(name = "likesItemId") Long likesItemId,
                                                               @RequestParam(name = "productId") Long productId) {
        likesItemFacadeService.deleteById(likesItemId, productId);
        return ResponseEntity.ok().body(ResponseFormat.of("찜 아이템 삭제에 성공했습니다."));
    }
}
