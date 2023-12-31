package com.likelion.memoapp.memo;

import com.likelion.memoapp.memo.dto.MemoCreateRequestDTO;
import com.likelion.memoapp.memo.dto.MemoUpdateRequestDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {
    private final MemoRepository memoRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public void addMemo(MemoCreateRequestDTO memo) {
        Memo newMemo = new Memo(UUID.randomUUID().toString(), memo.getContent());
        this.memoRepository.addMemo(newMemo);
    }

    public List<Memo> getAllMemos() {
        return this.memoRepository.getAllMemos();
    }

    public void deleteMemoById(String id) {
        this.memoRepository.deleteMemoById(id);
    }

    public void updateMemoById(String id, MemoUpdateRequestDTO memoUpdateRequestDTO) {
        this.memoRepository.updateMemoById(id, memoUpdateRequestDTO.getContent());
    }

    public Memo getMemoById(String id) {
        return this.memoRepository.getMemoById(id);
    }
}
