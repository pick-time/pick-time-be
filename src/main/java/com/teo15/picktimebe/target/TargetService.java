package com.teo15.picktimebe.target;

import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.target.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

            Optional<Gift> gift = target.getGiftList().stream()
                    .filter(filterGift -> filterGift.getIsLike().equals(true))
                    .findFirst();
            //return new GetFinalTargetResponse(target.getConsumerName(), giftResponse);

//            Coupon coupon = target.getCouponList().stream()
//                    .filter(filterCoupon -> filterCoupon.getIsLike().equals(true))
//                    .findFirst()
//                    .orElseThrow(() -> new ResourceNotFoundException("해당 Target에 없는 couponId 입니다."));
//
//            coupon.likeToCoupon();
        return new GetFinalTargetResponse();
    }

    public GetTargetUserName getTargetUserName(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        return new GetTargetUserName(target.getProviderName(),target.getConsumerName());
    }
}
