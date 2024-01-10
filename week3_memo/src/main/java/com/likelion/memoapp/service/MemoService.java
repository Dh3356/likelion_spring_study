package com.likelion.memoapp.service;

import com.likelion.memoapp.model.Memo;
import com.likelion.memoapp.model.User;
import com.likelion.memoapp.model.dto.memo.MemoCreateRequestDTO;
import com.likelion.memoapp.model.dto.memo.MemoRequestDTO;
import com.likelion.memoapp.model.dto.memo.MemoUpdateRequestDTO;
import com.likelion.memoapp.repository.MemoRepository;
import com.likelion.memoapp.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository, UserRepository userRepository) {
        this.memoRepository = memoRepository;
        this.userRepository = userRepository;
    }

    private void validateUserMemo(UUID userId, UUID memoId) {
        if (!this.isValidUser(userId)) {
            throw new RuntimeException("User not found");
        }
        if (!this.isValidMemo(memoId)) {
            throw new RuntimeException("Memo not found");
        }
        Memo memo = this.memoRepository.findById(memoId).orElseThrow();
        if (!memo.getUser().getId().equals(userId)) {
            throw new RuntimeException("This memo does not belong to this user");
        }
    }

    private boolean isValidUser(UUID userId) {
        return this.userRepository.existsById(userId);
    }

    private boolean isValidMemo(UUID memoId) {
        return this.memoRepository.existsById(memoId);
    }

    public void addMemo(MemoCreateRequestDTO memoCreateRequestDTO) {
        User user = this.userRepository.findById(memoCreateRequestDTO.getUserId()).orElseThrow();
        Memo memo = new Memo(memoCreateRequestDTO.getTitle(), memoCreateRequestDTO.getContent(), user);
        this.memoRepository.save(memo);
    }

    public void deleteMemoById(UUID id, MemoRequestDTO memoRequestDTO) {
        this.validateUserMemo(memoRequestDTO.getUserId(), id);
        this.memoRepository.deleteById(id);
    }

    public void updateMemoById(UUID id, MemoUpdateRequestDTO memoUpdateRequestDTO) {
        this.validateUserMemo(memoUpdateRequestDTO.getUserId(), id);
        Memo memo = this.memoRepository.findById(id).orElseThrow();
        memo.setTitle(memoUpdateRequestDTO.getTitle());
        memo.setContent(memoUpdateRequestDTO.getContent());
        memo.setUpdatedAt(new Date());
        this.memoRepository.save(memo);
    }

    public Memo getMemoById(UUID id, MemoRequestDTO memoRequestDTO) {
        this.validateUserMemo(memoRequestDTO.getUserId(), id);
        return this.memoRepository.findById(id).orElseThrow();
    }

    public List<Memo> getAllMemos() {
        return this.memoRepository.findAll().stream().toList();
    }
}
