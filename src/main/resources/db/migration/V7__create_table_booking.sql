create table booking(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_booking bigint,
        passenger_id bigint,
        user_id bigint,
        booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
        nro_tickets integer not null,
        total_price float not null,
        user varchar(200),
        passenger varchar(200),
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        active boolean default true,
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_booking),

        constraint fk_booking_passenger_id foreign key (passenger_id) references passenger (id),
        constraint fk_booking_user_id foreign key (user_id) references user (id),
   
        primary key(id)

);