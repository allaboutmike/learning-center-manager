-- Add attended boolean to Session table

ALTER TABLE session.session
    ADD COLUMN attended BOOLEAN NOT NULL DEFAULT FALSE;