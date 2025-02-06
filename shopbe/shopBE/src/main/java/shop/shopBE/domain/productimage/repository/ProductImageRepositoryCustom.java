package shop.shopBE.domain.productimage.repository;

import shop.shopBE.domain.productimage.response.ImgInforms;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepositoryCustom {

    public Optional<List<ImgInforms>> findSideImgUrlsByProductId(Long productId);
}
