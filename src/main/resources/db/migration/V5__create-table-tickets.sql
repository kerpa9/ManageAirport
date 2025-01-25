create table tickets(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_ticket bigint,
        passenger_id bigint,
        user_id bigint,
        type_class VARCHAR(50) NOT NULL CHECK (type_class IN ("first_class", "ejecutive_class","premium_economic_class","economic_class")),
        price float not null,
        seat_number integer not null,
        user varchar(200),
        passenger varchar(200),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        active boolean default true,
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_ticket),

        constraint fk_tickets_passenger_id foreign key (passenger_id) references passenger (id),
        constraint fk_tickets_user_id foreign key (user_id) references user (id),

   
        primary key(id)

);