package com.likelion.memoapp.repository;

import com.likelion.memoapp.model.Memo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, UUID> {
    Memo findByTitle(String title);
}