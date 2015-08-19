ALTER TABLE ota_info DROP COLUMN "company_id", DROP COLUMN "appkey", DROP COLUMN "appsecret",DROP COLUMN "sessionkey",DROP COLUMN "expired_time";


alter table ota_inn_ota add column bang_inn_id varchar(64);
alter table ota_inn_ota add column ota_info_id varchar(64);
alter table ota_inn_ota add column inn_id int8;