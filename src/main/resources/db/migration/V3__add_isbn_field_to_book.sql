-- Add ISBN column to book table
ALTER TABLE book
    ADD COLUMN isbn VARCHAR(17) NOT NULL UNIQUE;
