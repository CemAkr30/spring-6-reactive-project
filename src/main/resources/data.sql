CREATE TABLE if NOT EXISTS beer
(
    İD             integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    BEER_NAME      varchar(255),
    BEER_STYLE     varchar (255),
    UPC            varchar (25),
    QUANTİTY_ON_HAND integer,
    PRİCE          decimal,
    CREATED_DATE   timestamp,
    LAST_MODİFİED_DATE timestamp
);
