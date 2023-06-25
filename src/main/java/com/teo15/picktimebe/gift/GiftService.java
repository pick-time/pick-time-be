package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.target.dto.GetTargetUserName;
import com.teo15.picktimebe.target.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {
    private final GiftRepository giftRepository;
    private final TargetService targetService;

    public GiftResponse createGiftResponse(Long targetId) {
        List<GiftData> giftList = getGiftListForTarget(targetId);
        GetTargetUserName targetUserName = targetService.getTargetUserName(targetId);

        GiftResponse giftResponse = new GiftResponse();
        giftResponse.setGiftTotal(giftList.size());
        giftResponse.setProviderName(targetUserName.getProviderName());
        giftResponse.setConsumerName(targetUserName.getConsumerName());
        giftResponse.setGiftList(giftList);

        return giftResponse;
    }

    public List<GiftData> getGiftListForTarget(Long targetId) {
        List<Gift> gifts = giftRepository.findByTargetId(targetId);

        if (gifts.isEmpty()) {
            throw new ResourceNotFoundException("카드를 찾을 수 없습니다.");
        }

        return gifts.stream()
                .map(this::convertToGiftData)
                .collect(Collectors.toList());
    }

    private GiftData convertToGiftData(Gift gift) {
        GiftData giftData = new GiftData();
        giftData.setGiftId(gift.getId());
        giftData.setGiftUrl(gift.getGiftUrl());
        giftData.setGiftImage(gift.getGiftImageUrl());
        giftData.setGiftTitle(gift.getGiftTitle());
        giftData.setGiftDescription(gift.getGiftDescription());
        return giftData;
    }
}
