package shop.shopBE.domain.cart.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.shopBE.domain.cart.response.CartInform;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom{

    private final JPAQueryFactory queryFactory;



}
