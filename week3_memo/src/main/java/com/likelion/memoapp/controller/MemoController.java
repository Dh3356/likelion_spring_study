package com.likelion.memoapp.controller;

import com.likelion.memoapp.dto.memo.MemoCreateRequestDto;
import com.likelion.memoapp.dto.memo.MemoUpdateRequestDto;
import com.likelion.memoapp.dto.response.ResponseDto;
import com.likelion.memoapp.model.Memo;
import com.likelion.memoapp.service.MemoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memo")
public class MemoController {
    private final MemoService memoService;

    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<Memo>>> getAllMemos() {
        return this.memoService.getAllMemos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Memo>> getMemoById(@PathVariable UUID id) {
        return this.memoService.getMemoById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> addMemo(
            @RequestBody @Valid MemoCreateRequestDto memoCreateRequestDTO)
            throws Exception {
        return this.memoService.addMemo(memoCreateRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoById(@PathVariable("id") UUID id) {
        return this.memoService.deleteMemoById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMemoById(@PathVariable("id") String id,
                                                            @RequestBody @Valid MemoUpdateRequestDto memoUpdateRequestDTO) {
        return this.memoService.updateMemoById(UUID.fromString(id), memoUpdateRequestDTO);
    }

    @PostMapping("/test")
    public ResponseEntity<ResponseDto<Void>> test() {
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Success"), HttpStatus.CREATED);
    }
}
