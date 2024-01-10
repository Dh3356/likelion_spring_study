package com.likelion.memoapp.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.memoapp.memo.Memo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    private UUID id;

    @Setter
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore//json으로 변환할 때 memos를 무시한다.
    private List<Memo> memos;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @Setter
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    public User(String name) {
        this.name = name;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
