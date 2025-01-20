package shop.shopBE.domain.orderhistory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderHistory is a Querydsl query type for OrderHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderHistory extends EntityPathBase<OrderHistory> {

    private static final long serialVersionUID = 898038774L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderHistory orderHistory = new QOrderHistory("orderHistory");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final shop.shopBE.domain.destination.entity.QDestination destination;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.shopBE.domain.member.entity.QMember member;

    public final NumberPath<Integer> orderPrice = createNumber("orderPrice", Integer.class);

    public QOrderHistory(String variable) {
        this(OrderHistory.class, forVariable(variable), INITS);
    }

    public QOrderHistory(Path<? extends OrderHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderHistory(PathMetadata metadata, PathInits inits) {
        this(OrderHistory.class, metadata, inits);
    }

    public QOrderHistory(Class<? extends OrderHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.destination = inits.isInitialized("destination") ? new shop.shopBE.domain.destination.entity.QDestination(forProperty("destination"), inits.get("destination")) : null;
        this.member = inits.isInitialized("member") ? new shop.shopBE.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

