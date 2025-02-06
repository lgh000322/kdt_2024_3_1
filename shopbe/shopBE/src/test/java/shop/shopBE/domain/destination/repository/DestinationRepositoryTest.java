package shop.shopBE.domain.destination.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.destination.entity.Destination;
import shop.shopBE.domain.destination.response.DestinationListInfo;
import shop.shopBE.domain.member.entity.Member;
import shop.shopBE.domain.member.entity.enums.Role;
import shop.shopBE.domain.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
class DestinationRepositoryTest{

    @Autowired
    DestinationRepository destinationRepository;

    @Autowired
    MemberRepository memberRepository;




    @Test
    void 배송지검색(){
        //given
        Member member = Member.createDefaultMember("홍길동", "홍길동", "asdf1234@naver.com", Role.USER, false);
        Member save = memberRepository.save(member);

        List<Destination> destinationList = new ArrayList<>();
        Destination destination = Destination.builder()
                .destinationName("로퍼1")
                .receiverName("아무나")
                .tel("010-1234-5678")
                .address("서북구 ㅇㄴㄹㄴㅇㄹㅇㄴ")
                .zipCode(112345L)
                .isSelectedDestination(false)
                .deliveryMessage("")
                .member(save)
                .build();

        Destination destination2 = Destination.builder()
                .destinationName("로퍼2")
                .receiverName("아무나2")
                .tel("010-1234-5678")
                .address("서북구2 ㅇㄴㄹㄴㅇㄹㅇㄴ")
                .zipCode(22222345L)
                .isSelectedDestination(false)
                .deliveryMessage("")
                .member(save)
                .build();

        destinationList.add(destination);
        destinationList.add(destination2);

        destinationRepository.saveAll(destinationList);


        //when
        Optional<List<DestinationListInfo>> destinationIdByMemberId = destinationRepository.findDestinationListByMemberId(save.getId());



        //then
        int lstSize = destinationIdByMemberId.get().size();
        Assertions.assertEquals(2, lstSize);
    }


}