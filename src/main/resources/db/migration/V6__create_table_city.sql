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
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_city),
   
        primary key(id)

);