package com.project.barter.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BoardPreview {
    private Long id;
    private String title;
    private LocalDateTime createDate;
    private String loginId;
}
