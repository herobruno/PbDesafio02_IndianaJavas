INSERT INTO tb_user (id, email, first_name, last_name, password)
VALUES (1, 'admin@mail.com', 'ADMIN', 'ADMIN', '$2a$12$bfxoy5Ex7.IpjP5zCvzhYupypaWvxhLVbMJ6OEylwuBiI4jGr7nXS'),
       (2, 'jorge@bagre.com', 'Jorge', 'Bagre', '$2a$12$AJEZFOBOCzOiVLM0wiTNE.mJgQTBIVN5I8mwHWVgXa91VFszgEcIm');

INSERT INTO tb_user_role (user_id, role_id)
VALUES (1, 1),
       (2, 2);