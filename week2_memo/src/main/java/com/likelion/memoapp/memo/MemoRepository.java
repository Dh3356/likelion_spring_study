package com.likelion.memoapp.memo;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, UUID> {
    Memo findByTitle(String title);
}