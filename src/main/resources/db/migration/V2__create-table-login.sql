create table login(
        
        id bigint not null auto_increment,
        email varchar(100) not null unique,
        password varchar(100) not null,
        role_user VARCHAR(50) NOT NULL CHECK (role_user IN ("user", "receptionist", "admin", "manager", "developers"
        )) DEFAULT 'user',
   
        primary key(id)

);