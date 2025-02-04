package shop.shopBE.domain.productimage.repository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepositoryCustom {

    public Optional<List<String>> findSideImgUrlsByProductId(Long productId);
}
