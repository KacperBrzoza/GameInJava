---TABELA OD REJESTRACJI---

CREATE TABLE user_data(
                          UID SERIAL PRIMARY KEY,
                          username varchar(50) UNIQUE NOT NULL,
                          password varchar(50) NOT NULL
);

---TABELA WYNIKOW---
--- Mozliwie do score dodac default value -->

CREATE TABLE scores(
                       UID INT NOT NULL,
                       score INT,
                       FOREIGN KEY (UID)
                           REFERENCES user_data(UID)
);


---TWORZENIE TABELI DO TRIGGERA  UID SERIAL PRIMARY KEY,---

CREATE TABLE user_data_audit (
                             UID SERIAL PRIMARY KEY,
                             username varchar(50) UNIQUE NOT NULL ,
                             register_time TIMESTAMP(6) NOT NULL
);


--TRIGGER ZAWIERAJACY INFORMACJE KIEDY I KTO SIE REJESTROWAL---

CREATE OR REPLACE FUNCTION log_last_register_fnc()

	RETURNS trigger AS

$$

BEGIN


INSERT INTO "user_data_audit" (UID, "username", "register_time")

VALUES(NEW.UID, NEW."username", now());

RETURN NEW;

END

$$

LANGUAGE 'plpgsql';



CREATE TRIGGER user_data_insert_trigger

    AFTER INSERT

    ON "user_data"

    FOR EACH ROW

    EXECUTE PROCEDURE log_last_register_fnc();


---DROPOWANIE TRIGGERA---

drop trigger user_data_insert_trigger on "user_data" ;