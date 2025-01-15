create table passenger(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_passenger bigint,
        user_id bigint,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        born_date DATETIME DEFAULT CURRENT_TIMESTAMP,
        genre VARCHAR(50) NOT NULL CHECK (genre IN ("male", "female", "prefer_not_to_say")),
        email varchar(100) not null,
        password varchar(100),
        phone varchar(100) not null,
        user varchar(100),
        active boolean default true,
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_passenger),
   
        constraint fk_passenger_user_id foreign key (user_id) references user (id),
        
        primary key(id)

);