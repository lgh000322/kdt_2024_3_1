package shop.shopBE.domain.productimage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.productimage.entity.enums.ProductImageCategory;

import java.util.List;
import java.util.Optional;

import static shop.shopBE.domain.productimage.entity.QProductImage.productImage;

@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<String>> findSideImgUrlsByProductId(Long productId) {

        List<String> sideImgUrls = queryFactory
                .select(productImage.savedName)
                .from(productImage)
                .where(productImage.productImageCategory.eq(ProductImageCategory.SIDE),
                        productImage.product.id.eq(productId))
                .fetch();

        return Optional.of(sideImgUrls);
    }
}
