create table user(
        
        id bigint not null auto_increment,
        full_name varchar(100) not null,
        email varchar(100) not null unique,
        password varchar(100) not null,
        genre VARCHAR(50) NOT NULL CHECK (genre IN ("male", "female", "prefer_not_to_say")),
        role_user VARCHAR(50) NOT NULL CHECK (role_user IN ("user", "receptionist", "admin", "manager", "developers"
        )) DEFAULT 'user',
        verification varchar(255),
        email_verified boolean default false,
        token_expiry DATETIME DEFAULT CURRENT_TIMESTAMP,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        active boolean default true,
   
        primary key(id)



);