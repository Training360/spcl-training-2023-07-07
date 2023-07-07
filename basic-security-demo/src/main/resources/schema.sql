create table USERS (
  ID int not null AUTO_INCREMENT,
  USERNAME varchar(100) not null,
  PASSWORD varchar(100) not null,
  ROLE varchar(100) not null,
  PRIMARY KEY ( ID )
);