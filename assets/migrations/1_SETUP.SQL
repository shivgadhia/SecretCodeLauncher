CREATE TABLE 'SECRET_CODES'(_id INTEGER PRIMARY KEY AUTOINCREMENT, activity_name TEXT UNIQUE, secret_code TEXT UNIQUE, date_added CURRENT_DATE, hidden TEXT DEFAULT '0')

INSERT INTO 'SECRET_CODES' ('activity_name', 'secret_code') VALUES ('shhhhhh', '1234')
INSERT INTO 'SECRET_CODES' ('activity_name', 'secret_code', 'hidden') VALUES ('hidden1', '12345', '1')
INSERT INTO 'SECRET_CODES' ('activity_name', 'secret_code', 'hidden') VALUES ('hidden2', '123456', '1')