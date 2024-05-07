package com.insider.login.proposal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "suggest")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Proposal {

    @Id
    @Column(name = "suggestion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int suggestionId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "member_id", nullable = false)
    private int memberId;

    @Column(name = "receiver_id") // receiverId 프로퍼티 추가
    private int receiverId;

    @Column(name = "delete_yn") // deleteYn 프로퍼티 추가
    private boolean deleteYn;

    @Column(name = "suggest_date") // suggestDate 프로퍼티 추가
    private LocalDate suggestDate;

    public Proposal(String title, String content, int memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public void setDate(LocalDate suggestDate) {
        this.suggestDate = suggestDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getSuggestDate() {
        return suggestDate;
    }

    public void setSuggestDate(LocalDate suggestDate) {
        this.suggestDate = suggestDate;
    }
}
