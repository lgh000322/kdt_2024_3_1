package shop.shopBE.domain.orderproduct.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProduct is a Querydsl query type for OrderProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProduct extends EntityPathBase<OrderProduct> {

    private static final long serialVersionUID = -1837231828L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProduct orderProduct = new QOrderProduct("orderProduct");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final EnumPath<shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus> deliveryStatus = createEnum("deliveryStatus", shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.shopBE.domain.orderhistory.entity.QOrderHistory orderHistory;

    public final shop.shopBE.domain.product.entity.QProduct product;

    public final NumberPath<Integer> productCount = createNumber("productCount", Integer.class);

    public final NumberPath<Integer> productTotalPrice = createNumber("productTotalPrice", Integer.class);

    public QOrderProduct(String variable) {
        this(OrderProduct.class, forVariable(variable), INITS);
    }

    public QOrderProduct(Path<? extends OrderProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProduct(PathMetadata metadata, PathInits inits) {
        this(OrderProduct.class, metadata, inits);
    }

    public QOrderProduct(Class<? extends OrderProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderHistory = inits.isInitialized("orderHistory") ? new shop.shopBE.domain.orderhistory.entity.QOrderHistory(forProperty("orderHistory"), inits.get("orderHistory")) : null;
        this.product = inits.isInitialized("product") ? new shop.shopBE.domain.product.entity.QProduct(forProperty("product")) : null;
    }

}

