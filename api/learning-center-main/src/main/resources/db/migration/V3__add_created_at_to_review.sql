alter table review.review
    add column created_at TIMESTAMP not null default NOW();

UPDATE review.review
SET created_at = TIMESTAMP '2026-01-01 10:00:00';