INSERT INTO public.page (id, exact, level, url)
    VALUES ('0', TRUE, 0, '/connexion');

INSERT INTO public.page (id, exact, level, url)
    VALUES ('1', FALSE, 10, '/deconnexion');

INSERT INTO public.utilisateur (id, level, email, mot_de_passe)
    VALUES ('0', 10, 'user@user.com', '1234');
