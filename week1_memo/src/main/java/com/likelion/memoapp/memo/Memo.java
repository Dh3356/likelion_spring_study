package com.likelion.memoapp.memo;

import java.util.UUID;

public class Memo {
    private final String id;
    private String content;

    public Memo(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return this.id;
    }
}
