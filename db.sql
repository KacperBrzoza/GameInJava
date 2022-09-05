---TABELA OD REJESTRACJI---

CREATE TABLE user_data(
                          UID SERIAL PRIMARY KEY,
                          username varchar(50) UNIQUE NOT NULL,
                          password varchar(50) NOT NULL
);

---TABELA WYNIKOW---
<!-- Mozliwie do score dodac default value -->

CREATE TABLE scores(
                       UID INT NOT NULL,
                       score INT,
                       FOREIGN KEY (UID)
                           REFERENCES user_data(UID)
);