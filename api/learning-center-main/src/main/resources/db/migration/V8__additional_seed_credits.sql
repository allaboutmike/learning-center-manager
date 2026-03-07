-- ADDITIONAL SEED DATA FOR CREDITS TRANSACTIONS

INSERT INTO parent_account.credit_transaction (parent_id, amount, purchased_at)
SELECT p.parent_id, t.amount, t.purchased_at
FROM (
    VALUES
    ('Parent One', 20, TIMESTAMP '2026-01-05 10:00:00'),
    ('Parent Two', 15, TIMESTAMP '2026-01-05 14:00:00'),
    ('Parent Two', 25, TIMESTAMP '2026-01-06 11:00:00'),
    ('Parent One', 30, TIMESTAMP '2026-01-12 09:00:00'),
    ('Parent Two', 20, TIMESTAMP '2026-01-13 13:00:00'),
    ('Parent One', 10, TIMESTAMP '2026-01-13 16:00:00'),
    ('Parent Two', 40, TIMESTAMP '2026-01-19 10:00:00'),
    ('Parent One', 25, TIMESTAMP '2026-01-19 15:00:00'),
    ('Parent One', 15, TIMESTAMP '2026-01-20 09:00:00'),
    ('Parent Two', 30, TIMESTAMP '2026-01-26 11:00:00'),
    ('Parent One', 20, TIMESTAMP '2026-01-27 14:00:00'),
    ('Parent Two', 10, TIMESTAMP '2026-01-27 17:00:00'),
    ('Parent One', 15, TIMESTAMP '2026-02-16 08:00:00'),
    ('Parent Two', 25, TIMESTAMP '2026-02-16 13:00:00'),
    ('Parent Two', 20, TIMESTAMP '2026-02-17 10:00:00'),
    ('Parent One', 25, TIMESTAMP '2026-03-04 11:00:00'),
    ('Parent Two', 15, TIMESTAMP '2026-03-04 15:00:00'),
    ('Parent One', 10, TIMESTAMP '2026-03-05 09:00:00'),
    ('Parent Two', 30, TIMESTAMP '2026-03-06 14:00:00')
) AS t(parent_name, amount, purchased_at)
JOIN parent_account.parent p ON p.name = t.parent_name;
