package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.ReactionRequestDto;
import com.knitted.marketplace.dtos.ReactionResponseDto;
import com.knitted.marketplace.mappers.ReactionMapper;
import com.knitted.marketplace.models.Reaction;
import com.knitted.marketplace.services.ReactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class ReactionController {
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("reviews/{id}/reaction")
    public ResponseEntity<ReactionResponseDto> createReaction(@PathVariable("id") Long reviewId, @RequestBody ReactionRequestDto request) {
        Reaction savedReaction = reactionService.save(reviewId, request);
        ReactionResponseDto response = ReactionMapper.toResponseDto(savedReaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
