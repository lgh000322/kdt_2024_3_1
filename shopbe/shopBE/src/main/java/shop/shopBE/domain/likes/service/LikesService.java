package shop.shopBE.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopBE.domain.likes.entity.Likes;
import shop.shopBE.domain.likes.repository.LikesRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    public Optional<Likes> findLikesByMemberId(Long memberId) {
        return likesRepository.findLikesIdByMemberId(memberId);
    }

    @Transactional
    public Likes save(Likes likes) {
        return likesRepository.save(likes);
    }


}
