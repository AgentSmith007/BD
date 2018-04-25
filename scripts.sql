CREATE TABLE employee (
    id      NUMBER NOT NULL,
    fio     VARCHAR2(80),
    phone   VARCHAR2(80),
    email   VARCHAR2(80),
    age     NUMBER
);

ALTER TABLE employee ADD CONSTRAINT employee_pk PRIMARY KEY ( id );

CREATE TABLE enterprise (
    id      NUMBER NOT NULL,
    name    VARCHAR2(80),
    phone   VARCHAR2(80)
);

ALTER TABLE enterprise ADD CONSTRAINT enterprise_pk PRIMARY KEY ( id );

CREATE TABLE resume (
    id              NUMBER NOT NULL,
    employee_id     NUMBER NOT NULL,
    speciality_id   NUMBER NOT NULL,
    experience      NUMBER,
    data            DATE
);

ALTER TABLE resume ADD CONSTRAINT resume_pk PRIMARY KEY ( id );

CREATE TABLE speciality (
    id     NUMBER NOT NULL,
    name   VARCHAR2(80)
);

ALTER TABLE speciality ADD CONSTRAINT speciality_pk PRIMARY KEY ( id );

CREATE TABLE vacancy (
    id              NUMBER NOT NULL,
    enterprise_id   NUMBER NOT NULL,
    speciality_id   NUMBER NOT NULL,
    experience      NUMBER,
    salary          NUMBER
);

ALTER TABLE vacancy ADD CONSTRAINT vacancy_pk PRIMARY KEY ( id );

ALTER TABLE resume
    ADD CONSTRAINT resume_employee_fk FOREIGN KEY ( employee_id )
        REFERENCES employee ( id );

ALTER TABLE resume
    ADD CONSTRAINT resume_speciality_fk FOREIGN KEY ( speciality_id )
        REFERENCES speciality ( id );

ALTER TABLE vacancy
    ADD CONSTRAINT vacancy_enterprise_fk FOREIGN KEY ( enterprise_id )
        REFERENCES enterprise ( id );

ALTER TABLE vacancy
    ADD CONSTRAINT vacancy_speciality_fk FOREIGN KEY ( speciality_id )
        REFERENCES speciality ( id );

CREATE SEQUENCE employee_id_seq MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER NOCYCLE;

CREATE SEQUENCE enterprise_id_seq MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER NOCYCLE;

CREATE SEQUENCE resume_id_seq MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER NOCYCLE;

CREATE SEQUENCE vacancy_id_seq MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER NOCYCLE;

CREATE SEQUENCE speciality_id_seq MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 1 NOCACHE NOORDER NOCYCLE;

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Mercury',
    '8462756328'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Altarix',
    '8462116328'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'IBM',
    '800-426-4968'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Megafon',
    '8 (800) 550-58-58'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Сбербанк',
    '8462256328'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'ВТБ-24',
    '8462543328'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Tinkoff-bank',
    '8462776328'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Vita',
    '8462756312'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Haulmont',
    '84622334328'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Apple',
    '+1 (301) 101-1030'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'NetCracker',
    '312-14-44'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'EPAM',
    '89993111223'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'CQG',
    '89911233212'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Открытый код',
    '89273353324'
);

INSERT INTO enterprise VALUES (
    enterprise_id_seq.NEXTVAL,
    'Google',
    '+1 (650) 253-0000'
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Габрусевич Павел Евгеньевич',
    '89650738240',
    'misefealaska@gmail.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Чугунов Евгений Александрович',
    '89650711140',
    'kolobok@gmail.com',
    20
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Харламов Ярослав Александрович',
    '89990738240',
    'harlamov@gmail.com',
    24
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Голов Максим Евгеньевич',
    '+7 (927) 700-45-91',
    'afterfocus@icloud.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Каймаков Кирилл Сергеевич',
    '89611738240',
    'kaymakov@gmail.com',
    20
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Шепелев Федор Иванович',
    '88888738240',
    'shepelev@gmail.com',
    20
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Иванов Петр Петрович',
    '89096756787',
    'ivanov@gmail.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Кузнецова Алина Юрьевна',
    '81234234789',
    'mka@gmail.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Витина Екатерина Олеговна',
    '88989765454',
    'oloi@gmail.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Мороз Елена Петровна',
    '86754765456',
    'gendalf@gmail.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Орлова Анастасия Сергеевна',
    '80987654321',
    'ska@gmail.com',
    21
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Постафина Мария Серегеевна',
    '89999876542',
    'feaka@gmail.com',
    22
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Постафин Дмитрий Сергеевич',
    '89999876545',
    'feak@gmail.com',
    23
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Холодев Альберт Эдуардович',
    '89999876544',
    'holoda@gmail.com',
    24
);

INSERT INTO employee VALUES (
    employee_id_seq.NEXTVAL,
    'Забиякин Тимур Рафатович',
    '89999876343',
    'timur@gmail.com',
    22
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Программист'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Инженер'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Менеджер'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Преподаватель'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Юрист'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Киллер'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Министр'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Повар'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Телохранитель'
);

INSERT INTO speciality VALUES (
    speciality_id_seq.NEXTVAL,
    'Разведчик'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    1,
    1,
    1,
    '12-07-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    10,
    2,
    3,
    '12-06-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    5,
    3,
    0,
    '12-08-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    3,
    4,
    10,
    '22-12-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    4,
    5,
    3,
    '12-07-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    5,
    6,
    4,
    '10-05-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    6,
    7,
    2,
    '12-06-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    7,
    8,
    15,
    '11-07-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    8,
    9,
    1,
    '11-07-17'
);

INSERT INTO resume VALUES (
    resume_id_seq.NEXTVAL,
    9,
    10,
    0,
    '11-07-17'
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    1,
    1,
    1,
    50000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    2,
    6,
    3,
    40000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    3,
    1,
    1,
    60000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    4,
    1,
    5,
    70000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    5,
    2,
    2,
    40000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    6,
    1,
    1,
    30000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    7,
    6,
    0,
    20000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    8,
    4,
    3,
    55000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    9,
    1,
    5,
    58000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    10,
    1,
    5,
    58000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    11,
    1,
    1,
    19000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    12,
    1,
    3,
    48000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    13,
    3,
    0,
    8000
);

INSERT INTO vacancy VALUES (
    vacancy_id_seq.NEXTVAL,
    14,
    1,
    8,
    90000
);

COMMIT;