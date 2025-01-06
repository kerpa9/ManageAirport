create table flight(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_flight bigint,
        departure_time DATETIME DEFAULT CURRENT_TIMESTAMP,
        check_in DATETIME DEFAULT CURRENT_TIMESTAMP,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        active boolean default true,
   
        primary key(id)

);