INSERT INTO `user` (`user_id`, `password`)
VALUES ('test', 'user'),
       ('user123', 'password123');

INSERT INTO `calendar` (`cid`, `user_id`, `name`, `start`, `end`, `need_noti`, `depth`, `tag`)
VALUES (UUID_TO_BIN(UUID(), 1), 'test', '독서', '2024-12-01 10:00:00', '2024-12-01 12:00:00', TRUE, 1, 1),
       (UUID_TO_BIN(UUID(), 1), 'test', '회의', '2024-12-01 14:00:00', '2024-12-01 15:00:00', FALSE, 1, 2),
       (UUID_TO_BIN(UUID(), 1), 'test', '운동', '2024-12-01 17:00:00', '2024-12-01 18:00:00', FALSE, 1, 4),
       (UUID_TO_BIN(UUID(), 1), 'test', '프로젝트 발표', '2024-12-03 09:00:00', '2024-12-03 10:30:00', TRUE, 1, 3),
       (UUID_TO_BIN(UUID(), 1), 'test', '운동', '2024-12-04 06:00:00', '2024-12-04 07:00:00', FALSE, 1, 4),
       (UUID_TO_BIN(UUID(), 1), 'test', '결혼기념일', '2024-12-05 12:00:00', '2024-12-05 15:00:00', TRUE, 1, 0),
       (UUID_TO_BIN(UUID(), 1), 'test', '업무보고', '2024-12-06 09:00:00', '2024-12-06 10:00:00', FALSE, 1, 2),
       (UUID_TO_BIN(UUID(), 1), 'test', '클라이언트 미팅', '2024-12-07 13:00:00', '2024-12-07 14:30:00', TRUE, 1, 3),
       (UUID_TO_BIN(UUID(), 1), 'test', '영화 관람', '2024-12-08 19:00:00', '2024-12-08 21:00:00', FALSE, 1, 1),
       (UUID_TO_BIN(UUID(), 1), 'test', '일기쓰기', '2024-12-09 22:00:00', '2024-12-09 22:30:00', TRUE, 1, 4),
       (UUID_TO_BIN(UUID(), 1), 'test', '생일파티', '2024-12-10 18:00:00', '2024-12-10 21:00:00', TRUE, 1, 0),
       (UUID_TO_BIN(UUID(), 1), 'test', '마감일', '2024-12-11 09:00:00', '2024-12-11 11:00:00', FALSE, 1, 2),
       (UUID_TO_BIN(UUID(), 1), 'test', '새로운 프로젝트 미팅', '2024-12-12 14:00:00', '2024-12-12 15:00:00', TRUE, 1, 3),
       (UUID_TO_BIN(UUID(), 1), 'test', '사진 촬영', '2024-12-13 08:00:00', '2024-12-13 12:00:00', TRUE, 1, 1),
       (UUID_TO_BIN(UUID(), 1), 'test', '연차', '2024-12-14 00:00:00', '2024-12-14 23:59:59', TRUE, 1, 2),
       (UUID_TO_BIN(UUID(), 1), 'test', '연말 파티', '2024-12-15 17:00:00', '2024-12-15 22:00:00', TRUE, 1, 0),

       (UUID_TO_BIN(UUID(), 1), 'test', '새해 계획 회의', '2025-01-02 10:00:00', '2025-01-02 11:30:00', TRUE, 1, 3),
       (UUID_TO_BIN(UUID(), 1), 'test', '새해 목표 설정', '2025-01-03 08:00:00', '2025-01-03 09:00:00', FALSE, 1, 4),
       (UUID_TO_BIN(UUID(), 1), 'test', '새로운 책 읽기', '2025-01-04 14:00:00', '2025-01-04 16:00:00', TRUE, 1, 1),
       (UUID_TO_BIN(UUID(), 1), 'test', '워크숍', '2025-01-05 09:00:00', '2025-01-05 12:00:00', TRUE, 1, 2),

-- Adding overlapping or multi-day events
       (UUID_TO_BIN(UUID(), 1), 'test', '1일 연수', '2024-12-02 09:00:00', '2024-12-03 18:00:00', TRUE, 2, 3),
       (UUID_TO_BIN(UUID(), 1), 'test', '휴식', '2024-12-01 00:00:00', '2024-12-01 23:59:59', TRUE, 2, 1),
       (UUID_TO_BIN(UUID(), 1), 'test', '마감일 점검', '2024-12-03 08:00:00', '2024-12-03 10:00:00', FALSE, 2, 2),
       (UUID_TO_BIN(UUID(), 1), 'test', '팀 미팅', '2024-12-04 08:00:00', '2024-12-04 09:00:00', TRUE, 2, 3),
       (UUID_TO_BIN(UUID(), 1), 'test', '운동', '2024-12-07 06:00:00', '2024-12-07 07:00:00', FALSE, 1, 4),
       (UUID_TO_BIN(UUID(), 1), 'test', '가족 모임', '2024-12-08 10:00:00', '2024-12-08 12:00:00', TRUE, 1, 0),
       (UUID_TO_BIN(UUID(), 1), 'test', '기념일 기획', '2024-12-07 12:00:00', '2024-12-07 14:00:00', TRUE, 2, 0);

-- Insert todo entries for 'test' user
INSERT INTO `todo` (`tid`, `user_id`, `name`, `priority`, `complete`, `delay`, `category`)
VALUES (UUID_TO_BIN(UUID(), 1), 'test', '보고서 작성', 1, NULL, FALSE, '처리'),
       (UUID_TO_BIN(UUID(), 1), 'test', '운동하기', 10, '2024-12-01 08:00:00', FALSE, '루틴'),
       (UUID_TO_BIN(UUID(), 1), 'test', '친구와 만남', 20, '2024-12-10 18:00:00', FALSE, '오늘 선호'),
       (UUID_TO_BIN(UUID(), 1), 'test', '책 읽기', 30, NULL, FALSE, '자기계발'),
       (UUID_TO_BIN(UUID(), 1), 'test', '여행 계획', 1, NULL, FALSE, '처리'),
       (UUID_TO_BIN(UUID(), 1), 'test', '장보기', 10, NULL, FALSE, '오늘 선호'),
       (UUID_TO_BIN(UUID(), 1), 'test', '회의 준비', 20, NULL, FALSE, '처리'),
       (UUID_TO_BIN(UUID(), 1), 'test', '운동 루틴', 30, NULL, FALSE, '루틴'),
       (UUID_TO_BIN(UUID(), 1), 'test', '메일 답변', 1, NULL, FALSE, '처리'),
       (UUID_TO_BIN(UUID(), 1), 'test', '주간 계획 세우기', 10, NULL, FALSE, '자기계발'),

       (UUID_TO_BIN(UUID(), 1), 'test', '과제 제출', NULL, NULL, TRUE, '처리'),
       (UUID_TO_BIN(UUID(), 1), 'test', '일기 쓰기', NULL, NULL, TRUE, '루틴'),
       (UUID_TO_BIN(UUID(), 1), 'test', '영화 보기', NULL, NULL, TRUE, '자기계발'),
       (UUID_TO_BIN(UUID(), 1), 'test', '쇼핑', NULL, NULL, TRUE, '오늘 선호'),
       (UUID_TO_BIN(UUID(), 1), 'test', '운동하기', NULL, NULL, TRUE, '루틴');
