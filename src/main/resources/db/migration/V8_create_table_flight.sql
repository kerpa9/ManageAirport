create table flight(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_flight bigint,
        origin_id bigint,
        destination_id bigint,
        departure_time DATETIME DEFAULT CURRENT_TIMESTAMP,
        check_in DATETIME DEFAULT CURRENT_TIMESTAMP,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        city varchar(200),
        active boolean default true,
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_flight),

        constraint fk_flight_city_id foreign key (origin_id) references city (id),

        constraint fk_flight_city_id foreign key (destination_id) references city (id),
        

        primary key(id)

);