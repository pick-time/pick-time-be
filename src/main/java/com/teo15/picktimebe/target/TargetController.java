package com.teo15.picktimebe.target;


import com.teo15.picktimebe.target.dto.GetFinalTargetResponse;
import com.teo15.picktimebe.target.dto.GetTargetResponse;
import com.teo15.picktimebe.target.dto.PostLikeGiftForTargetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/target")
@RequiredArgsConstructor
public class TargetController {
    private final TargetService targetService;

    @GetMapping("/{targetId}")
    public ResponseEntity<GetTargetResponse> getTarget(@PathVariable Long targetId){
        return ResponseEntity.ok(targetService.selectTarget(targetId));
    }

    @PostMapping("/target/{targetId}")
    public ResponseEntity<Long> likeGiftForTarget(@PathVariable("targetId") Long targetId,
                                                  @RequestBody PostLikeGiftForTargetRequest request) {
        request.setTargetId(targetId);

        return ResponseEntity.ok(targetService.likeGiftForTarget(request));
    }

    @GetMapping("/{targetId}/final")
    public ResponseEntity<GetFinalTargetResponse> getFinalGiftForTarget(@PathVariable("targetId") Long targetId) {
        return ResponseEntity.ok(targetService.getFinalGiftForTarget(targetId));
    }
}