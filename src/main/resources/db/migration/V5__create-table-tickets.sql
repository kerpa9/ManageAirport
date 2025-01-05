create table tickets(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_ticket bigint,
        type_class VARCHAR(50) NOT NULL CHECK (type_class IN ("first_class", "ejecutive_class","premium_economic_class","economic_class")),
        price float not null,
        seat_number integer not null,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        active boolean default true,
   
        primary key(id)

);