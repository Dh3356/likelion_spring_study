package com.likelion.memoapp.service;

import com.likelion.memoapp.dto.memo.MemoCreateRequestDto;
import com.likelion.memoapp.dto.memo.MemoUpdateRequestDto;
import com.likelion.memoapp.dto.response.ResponseDto;
import com.likelion.memoapp.model.Memo;
import com.likelion.memoapp.model.User;
import com.likelion.memoapp.repository.MemoRepository;
import com.likelion.memoapp.repository.UserRepository;
import com.likelion.memoapp.util.SecurityUtil;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private void validateUserMemo(String userId, UUID memoId) {
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

    private boolean isValidUser(String userId) {
        return this.userRepository.existsById(userId);
    }

    private boolean isValidMemo(UUID memoId) {
        return this.memoRepository.existsById(memoId);
    }

    public ResponseEntity<ResponseDto<Void>> addMemo(MemoCreateRequestDto memoCreateRequestDTO) {
        User user = this.userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow();
        Memo memo = new Memo(memoCreateRequestDTO.getTitle(), memoCreateRequestDTO.getContent(), user);
        this.memoRepository.save(memo);
        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.CREATED,
                "Success"
        ), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseDto<Void>> deleteMemoById(UUID memoId) {
        this.validateUserMemo(SecurityUtil.getCurrentUserId(), memoId);
        this.memoRepository.deleteById(memoId);
        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "Success"
        ), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto<Void>> updateMemoById(UUID id, MemoUpdateRequestDto memoUpdateRequestDTO) {
        this.validateUserMemo(SecurityUtil.getCurrentUserId(), id);
        Memo memo = this.memoRepository.findById(id).orElseThrow();
        memo.setTitle(memoUpdateRequestDTO.getTitle());
        memo.setContent(memoUpdateRequestDTO.getContent());
        memo.setUpdatedAt(new Date());
        this.memoRepository.save(memo);
        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "Success"
        ), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto<Memo>> getMemoById(UUID id) {
        this.validateUserMemo(SecurityUtil.getCurrentUserId(), id);
        Memo memo = this.memoRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "Success",
                memo
        ), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto<List<Memo>>> getAllMemos() {
        List<Memo> memos = this.memoRepository.findAll();
        return new ResponseEntity<>(ResponseDto.res(
                HttpStatus.OK,
                "Success",
                memos
        ), HttpStatus.OK);
    }
}
