package com.teo15.picktimebe.target;

import com.teo15.picktimebe.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TargetService {
    private final TargetRepository targetRepository;

    public GetTargetResponse selectTarget(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        return new GetTargetResponse(target.getProviderName(), target.getCardImageUrl(), target.getMessage());
    }

    @Transactional
    public Long likeGiftForTarget(Long targetId, Long giftId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        target.giftLikeChange(giftId);
        return target.getId();
    }

    public GetFinalTargetResponse getFinalGiftForTarget(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        //target.getLikedGift();
        return new GetFinalTargetResponse();
    }
}
