CREATE TABLE 'SECRET_CODES'(_id INTEGER PRIMARY KEY AUTOINCREMENT, activity_name TEXT UNIQUE, secret_code TEXT UNIQUE, date_added CURRENT_DATE);

INSERT INTO 'SECRET_CODES' ('activity_name', 'secret_code') VALUES ('test', '4636')