USE ali_express;

INSERT INTO категории (название) VALUES ('Товары для детей');
INSERT INTO категории (название) VALUES ('Товары для животных');
INSERT INTO категории (название) VALUES ('Товары для гигиены');

INSERT INTO товары VALUES (NULL, 1, 'Погремушка', 60, NULL);
INSERT INTO товары
VALUES (NULL, 1, 'Вертолётик на радиоуправлении', 3499.99, 'Очень дорогой вертолетик для капризных детишек!');
INSERT INTO товары VALUES (NULL, 1, 'Мягкий медвежонок', 2249.99, 'Большой и пушистый!!! ♥♥♥');
INSERT INTO товары VALUES (NULL, 1, 'Колокольчик', 120, NULL);
INSERT INTO товары VALUES (NULL, 1, 'Альбом любимой', 9999.99, NULL);
INSERT INTO товары VALUES (NULL, 2, 'Корм для собак 100г', 43.50, NULL);
INSERT INTO товары VALUES (NULL, 2, 'Корм для собак 200г', 75, NULL);
INSERT INTO товары VALUES (NULL, 2, 'Корм для кошек 100г', 55, NULL);
INSERT INTO товары VALUES (NULL, 2, 'Корм для кошек 200г', 95, NULL);
INSERT INTO товары VALUES (NULL, 3, 'Зубная щетка', 78, 'Лучше всего применять с зубной пастой Colgate');
INSERT INTO товары VALUES (NULL, 3, 'Туалетная бумага', 15.1, 'Для настоящих засранцев!');
INSERT INTO товары
VALUES (NULL, 3, 'Гель для душа ПАЛМОЛИВ', 80.15, 'Палмолив мой нежный гель, ты даришь запах орхидей');

INSERT INTO покупатели VALUES (NULL, 'Dmitriy_penetrator', 'Дмитрий', 'Киселёв', 'М', 1000);
INSERT INTO покупатели VALUES (NULL, 'Tanya.orlova', 'Танюша', 'Орлова', 'Ж', 999);
INSERT INTO покупатели VALUES (NULL, 'Vasya_228', 'Васька', 'Пупкин', 'М', 125);
INSERT INTO покупатели VALUES (NULL, 'MAMA_ama_KrImInAL', 'Аноним', NULL, 'М', 0);

INSERT INTO покупки VALUES (NULL, 1, '2017-02-15', '2017-03-25', 5, 1, 'доставлен');
INSERT INTO покупки VALUES (NULL, 1, '2017-05-11', NULL, 3, 1, DEFAULT);
INSERT INTO покупки VALUES (NULL, 2, '2016-09-26', NULL, 8, 2, 'отменен');
INSERT INTO покупки VALUES (NULL, 2, '2016-10-10', '2016-11-01', 8, 4, 'доставлен');
INSERT INTO покупки VALUES (NULL, 2, '2016-11-01', '2016-11-21', 9, 3, 'доставлен');
INSERT INTO покупки VALUES (NULL, 2, '2017-01-15', '2017-01-30', 10, 1, 'доставлен');
INSERT INTO покупки VALUES (NULL, 3, '2016-10-24', '2016-11-06', 11, 5, 'доставлен');
INSERT INTO покупки VALUES (NULL, 3, '2016-11-07', '2016-11-30', 11, 15, 'доставлен');
INSERT INTO покупки VALUES (NULL, 3, '2016-12-03', NULL, 11, 120, 'отменен');
INSERT INTO покупки VALUES (NULL, 3, '2017-04-29', NULL, 11, 50, 'в пути');