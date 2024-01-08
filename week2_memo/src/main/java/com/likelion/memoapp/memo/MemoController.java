package com.likelion.memoapp.memo;

import com.likelion.memoapp.memo.dto.MemoCreateRequestDTO;
import com.likelion.memoapp.memo.dto.MemoUpdateRequestDTO;
import com.likelion.memoapp.memo.dto.RequestDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Memo> getAllMemos() {
        return this.memoService.getAllMemos();
    }

    @GetMapping("/{id}")
    public Memo getMemoById(@PathVariable String id, @RequestBody RequestDTO requestDTO) {
        return this.memoService.getMemoById(UUID.fromString(id), requestDTO);
    }

    @PostMapping
    public void addMemo(@RequestBody MemoCreateRequestDTO memoCreateRequestDTO) throws Exception {
        this.memoService.addMemo(memoCreateRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMemoById(@PathVariable("id") String id, @RequestBody RequestDTO requestDTO) {
        this.memoService.deleteMemoById(UUID.fromString(id), requestDTO);
    }

    @PatchMapping("/{id}")
    public void updateMemoById(@PathVariable("id") String id, @RequestBody MemoUpdateRequestDTO memoUpdateRequestDTO) {
        this.memoService.updateMemoById(UUID.fromString(id), memoUpdateRequestDTO);
    }
}
