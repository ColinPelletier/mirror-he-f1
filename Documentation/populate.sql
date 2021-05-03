/*  CLEAR DB CONTENT */
/* create database he_f1; */
use he_f1;

/* PIECES_CATEGORY  */
insert into piece_category (id, name) VALUES (1, "aerodynamics");
insert into piece_category (id, name) VALUES (2, "sensor");
insert into piece_category (id, name) VALUES (3, "engine");
insert into piece_category (id, name) VALUES (4, "cooling");

/* PIECES */
insert into pieces (id, piece_category, name, base_repair_price, base_repair_time, base_upgrade_price, base_upgrade_time) VALUES (1, 1, "Front wing", 50000, 2, 50000, 5);
insert into pieces (id, piece_category, name, base_repair_price, base_repair_time, base_upgrade_price, base_upgrade_time) VALUES (2, 1, "Rear wing", 60000, 2, 50000, 5);
insert into pieces (id, piece_category, name, base_repair_price, base_repair_time, base_upgrade_price, base_upgrade_time) VALUES (3, 1, "Bargeboard", 50000, 3, 100000, 10);
insert into pieces (id, piece_category, name, base_repair_price, base_repair_time, base_upgrade_price, base_upgrade_time) VALUES (4, 3, "MGU-H", 20000, 5, 1200000, 10);
insert into pieces (id, piece_category, name, base_repair_price, base_repair_time, base_upgrade_price, base_upgrade_time) VALUES (5, 2, "Tyre heat sensors", 5000, 1, 10000, 5);
insert into pieces (id, piece_category, name, base_repair_price, base_repair_time, base_upgrade_price, base_upgrade_time) VALUES (6, 4, "Oil System", 5000, 1, 100000, 2);

/* TEAM */
insert into teams (id, name, budget) VALUES (1, "Mercedes AMG Petronas Formula 1 Team", 4000000);
insert into teams (id, name, budget) VALUES (2, "Scuderia Ferrari", 4500000);
insert into teams (id, name, budget) VALUES (3, "Scuderia Alpha Tauri", 1500000);

/* CARS */
insert into cars (id, team_id, name) VALUES (1, 1, "W12");
insert into cars (id, team_id, name) VALUES (2, 2, "SF21");
insert into cars (id, team_id, name) VALUES (3, 3, "AT02");

/* CAR_PIECES */
	/* W12 */
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (1, 1, 1, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (2, 1, 2, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (3, 1, 3, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (4, 1, 4, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (5, 1, 5, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (6, 1, 6, NULL, 1, 100);
	/* SF21 */
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (7, 2, 1, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (8, 2, 2, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (9, 2, 3, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (10, 2, 4, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (11, 2, 5, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (12, 2, 6, NULL, 1, 100);
	/* AT02 */
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (13, 3, 1, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (14, 3, 2, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (15, 3, 3, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (16, 3, 4, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (17, 3, 5, NULL, 1, 100);
insert into car_pieces (id, car_id, piece_id, repair_upgrade_id, level, wear) VALUES (18, 3, 6, NULL, 1, 100);