CREATE TABLE public.challenge (
                           id serial constraint challenge_pk primary key,
                           date DATE NOT NULL ,
                           geo_guessr_id VARCHAR(255) NOT NULL UNIQUE,
                           type VARCHAR(255) NOT NULL
);

CREATE TABLE public.user (
                      id serial constraint user_pk primary key, -- Replace with your actual primary key definition from AbstractEntity
                      geo_guessr_id VARCHAR(255),
                      geo_guessr_name VARCHAR(255)
);

CREATE TABLE public.user_challenge (
                                user_id BIGINT,
                                challenge_id BIGINT,
                                PRIMARY KEY (user_id, challenge_id),
                                FOREIGN KEY (user_id) REFERENCES user(id),
                                FOREIGN KEY (challenge_id) REFERENCES challenge(id)
);