package com.dnproject.platform.domain;

/*
* CREATE TABLE boards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- 작성자 ID
    shelter_id BIGINT NULL, -- 관련 보호소 ID (있을 경우만)
    type VARCHAR(20) NOT NULL, -- 게시글 타입 (FREE, NOTICE, STORY 등) (자유게시판, 공지사항 및 FAQ, 입양후기 게시판) 필터링 용도.
    title VARCHAR(200) NOT NULL, -- 게시글 제목
    content TEXT NOT NULL, -- 게시글 본문
    views INT DEFAULT 0, -- 조회수
    is_pinned BOOLEAN DEFAULT FALSE, -- 상단 고정 여부 (공지사항 용)
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (shelter_id) REFERENCES shelters(id) ON DELETE SET NULL, -- 보호소 삭제 시 연결만 해제
    INDEX idx_boards_type (type),
    INDEX idx_boards_created (created_at),
    INDEX idx_boards_shelter (shelter_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
* */

import com.dnproject.platform.domain.constant.BoardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "boards", indexes = {
        @Index(name = "idx_board_type", columnList = "type"),
        @Index(name = "idx_boards_created", columnList = "created_at"),
        @Index(name = "idx_boards_shelter", columnList = "shelter_id")
})
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BoardType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @Column(nullable = false)
    @ColumnDefault("0")
    private int views = 0;

    // 공지사항 상단 고정 여부
    @Builder.Default
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isPinned = false;

    // logic
    public void incrementViews(){
        this.views++;
    }

    public void update(String title, String content, boolean isPinned){
        this.title = title;
        this.content = content;
        this.isPinned = isPinned;
    }
}
