alter table anomalies add update_by varchar (255) default null;

alter table anomaly_tracking add update_by varchar (255) default null;
alter table anomaly_tracking add update_on datetime DEFAULT current_timestamp();

alter table anomaly_types add description varchar (1000) default null;