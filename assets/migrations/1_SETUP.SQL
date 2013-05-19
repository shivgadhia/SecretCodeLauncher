CREATE TABLE 'SECRET_CODES'(_id INTEGER PRIMARY KEY AUTOINCREMENT, activity_name TEXT UNIQUE, secret_code TEXT UNIQUE, date_added CURRENT_DATE, hidden INTEGER DEFAULT 0 );

INSERT INTO 'SECRET_CODES' ('activity_name', 'secret_code') VALUES ('shhhhhh', '1234')