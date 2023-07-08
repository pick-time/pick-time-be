package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.gift.dto.GiftResponse;
import com.teo15.picktimebe.gift.dto.PostGiftRequest;
import com.teo15.picktimebe.gift.dto.UpdateGiftInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystemException;
import java.util.List;

@RestController
@RequestMapping("/gift")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    /**
     * Target 선물 조회 API
     * 주는 사람이 생성한 선물 목록을 확인하는 페이지에 진입할때 요청
     */
    @GetMapping("/{targetId}")
    public ResponseEntity<GiftResponse> getGiftListByTargetId(@PathVariable("targetId") Long targetId) {
        return ResponseEntity.ok(giftService.selectByTargetIdGiftList(targetId));
    }

    /**
     * TODO 선물생성 - 상품
     */
    @PostMapping("/{targetId}")
    public ResponseEntity<GiftResponse> createGift(@PathVariable("targetId") Long targetId, @RequestBody PostGiftRequest request) {
        return ResponseEntity.ok(giftService.createAndgetList(targetId, request));
    }

    /**
     * 선물 수정 API
     * req : productTitle ,productName ,productMessage
     * res : List<Gift>
     */
    @PutMapping("/{giftId}")
    public ResponseEntity<GiftResponse> updateGiftInfo(@PathVariable("giftId") Long giftId, @ModelAttribute UpdateGiftInfoRequest request, @RequestParam(value ="file", required=false) MultipartFile file) throws FileSystemException {
        return ResponseEntity.ok(giftService.updateAndgetList(giftId, request, file));
    }
}
