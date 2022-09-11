---TABELA OD REJESTRACJI---

CREATE TABLE user_data(
                          UID SERIAL PRIMARY KEY,
                          username varchar(50) UNIQUE NOT NULL,
                          password varchar(50) NOT NULL,
                            --Dodanie nowej kolumny bool
                          is_user BOOLEAN NOT NULL DEFAULT FALSE
);



---TABELA WYNIKOW---
--- Mozliwie do score dodac default value -->

CREATE TABLE scores(
                       UID INT PRIMARY KEY NOT NULL,--
                       username varchar(50) UNIQUE NOT NULL,
                       score FLOAT,
                       FOREIGN KEY (UID)
                          REFERENCES user_data(UID),
                       FOREIGN KEY (username)
                            REFERENCES user_data(username)

);


CREATE TABLE ip_table(
                         IPID INT UNIQUE DEFAULT(1),
                         ip_address CHAR(30) UNIQUE NOT NULL,
                         CONSTRAINT onerow_uni CHECK ( IPID = 1 )
);

---TWORZENIE TABELI DO TRIGGERA  UID SERIAL PRIMARY KEY,---

CREATE TABLE user_data_audit (
                             UID SERIAL PRIMARY KEY,
                             username varchar(50) UNIQUE NOT NULL ,
                             is_user BOOLEAN NOT NULL DEFAULT FALSE,
                             register_time TIMESTAMP(6) NOT NULL
);


--TRIGGER ZAWIERAJACY INFORMACJE KIEDY I KTO SIE REJESTROWAL---

CREATE OR REPLACE FUNCTION log_last_register_fnc()

	RETURNS trigger AS

$$

BEGIN


INSERT INTO "user_data_audit" (UID, "username",is_user, "register_time")

VALUES(NEW.UID, NEW."username",NEW.is_user, now());

RETURN NEW;

END

$$

LANGUAGE 'plpgsql';



CREATE TRIGGER user_data_insert_trigger

    AFTER INSERT

    ON "user_data"

    FOR EACH ROW

    EXECUTE PROCEDURE log_last_register_fnc();


--TRIGGER DO USTAWIANIA NAZWY GRACZA W SCORES i 0 PUNKTOW--


CREATE OR REPLACE FUNCTION set_username_0_scores_fnc()

    RETURNS trigger AS

$$

BEGIN


    INSERT INTO "scores" (UID, username, score)

    VALUES(NEW.UID, NEW.username, 0);

    RETURN NEW;

END

$$

    LANGUAGE 'plpgsql';

CREATE TRIGGER set_username_0_score_trigger

    AFTER INSERT

    ON "user_data"

    FOR EACH ROW

EXECUTE PROCEDURE set_username_0_scores_fnc();

---DROPOWANIE TRIGGERA---

drop trigger user_data_insert_trigger on "user_data" ;
drop trigger set_username_0_score_trigger on "user_data" ;

--PRZYDATNE ZAPYTANIA--
--Do updatowania scoresow
UPDATE scores
SET score = 0
WHERE username = ''; --Wpisujesz nazwe uzytkownika--