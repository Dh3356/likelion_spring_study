package com.likelion.memoapp.memo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoRepository {
    private final List<Memo> memos;

    public MemoRepository() {
        this.memos = new ArrayList<>();
    }

    public void addMemo(Memo memo) {
        this.memos.add(memo);
    }

    public List<Memo> getAllMemos() {
        return this.memos;
    }

    public Memo getMemoById(String id) {
        for (Memo memo : this.memos) {
            if (memo.getId().equals(id)) {
                return memo;
            }
        }
        return null;
    }

    public void deleteMemoById(String id) {
        for (Memo memo : this.memos) {
            if (memo.getId().equals(id)) {
                this.memos.remove(memo);
                return;
            }
        }
    }

    public void updateMemoById(String id, String content) {
        for (Memo memo : this.memos) {
            if (memo.getId().equals(id)) {
                memo.setContent(content);
                return;
            }
        }
    }
}
