create table flight(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_flight bigint,
        origin_id bigint,
        destination_id bigint,
        plane_id bigint,
        departure_time DATETIME DEFAULT CURRENT_TIMESTAMP,
        check_in_start DATETIME DEFAULT CURRENT_TIMESTAMP,
        check_in_end DATETIME DEFAULT CURRENT_TIMESTAMP,
        available_seats bigint,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        city varchar(200),
        plane varchar(200),
        flight_status VARCHAR(50) NOT NULL CHECK (flight_status IN ("pending", "scheduled", "boarding", "in_progress", "delayed","done","cancelled","diverted","technical_stop","returned"
        )) DEFAULT 'pending',
        active boolean default true,
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_flight),

        constraint fk_flight_city_id foreign key (origin_id) references city (id),

        constraint fk_flight_city_id foreign key (destination_id) references city (id),

        constraint fk_flight_plane_id foreign key (plane_id) references plane (id),
        

        primary key(id)

);