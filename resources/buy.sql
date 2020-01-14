SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
START TRANSACTION;
UPDATE ali_express.покупатели SET баланс = баланс - (SELECT цена FROM ali_express.товары WHERE ид = {goodId}) * {count} WHERE ид = {customerId};
INSERT INTO ali_express.покупки VALUES (NULL, {customerId}, CURDATE(), NULL, {goodId}, {count}, 'проверяется');
COMMIT;