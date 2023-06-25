package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.target.GetTargetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {
    private final GiftRepository giftRepository;

//    public List<Gift> getGiftListForTarget(Long targetId) {
//        List<Gift> gifts = giftRepository.findByTargetId(targetId)
//                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));
//        return gifts;
//    }
}
