package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.dto.GiftData;
import com.teo15.picktimebe.gift.dto.GiftResponse;
import com.teo15.picktimebe.gift.dto.PostGiftRequest;
import com.teo15.picktimebe.gift.dto.UpdateGiftInfoRequest;
import com.teo15.picktimebe.lib.OpenGraph;
import com.teo15.picktimebe.target.Target;
import com.teo15.picktimebe.target.dto.GetTargetUserName;
import com.teo15.picktimebe.target.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {
    private final GiftRepository giftRepository;
    private final TargetService targetService;
    private final S3Uploader s3Uploader;

    public GiftResponse selectByTargetIdGiftList(Long targetId) {
        return targetService.selectByTargetIdGiftList(targetId, GiftType.TOTAL);
    }

    @Transactional
    public GiftResponse updateAndgetList(Long giftId, UpdateGiftInfoRequest request, MultipartFile file) throws FileSystemException {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Gift."));

        String fileName = "";
        if (file != null) {
            try {
                fileName = s3Uploader.upload(file, "images");
                gift.setGiftImageUrl(fileName);
            } catch (IOException e) {
                throw new FileSystemException("Failed to save the card image file.");
            }
        }

        Gift updatedGift = request.toEntity(gift);
        giftRepository.save(updatedGift);

        return targetService.selectByTargetIdGiftList(updatedGift.getTarget().getId(), gift.getGiftType());
    }

    public GiftResponse createAndgetList(Long targetId, PostGiftRequest request) {
        GiftData og = new GiftData();
        og.setGiftUrl(request.getGiftUrl());

        try {
            OpenGraph page = new OpenGraph(request.getGiftUrl(), true);
            og.setGiftTitle(getContent(page, "title"));
            og.setGiftDescription(getContent(page, "description"));
            og.setGiftImage(getContent(page, "image"));

        } catch (Exception e) {
            // og 태그에서 가져오지 못하면 기본 이미지로 추가해준다.
            og.setGiftTitle("선물");
            og.setGiftImage("https://picktime-image.s3.ap-northeast-2.amazonaws.com/images/KakaoTalk_Photo_2023-01-27-00-54-47%20%281%29.png");
        }

        Target target = targetService.getTargetEntity(targetId);

        Gift updatedGift = og.toEntity(target); // nullable check
        giftRepository.save(updatedGift);

        return targetService.selectByTargetIdGiftList(targetId, GiftType.PRODUCT);
    }

    private String getContent(OpenGraph page, String propertyName) {
        try {
            return page.getContent(propertyName);
        } catch (NullPointerException e) {
            return "태그 없음";
        }
    }

    @Transactional
    public void deleteGift(Long targetId) {
        giftRepository.deleteById(targetId);
    }
}
