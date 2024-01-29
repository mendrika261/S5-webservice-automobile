INSERT INTO public.page (id, exact, level, url) VALUES (0, false, 20, '/admin');
INSERT INTO public.page (id, exact, level, url) VALUES (1, false, 10, '/deconnexion');
INSERT INTO public.page (id, exact, level, url) VALUES (2, true, 0, '/connexion');
INSERT INTO public.page (id, exact, level, url) VALUES (3, true, 0, '/inscription');
INSERT INTO public.page (id, exact, level, url) VALUES (5, false, 0, '/tokens/utilisateur');
INSERT INTO public.page (id, exact, level, url) VALUES (6, true, 10, '/api');
INSERT INTO public.page (id, exact, level, url) VALUES (7, true, 0, '/auth');


INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('5c819151-7490-46b0-ab75-310694115d20', '032 19 203 29', 'zo@gmail.com', 20, '12345678', 'Zo', 'Randrianarison', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('fef61249-5ec2-4dcf-858d-6c1b793c390a', '032 18 237 28', 'ravaka@gmail.com', 10, '12345678', 'Ravaka', 'Raharinirina', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('7493d01d-5ca0-493e-8a6d-5349f3aa1998', '032 18 237 29', 'mirana@gmail.com', 10, '12345678', 'Mirana', 'Randrianarison', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('73e43741-7dc3-4d50-b577-973f0053f1c3', '032 18 237 30', 'nantenaina@gmail.com', 10, '12345678', 'Nantenaina', 'Andriamihaja', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('cc4da39e-4b2b-4b54-aeac-990112b9061b', '032 18 237 31', 'lalaina@gmail.com', 10, '12345678', 'Lalaina', 'Randrianarisoa', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('513b8fdf-5418-4fc5-81ba-71804966e05e', '032 18 237 32', 'harisoa@gmail.com', 10, '12345678', 'Harisoa', 'Rajaonarivelo', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('c0634fd7-4be1-4310-bde0-78da3483d0e6', '032 18 237 33', 'fanomezantsoa@gmail.com', 10, '12345678', 'Fanomezantsoa', 'Rakotoson', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('2d91672e-cf72-497d-883c-081a8d2cc8e7', '032 18 237 34', 'eric@gmail.com', 10, '12345678', 'Eric', 'Randrianarison', null);
INSERT INTO public.utilisateur (id, contact, email, level, mot_de_passe, nom, prenom, fichier_id) VALUES ('1aa65e40-3490-40b0-ab9d-df7f9280a121', '032 18 237 35', 'jose@gmail.com', 10, '12345678', 'Jose', 'Randrianarivelo', null);

INSERT INTO public.commission (id, date_application, min_prix, max_prix, pourcentage) VALUES (1, '2024-01-01 00:00:00.000000', 5000000, 8000000, 20);
INSERT INTO public.commission (id, date_application, min_prix, max_prix, pourcentage) VALUES (2, '2024-01-02 00:00:00.000000', 0, 10000000, 25);
INSERT INTO public.commission (id, date_application, min_prix, max_prix, pourcentage) VALUES (3, '2024-01-02 00:00:00.000000', 20000, 100000, 23);
INSERT INTO public.commission (id, date_application, min_prix, max_prix, pourcentage) VALUES (4, '2024-01-04 00:00:00.000000', 10000, 80000, 30);
INSERT INTO public.commission (id, date_application, min_prix, max_prix, pourcentage) VALUES (5, '2024-01-20 00:00:00.000000', 200000, 10000000, 50);
INSERT INTO public.commission (id, date_application, min_prix, max_prix, pourcentage) VALUES (6, '2024-02-20 00:00:00.000000', 3000000, 9000000, 35);

INSERT INTO public.paiement (id, designation) VALUES (1, 'Chèque');
INSERT INTO public.paiement (id, designation) VALUES (2, 'Echange');
INSERT INTO public.paiement (id, designation) VALUES (3, 'Virement');
INSERT INTO public.paiement (id, designation) VALUES (4, 'Espèces');

INSERT INTO public.marque (id, nom) VALUES (1, 'Peugeot');
INSERT INTO public.marque (id, nom) VALUES (2, 'Nissan');
INSERT INTO public.marque (id, nom) VALUES (3, 'Mitsubishi');
INSERT INTO public.marque (id, nom) VALUES (4, 'Renault');
INSERT INTO public.marque (id, nom) VALUES (5, 'Mercedes');
INSERT INTO public.marque (id, nom) VALUES (6, 'Toyota');
INSERT INTO public.marque (id, nom) VALUES (7, 'Volkswagen');
INSERT INTO public.marque (id, nom) VALUES (8, 'General Motors');
INSERT INTO public.marque (id, nom) VALUES (9, 'Hyundai');
INSERT INTO public.marque (id, nom) VALUES (10, 'Honda');
INSERT INTO public.marque (id, nom) VALUES (11, 'Ford');
INSERT INTO public.marque (id, nom) VALUES (12, 'Suzuki');

INSERT INTO public.modele (id, nom, marque_id) VALUES (1, '206', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (2, '207', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (3, '208', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (4, '301', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (5, '308', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (6, '508', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (7, '2008', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (8, '3008', 1);
INSERT INTO public.modele (id, nom, marque_id) VALUES (9, '5008', 1);

INSERT INTO public.modele (id, nom, marque_id) VALUES (10, 'Micra', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (11, 'Qashqai', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (12, 'Juke', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (13, 'X-Trail', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (14, 'Leaf', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (15, 'GT-R', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (16, 'Navara', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (17, 'Patrol', 2);
INSERT INTO public.modele (id, nom, marque_id) VALUES (18, 'Terrano', 2);

INSERT INTO public.modele (id, nom, marque_id) VALUES (19, 'Lancer', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (20, 'Pajero', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (21, 'Outlander', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (22, 'ASX', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (23, 'Eclipse Cross', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (24, 'Space Star', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (25, 'L200', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (26, 'L300', 3);
INSERT INTO public.modele (id, nom, marque_id) VALUES (27, 'L400', 3);

INSERT INTO public.modele (id, nom, marque_id) VALUES (28, 'Clio', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (29, 'Captur', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (30, 'Megane', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (31, 'Kadjar', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (32, 'Scenic', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (33, 'Talisman', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (34, 'Koleos', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (35, 'Twingo', 4);
INSERT INTO public.modele (id, nom, marque_id) VALUES (36, 'Zoe', 4);

INSERT INTO public.modele (id, nom, marque_id) VALUES (37, 'Classe A', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (38, 'Classe B', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (39, 'Classe C', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (40, 'Classe E', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (41, 'Classe S', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (42, 'Classe G', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (43, 'Classe GLA', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (44, 'Classe GLB', 5);
INSERT INTO public.modele (id, nom, marque_id) VALUES (45, 'Classe GLC', 5);

INSERT INTO public.modele (id, nom, marque_id) VALUES (46, 'Yaris', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (47, 'Corolla', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (48, 'Auris', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (49, 'Prius', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (50, 'Avensis', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (51, 'RAV4', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (52, 'Land Cruiser', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (53, 'Hilux', 6);
INSERT INTO public.modele (id, nom, marque_id) VALUES (54, 'Proace', 6);

INSERT INTO public.modele (id, nom, marque_id) VALUES (55, 'Golf', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (56, 'Polo', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (57, 'Passat', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (58, 'Tiguan', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (59, 'T-Roc', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (60, 'Touran', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (61, 'Touareg', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (62, 'Arteon', 7);
INSERT INTO public.modele (id, nom, marque_id) VALUES (63, 'Caddy', 7);

INSERT INTO public.modele (id, nom, marque_id) VALUES (64, 'Chevrolet', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (65, 'Buick', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (66, 'Cadillac', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (67, 'GMC', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (68, 'Holden', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (69, 'Opel', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (70, 'Vauxhall', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (71, 'Wuling', 8);
INSERT INTO public.modele (id, nom, marque_id) VALUES (72, 'Baojun', 8);

INSERT INTO public.modele (id, nom, marque_id) VALUES (73, 'i10', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (74, 'i20', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (75, 'i30', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (76, 'i40', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (77, 'ix20', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (78, 'ix35', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (79, 'Kona', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (80, 'Tucson', 9);
INSERT INTO public.modele (id, nom, marque_id) VALUES (81, 'Santa Fe', 9);

INSERT INTO public.modele (id, nom, marque_id) VALUES (82, 'Civic', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (83, 'Jazz', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (84, 'HR-V', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (85, 'CR-V', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (86, 'NSX', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (87, 'Accord', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (88, 'Civic Type R', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (89, 'CR-Z', 10);
INSERT INTO public.modele (id, nom, marque_id) VALUES (90, 'Insight', 10);

INSERT INTO public.modele (id, nom, marque_id) VALUES (91, 'Fiesta', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (92, 'Focus', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (93, 'Mondeo', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (94, 'Kuga', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (95, 'EcoSport', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (96, 'Edge', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (97, 'Mustang', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (98, 'GT', 11);
INSERT INTO public.modele (id, nom, marque_id) VALUES (99, 'Tourneo', 11);

INSERT INTO public.modele (id, nom, marque_id) VALUES (100, 'Swift', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (101, 'Ignis', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (102, 'Baleno', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (103, 'Celerio', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (104, 'Jimny', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (105, 'Vitara', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (106, 'SX4 S-Cross', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (107, 'Grand Vitara', 12);
INSERT INTO public.modele (id, nom, marque_id) VALUES (108, 'Alto', 12);

INSERT INTO public.boite_vitesse (id, nom) VALUES (1, 'Manuel');
INSERT INTO public.boite_vitesse (id, nom) VALUES (2, 'Automatique');
INSERT INTO public.boite_vitesse (id, nom) VALUES (3, 'Semi-automatique');

INSERT INTO public.etat_voiture (id, designation) VALUES (1, 'Neuve');
INSERT INTO public.etat_voiture (id, designation) VALUES (2, 'Occasion');
INSERT INTO public.etat_voiture (id, designation) VALUES (3, 'Véhicule non roulant');
INSERT INTO public.etat_voiture (id, designation) VALUES (4, 'Véhicule accidenté');

INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (1, 'Vitres électriques', 'string', 'avant seulement, arrière seulement, tous');
INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (2, 'Rétroviseurs électriques', 'string', 'côté seulement, milieu seulement, tous');
INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (3, 'Climatisation', 'string', 'manuelle, automatique');
INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (4, 'Radio', 'string', 'AM FM, AM FM CD, AM FM CD USB');
INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (5, 'Lecteur de DVD', 'string', 'oui, non');
INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (6, 'Système de navigation', 'string', 'oui, non');
INSERT INTO public.option (id, designation, type_valeur, valeurs) VALUES (7, 'Toit ouvrant', 'string', 'oui, non');

INSERT INTO public.pays (id, code, nom) VALUES (1, 'fr', 'France');
INSERT INTO public.pays (id, code, nom) VALUES (2, 'mg', 'Madagascar');
INSERT INTO public.pays (id, code, nom) VALUES (3, 'us', 'Etats-Unis');
INSERT INTO public.pays (id, code, nom) VALUES (4, 'gb', 'Royaume-Uni');
INSERT INTO public.pays (id, code, nom) VALUES (5, 'de', 'Allemagne');
INSERT INTO public.pays (id, code, nom) VALUES (6, 'jp', 'Japon');
INSERT INTO public.pays (id, code, nom) VALUES (7, 'cn', 'Chine');
INSERT INTO public.pays (id, code, nom) VALUES (8, 'ru', 'Russie');
INSERT INTO public.pays (id, code, nom) VALUES (9, 'br', 'Brésil');
INSERT INTO public.pays (id, code, nom) VALUES (10, 'it', 'Italie');
INSERT INTO public.pays (id, code, nom) VALUES (11, 'ca', 'Canada');
INSERT INTO public.pays (id, code, nom) VALUES (12, 'au', 'Australie');
INSERT INTO public.pays (id, code, nom) VALUES (13, 'in', 'Inde');
INSERT INTO public.pays (id, code, nom) VALUES (14, 'es', 'Espagne');
INSERT INTO public.pays (id, code, nom) VALUES (15, 'mx', 'Mexique');
INSERT INTO public.pays (id, code, nom) VALUES (16, 'id', 'Indonésie');
INSERT INTO public.pays (id, code, nom) VALUES (17, 'tr', 'Turquie');
INSERT INTO public.pays (id, code, nom) VALUES (18, 'nl', 'Pays-Bas');
INSERT INTO public.pays (id, code, nom) VALUES (19, 'ch', 'Suisse');
INSERT INTO public.pays (id, code, nom) VALUES (20, 'sa', 'Arabie Saoudite');

INSERT INTO public.energie (id, nom) VALUES (1, 'Essence');
INSERT INTO public.energie (id, nom) VALUES (2, 'Electrique');
INSERT INTO public.energie (id, nom) VALUES (3, 'Hydrogène');
INSERT INTO public.energie (id, nom) VALUES (4, 'Diesel');


-- peugeot
-- 206
INSERT INTO public.sortie_voiture (id, annee, consommation, nbr_place, nbr_porte, puissance, vitesse_max, boite_vitesse_id, energie_id, modele_id, pays_id) VALUES (1, 2007, 5.2, 5, 5, 120, 218, 1, 1, 1, 1);

-- Nissan
INSERT INTO public.sortie_voiture (id, annee, consommation, nbr_place, nbr_porte, puissance, vitesse_max, boite_vitesse_id, energie_id, modele_id, pays_id) VALUES
                                                                                                                                                                (2, 2023, 4.8, 5, 5, 110, 184, 2, 2, 10, 1),
                                                                                                                                                                (3, 2022, 5.3, 5, 5, 158, 193, 1, 2, 11, 5),
                                                                                                                                                                (4, 2021, 5.9, 5, 5, 190, 212, 2, 2, 12, 6);
                                                                                                                   (15, 2022, 5.5, 5, 5, 132, 195, 1, 2, 47, 5);