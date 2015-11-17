ALTER TABLE "public"."otainfo_company_ref" ADD COLUMN "used_price_model" varchar(100);

UPDATE  otainfo_company_ref set used_price_model='MAI2DI' where id='572c1b47-9343-4567-981c-895ki8j18';
UPDATE  otainfo_company_ref set used_price_model='MAI' where id='1';



CREATE TABLE "public"."ota_commission_percent" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"commission_percent" numeric(3),
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"sjia_model" varchar(20) COLLATE "default",
"ota_id" int8,
"operate_type" varchar(30) COLLATE "default",
CONSTRAINT "ota_commission_percent_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_commission_percent" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_commission_percent"."company_id" IS '公司id';

COMMENT ON COLUMN "public"."ota_commission_percent"."commission_percent" IS '佣金比';

COMMENT ON COLUMN "public"."ota_commission_percent"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."ota_commission_percent"."sjia_model" IS '上架模式';

COMMENT ON COLUMN "public"."ota_commission_percent"."ota_id" IS '渠道id';

COMMENT ON COLUMN "public"."ota_commission_percent"."operate_type" IS '类型';

CREATE INDEX "ota_commission_percent_company_id_sjia_model_ota_id_operate_idx" ON "public"."ota_commission_percent" USING btree (company_id, sjia_model, ota_id, operate_type);



INSERT INTO "public"."dictionary" ("id", "desc", "url", "type", "value", "v_name", "v_pwd") VALUES ('5', '获取房型某天信息', 'http://192.168.1.159:8888/api/get_room_day_info', 'ROOM_DAY_INFO', '903', 'TB', 'tb');
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "used_price_model" varchar(20);
ADD COLUMN "add_price" decimal;

COMMENT ON COLUMN "public"."ota_toms_order"."add_price" IS '加减价';

COMMENT ON COLUMN "public"."ota_toms_order"."used_price_model" IS '当前价格模式';

ALTER TABLE "public"."ota_daily_infos"
ADD COLUMN "weather_add" int2;

COMMENT ON COLUMN "public"."ota_daily_infos"."weather_add" IS '是否加减价';

