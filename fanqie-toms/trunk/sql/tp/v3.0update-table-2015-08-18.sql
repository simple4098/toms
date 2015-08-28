ALTER TABLE ota_info DROP COLUMN "company_id", DROP COLUMN "appkey", DROP COLUMN "appsecret",DROP COLUMN "sessionkey",DROP COLUMN "expired_time";
alter table ota_info add column sort int2;


alter table ota_inn_ota add column bang_inn_id varchar(64);
alter table ota_inn_ota add column ota_info_id varchar(64);
alter table ota_inn_ota add column inn_id int8;
alter table ota_inn_ota add column sj int2;
--创建索引
CREATE INDEX  ON "public"."ota_inn_ota" ("wg_hid", "company_id", "bang_inn_id", "ota_info_id");

ALTER TABLE ota_toms_order ADD COLUMN company_id varchar(64);
COMMENT ON COLUMN "public"."ota_toms_order"."company_id" IS '公司id';