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
    speciality_id   NUMBER NOT NULL,
    employee_id     NUMBER NOT NULL,
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
    salary          NUMBER,
    experience      NUMBER,
    speciality_id   NUMBER NOT NULL
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

CREATE SEQUENCE  EMPLOYEE_ID_SEQ
MINVALUE 1
MAXVALUE 10000
INCREMENT BY 1
START WITH 1
NOCACHE
NOORDER
NOCYCLE;

CREATE SEQUENCE  ENTERPRISE_ID_SEQ
MINVALUE 1
MAXVALUE 10000
INCREMENT BY 1
START WITH 1
NOCACHE
NOORDER
NOCYCLE;

CREATE SEQUENCE  RESUME_ID_SEQ
MINVALUE 1
MAXVALUE 10000
INCREMENT BY 1
START WITH 1
NOCACHE
NOORDER
NOCYCLE;

CREATE SEQUENCE  VACANCY_ID_SEQ
MINVALUE 1
MAXVALUE 10000
INCREMENT BY 1
START WITH 1
NOCACHE
NOORDER
NOCYCLE;

CREATE SEQUENCE  SPECIALITY_ID_SEQ
MINVALUE 1
MAXVALUE 10000
INCREMENT BY 1
START WITH 1
NOCACHE
NOORDER
NOCYCLE;

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Mercury',
    '8462756328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Altarix',
    '8462116328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'IBM',
    '8462756111'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Megafon',
    '8462716328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Sberbank',
    '8462256328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'VTB-24',
    '8462543328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Tinkoff-bank',
    '8462776328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Vita',
    '8462756312'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Haulmont',
    '84622334328'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Apple',
    '88888888888'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Apple',
    '89223121444'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Apple',
    '89993111223'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Apple',
    '89911233212'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Apple',
    '89273353324'
);

INSERT INTO enterprise VALUES (
    ENTERPRISE_ID_SEQ.NEXTVAL,
    'Apple',
    '89973332244'
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Габрусевич Павел Евгеньевич',
    '89650738240',
    'misefealaska@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Чугунов Евгений Александрович',
    '89650711140',
    'kolobok@gmail.com',
    20
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Харламов Ярослав Александрович',
    '89990738240',
    'harlamov@gmail.com',
    24
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Голов Максим Юрьевич',
    '87656543211',
    'golov@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Каймаков Кирилл Сергеевич',
    '89611738240',
    'kaymakov@gmail.com',
    20
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Шепелев Федор Иванович',
    '88888738240',
    'shepelev@gmail.com',
    20
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Иванов Петр Петрович',
    '89096756787',
    'ivanov@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Кузнецова Алина Юрьевна',
    '81234234789',
    'mka@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Витина Екатерина Олеговна',
    '88989765454',
    'oloi@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Мороз Елена Петровна',
    '86754765456',
    'gendalf@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Орлова Анастасия Сергеевна',
    '80987654321',
    'ska@gmail.com',
    21
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Постафина Мария Серегеевна',
    '89999876542',
    'feaka@gmail.com',
    22
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Постафин Дмитрий Сергеевич',
    '89999876545',
    'feak@gmail.com',
    23
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Холодев Альберт Эдуардович',
    '89999876544',
    'holoda@gmail.com',
    24
);

INSERT INTO employee VALUES (
    EMPLOYEE_ID_SEQ.NEXTVAL,
    'Забиякин Тимур Рафатович',
    '89999876343',
    'timur@gmail.com',
    22
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Программист'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Инженер'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Менеджер'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Преподаватель'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Юрист'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Киллер'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Министр'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Повар'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Телохранитель'
);

INSERT INTO speciality VALUES (
    SPECIALITY_ID_SEQ.NEXTVAL,
    'Разведчик'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    1,
    1,
    '12-07-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    1,
    2,
    '12-06-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    1,
    3,
    '12-08-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    3,
    4,
    '22-12-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    4,
    5,
    '12-07-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    5,
    6,
    '10-05-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    6,
    7,
    '12-06-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    7,
    8,
    '11-07-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    8,
    9,
    '11-07-17'
);

INSERT INTO resume VALUES (
    RESUME_ID_SEQ.NEXTVAL,
    9,
    10,
    '11-07-17'
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    1,
    50000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    2,
    40000,
    2,
    6
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    3,
    60000,
    3,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    4,
    70000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    5,
    40000,
    2,
    2
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    6,
    30000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    7,
    20000,
    2,
    6
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    8,
    55000,
    2,
    4
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    9,
    58000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    10,
    58000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    11,
    19000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    12,
    48000,
    2,
    1
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    13,
    8000,
    2,
    3
);

INSERT INTO vacancy VALUES (
    VACANCY_ID_SEQ.NEXTVAL,
    14,
    90000,
    2,
    1
);

COMMIT;
