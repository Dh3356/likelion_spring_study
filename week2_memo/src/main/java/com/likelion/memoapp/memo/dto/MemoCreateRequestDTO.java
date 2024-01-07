package com.likelion.memoapp.memo.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class MemoCreateRequestDTO extends RequestDTO {
    private UUID userId;
    private String title;
    private String content;

}
