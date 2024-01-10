package com.likelion.memoapp.model.dto.memo;

import java.util.UUID;
import lombok.Getter;

@Getter
public class MemoCreateRequestDTO extends MemoRequestDTO {
    private UUID userId;
    private String title;
    private String content;

}
