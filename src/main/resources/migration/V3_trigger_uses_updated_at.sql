CREATE TRIGGER update_user_updated_at
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at();