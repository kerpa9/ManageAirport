create table plane(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_plane bigint,
        plane_number integer not null,
        model varchar(100) not null,
        max_capacity integer not null,
        airline VARCHAR(50) NOT NULL CHECK (airline IN ("AeroGlobe",  "AeroTronix", "Avianca", "AeroMexico", "Qtar", "Emirates","Latam")),
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        active boolean default true,
   
        primary key(id)

);