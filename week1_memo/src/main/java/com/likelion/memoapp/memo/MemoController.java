package com.likelion.memoapp.memo;

import com.likelion.memoapp.memo.dto.MemoCreateRequestDTO;
import com.likelion.memoapp.memo.dto.MemoUpdateRequestDTO;
import java.util.List;
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
    public Memo getMemoById(@PathVariable String id) {
        return this.memoService.getMemoById(id);
    }

    @PostMapping
    public void addMemo(@RequestBody MemoCreateRequestDTO memoCreateRequestDTO) {
        this.memoService.addMemo(memoCreateRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMemoById(@PathVariable("id") String id) {
        this.memoService.deleteMemoById(id);
    }

    @PatchMapping("/{id}")
    public void updateMemoById(@PathVariable("id") String id, @RequestBody MemoUpdateRequestDTO memoUpdateRequestDTO) {
        this.memoService.updateMemoById(id, memoUpdateRequestDTO);
    }
}
