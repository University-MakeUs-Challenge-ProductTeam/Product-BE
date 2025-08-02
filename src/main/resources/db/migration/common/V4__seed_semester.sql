-- 1. 기수 데이터 삽입
INSERT INTO semester (name, created_at, updated_at)
VALUES
    ('1', NOW(), NOW()),
    ('2', NOW(), NOW()),
    ('3', NOW(), NOW()),
    ('4', NOW(), NOW()),
    ('5', NOW(), NOW()),
    ('6', NOW(), NOW()),
    ('7', NOW(), NOW()),
    ('8', NOW(), NOW());

-- 2. 현재 기수 지정 (가장 마지막에 생성된 기수를 기준으로)
INSERT INTO semester_current (semester_id, created_at, updated_at)
VALUES (
           (SELECT id FROM semester WHERE name = '8' ORDER BY id DESC LIMIT 1),
    NOW(),
    NOW()
    );
