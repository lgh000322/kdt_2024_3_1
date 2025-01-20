package shop.shopBE.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -685902594L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final EnumPath<shop.shopBE.domain.member.entity.enums.Gender> gender = createEnum("gender", shop.shopBE.domain.member.entity.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<shop.shopBE.domain.member.entity.enums.Role> role = createEnum("role", shop.shopBE.domain.member.entity.enums.Role.class);

    public final ComparablePath<java.util.UUID> sub = createComparable("sub", java.util.UUID.class);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

