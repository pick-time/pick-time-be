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

    public GiftResponse updateAndgetList(Long giftId, UpdateGiftInfoRequest request, MultipartFile file) throws FileSystemException {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Gift."));

        String fileName = "";
        if (file != null) {
            try {
                fileName = s3Uploader.upload(file, "images");
            } catch (IOException e) {
                throw new FileSystemException("Failed to save the card image file.");
            }
        }

        gift.setGiftImageUrl(fileName);
        Gift updatedGift = request.toEntity(gift);
        giftRepository.save(updatedGift);

        return targetService.selectByTargetIdGiftList(updatedGift.getTarget().getId(), GiftType.COUPON);
    }

    public GiftResponse createAndgetList(Long targetId, PostGiftRequest request) {
        GiftData og = null;
        try {
            OpenGraph page = new OpenGraph(request.getGiftUrl(), true);
            og = new GiftData();
            og.setGiftUrl(request.getGiftUrl());
            og.setGiftTitle(getContent(page, "title"));
            og.setGiftDescription(getContent(page, "description"));
            og.setGiftImage(getContent(page, "image"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Target target = targetService.getTargetEntity(targetId);

        Gift updatedGift = og.toEntity();
        updatedGift.setTarget(target);
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
}
