ALTER TABLE refresh_tokens
ADD COLUMN created_by_ip_address VARCHAR(45),
ADD COLUMN user_agent VARCHAR(255);