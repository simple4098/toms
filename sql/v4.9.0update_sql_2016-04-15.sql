ALTER TABLE "public"."bang_inn"
ADD COLUMN "qunar_city_code" varchar(50);

COMMENT ON COLUMN "public"."bang_inn"."qunar_city_code" IS '去哪儿的城市code';
ALTER TABLE "public"."bang_inn"
ADD COLUMN "address" varchar(200);

COMMENT ON COLUMN "public"."bang_inn"."address" IS '客栈地址';

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "xml_data" varchar(255);

COMMENT ON COLUMN "public"."ota_toms_order"."xml_data" IS '创建订单请求的xml数据';
ALTER TABLE "public"."ota_toms_order"
ALTER COLUMN "xml_data" TYPE text COLLATE "default";
