package com.likelion.memoapp.memo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.memoapp.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
//optional = false: user가 없는 memo는 생성할 수 없다.
    @JsonIgnore//json으로 변환할 때 user를 무시한다.
    private User user;

    @Setter
    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Setter
    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @Setter
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    public Memo(String title, String content, User user) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
