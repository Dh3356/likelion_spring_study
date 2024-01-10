package com.likelion.memoapp.model.dto.memo;

import lombok.Getter;

@Getter
public class MemoUpdateRequestDTO extends MemoRequestDTO {
    private String title;
    private String content;

}
