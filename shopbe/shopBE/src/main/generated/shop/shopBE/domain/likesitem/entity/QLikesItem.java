package shop.shopBE.domain.likesitem.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikesItem is a Querydsl query type for LikesItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikesItem extends EntityPathBase<LikesItem> {

    private static final long serialVersionUID = 1549816880L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikesItem likesItem = new QLikesItem("likesItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.shopBE.domain.likes.entity.QLikes likes;

    public final shop.shopBE.domain.product.entity.QProduct product;

    public QLikesItem(String variable) {
        this(LikesItem.class, forVariable(variable), INITS);
    }

    public QLikesItem(Path<? extends LikesItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikesItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikesItem(PathMetadata metadata, PathInits inits) {
        this(LikesItem.class, metadata, inits);
    }

    public QLikesItem(Class<? extends LikesItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.likes = inits.isInitialized("likes") ? new shop.shopBE.domain.likes.entity.QLikes(forProperty("likes"), inits.get("likes")) : null;
        this.product = inits.isInitialized("product") ? new shop.shopBE.domain.product.entity.QProduct(forProperty("product")) : null;
    }

}

