CREATE TABLE public.articles
(
    id serial NOT NULL,
    title text,
    description text,
    document text,
    url text,
    min_url text,
    image_url text,
    pub_date bigint,
    PRIMARY KEY (id)
);

ALTER TABLE public.articles
    OWNER to postgres;