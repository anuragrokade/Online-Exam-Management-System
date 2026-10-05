package com.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.dto.LeaderboardResponse;
import com.examportal.service.LeaderboardService;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "*")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<LeaderboardResponse>>
    getLeaderboard(
            @PathVariable Long examId) {

        return ResponseEntity.ok(
                leaderboardService.getLeaderboard(examId));
    }
}
