use moneystats;

insert into users(nome, cognome, ddn, email, username, password) values
("Edoardo", "Carollo", "1999-06-01", "edoardo@gmail.com", "edoardo", "edoardo");
select * from users;

insert into categories(nome) values
("Contanti"),
("Conto Corrente"),
("Risparmi"),
("Cupon"),
("Investimenti");
select * from categories;

insert into wallets(name, category_id) values
("Ledger", 5),
("Coinbase", 5),
("Binance", 5),
("Contanti", 1),
("Salvadanaio", 3),
("Revolut", 2),
("BNL", 2),
("Amazon", 4);
select * from wallets;

insert into statements(date, value, user_id, wallet_id) values
("2021-06-09", 100, 1, 1),
("2021-06-09", 200, 1, 2),
("2021-06-09", 300, 1, 3),
("2021-06-09", 400, 1, 4),
("2021-06-09", 500, 1, 5),
("2021-06-08", 600, 1, 1),
("2021-06-08", 700, 1, 2),
("2021-06-08", 800, 1, 3),
("2021-06-08", 900, 1, 4),
("2021-06-08", 1000, 1, 5);
select * from statements;