create table plane(
        
        id bigint not null auto_increment,
        id_login bigint,
        id_plane bigint,
        plane_number integer not null,
        model varchar(100) not null,
        max_capacity integer not null,
        airline VARCHAR(50) NOT NULL CHECK (airline IN ("AeroGlobe",  "AeroTronix", "Avianca", "AeroMexico", "Qtar", "Emirates","Latam")),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        plane_status VARCHAR(50) NOT NULL CHECK (plane_status IN ("active", "maintenance", "repair", "inactive")) DEFAULT 'active',
        active boolean default true,
        INDEX idx_id_login (id_login),
        INDEX idx_id_login_id (id_login, id),
        UNIQUE INDEX uk_user_sequential (id_login, id_plane),
   
        primary key(id)
        );