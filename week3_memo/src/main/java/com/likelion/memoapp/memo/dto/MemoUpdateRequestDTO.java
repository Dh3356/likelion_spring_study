package com.likelion.memoapp.memo.dto;

import lombok.Getter;

@Getter
public class MemoUpdateRequestDTO extends RequestDTO {
    private String title;
    private String content;

}
