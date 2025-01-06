create table city(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_city bigint,
        name varchar(100) not null,
        country varchar(100) not null,
        lat float not null,
        lon float not null,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        active boolean default true,
   
        primary key(id)

);