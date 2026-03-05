ALTER TABLE parent_account.parent ADD COLUMN email VARCHAR(255) NOT NULL DEFAULT 'placeholder@example.com';
ALTER TABLE parent_account.parent ADD COLUMN phone VARCHAR(20);

UPDATE parent_account.parent SET email = 'parent.one@example.com' WHERE name = 'Parent One';
UPDATE parent_account.parent SET email = 'parent.two@example.com' WHERE name = 'Parent Two';

ALTER TABLE parent_account.parent ADD CONSTRAINT parent_email_unique UNIQUE (email);
