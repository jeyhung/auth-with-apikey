create table users (
    id bigserial constraint pk_users primary key,
    api_key varchar(255) not null constraint uk_users_apikey unique,
    creation_at timestamp not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null
);