package com.teo15.picktimebe.gift;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {
    private final GiftRepository giftRepository;

//    public List<Gift> getGiftListForTarget(Long targetId) {
//        List<Gift> gifts = giftRepository.findByTargetId(targetId)
//                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the card."));
//        return gifts;
//    }
}
