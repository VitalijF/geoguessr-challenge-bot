--
-- Set PostgreSQL database default time zone to UTC.
-- It will only be used if, for some reason, we pass a datetime without timezone as a value for a column with type
-- "timestamp with timezone".
--
SET TIME ZONE 'UTC';