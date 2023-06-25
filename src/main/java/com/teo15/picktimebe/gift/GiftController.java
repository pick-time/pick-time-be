package com.teo15.picktimebe.gift;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gift")
@RequiredArgsConstructor
public class GiftController {

    private GiftService giftService;

    @GetMapping("/{targetId}")
    public ResponseEntity<GiftResponse> getGiftList(@PathVariable("targetId") Long targetId) {

        GiftResponse giftResponse = giftService.createGiftResponse(targetId);

        return ResponseEntity.ok(giftResponse);
    }
}
