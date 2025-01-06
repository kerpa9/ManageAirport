create table booking(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_booking bigint,
        booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
        nro_tickets integer not null,
        total_price float not null,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        active boolean default true,
   
        primary key(id)

);