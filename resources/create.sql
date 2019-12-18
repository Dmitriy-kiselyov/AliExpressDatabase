CREATE DATABASE ali_express
  CHARACTER SET utf8
  COLLATE utf8_unicode_ci;

CREATE TABLE ali_express.категории
(
  ид       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  название VARCHAR(30)     NOT NULL
);
CREATE UNIQUE INDEX категории_название_index
  ON ali_express.категории (название);

CREATE TABLE ali_express.товары
(
  ид           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  ид_категории INT             NOT NULL,
  название     VARCHAR(40)     NOT NULL,
  цена         DECIMAL(10, 2)  NOT NULL,
  описание     TEXT,
  CONSTRAINT товары_категории_ид_fk FOREIGN KEY (ид_категории) REFERENCES категории (ид)
);

CREATE TABLE ali_express.покупатели
(
  ид      INT PRIMARY KEY                 NOT NULL AUTO_INCREMENT,
  ник     VARCHAR(20)                     NOT NULL,
  имя     VARCHAR(20)                     NOT NULL,
  фамилия VARCHAR(25),
  пол     ENUM ('М', 'Ж')
          CHARACTER SET utf8 DEFAULT 'М'  NOT NULL,
  баланс  DOUBLE(10, 2) DEFAULT 0         NOT NULL
);
CREATE UNIQUE INDEX покупатели_ник_index
  ON ali_express.покупатели (ник);

CREATE TABLE ali_express.покупки
(
  ид            INT PRIMARY KEY                          NOT NULL AUTO_INCREMENT,
  ид_покупателя INT,
  дата_покупки  DATE                                     NOT NULL,
  дата_доставки DATE,
  ид_товара     INT,
  количество    INT                                      NOT NULL,
  статус        ENUM ('проверяется', 'в пути', 'доставлен', 'отменен')
                CHARACTER SET utf8 DEFAULT 'проверяется' NOT NULL,
  CONSTRAINT покупки_покупатели_ид_fk FOREIGN KEY (ид_покупателя) REFERENCES покупатели (ид)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT покупки_товары_ид_fk FOREIGN KEY (ид_товара) REFERENCES товары (ид)
    ON DELETE SET NULL
    ON UPDATE SET NULL
);

CREATE TABLE ali_express.статусы
(
  название ENUM ('проверяется', 'в пути', 'доставлен', 'отменен') CHARACTER SET utf8 NOT NULL,
  переход ENUM ('проверяется', 'в пути', 'доставлен', 'отменен') CHARACTER SET utf8 NOT NULL
);