alter table ota_bang_inn_room add column created_date timestamp(6);
alter table ota_bang_inn_room add column updated_date timestamp(6);


alter table ota_inn_roomtype_goods add column created_date timestamp(6);
alter table ota_inn_roomtype_goods add column updated_date timestamp(6);

alter table xc_roomtype_fq add column "fq_rate_plan_code" varchar(50);
alter table xc_roomtype_fq add column "room_type_code" varchar(64);
alter table xc_roomtype_fq add column "inn_code" varchar(64);
COMMENT ON COLUMN "public"."xc_roomtype_fq"."fq_rate_plan_code" IS '番茄价格计划code';

COMMENT ON COLUMN "public"."xc_roomtype_fq"."room_type_code" IS 'otaid_roomTypeId';

COMMENT ON COLUMN "public"."xc_roomtype_fq"."inn_code" IS 'otaId_innId';


