create table login(
        
        id bigint not null auto_increment,
        email varchar(100) not null unique,
        password varchar(100) not null,
        active boolean default true,
   
        primary key(id)

);