package shop.shopBE.domain.destination.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDestination is a Querydsl query type for Destination
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDestination extends EntityPathBase<Destination> {

    private static final long serialVersionUID = -398565840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDestination destination = new QDestination("destination");

    public final StringPath address = createString("address");

    public final StringPath deliveryMessage = createString("deliveryMessage");

    public final StringPath destinationName = createString("destinationName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.shopBE.domain.member.entity.QMember member;

    public final StringPath receiverName = createString("receiverName");

    public final StringPath tel = createString("tel");

    public final NumberPath<Long> zipCode = createNumber("zipCode", Long.class);

    public QDestination(String variable) {
        this(Destination.class, forVariable(variable), INITS);
    }

    public QDestination(Path<? extends Destination> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDestination(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDestination(PathMetadata metadata, PathInits inits) {
        this(Destination.class, metadata, inits);
    }

    public QDestination(Class<? extends Destination> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new shop.shopBE.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

