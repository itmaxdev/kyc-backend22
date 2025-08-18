ALTER table service_providers add column color varchar(50) default null;

update service_providers set color = "rgb(160, 23, 117)" where id = 20;
update service_providers set color = "rgb(189, 4, 4)" where id = 24;
update service_providers set color = "rgb(227, 110, 11)" where id = 27;
update service_providers set color = "rgb(8, 148, 168)" where id = 28;