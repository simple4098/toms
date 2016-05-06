
ALTER TABLE "public"."bang_inn"
ADD COLUMN "address" varchar(200);

COMMENT ON COLUMN "public"."bang_inn"."address" IS '客栈地址';

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "xml_data" varchar(255);

COMMENT ON COLUMN "public"."ota_toms_order"."xml_data" IS '创建订单请求的xml数据';
ALTER TABLE "public"."ota_toms_order"
ALTER COLUMN "xml_data" TYPE text COLLATE "default";

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "user_id" varchar(64);

COMMENT ON COLUMN "public"."ota_toms_order"."user_id" IS '操作人id';


DROP TABLE IF EXISTS "public"."bang_inn_to_qunar_city";
CREATE TABLE "public"."bang_inn_to_qunar_city" (
"id" varchar(64) COLLATE "default" NOT NULL,
"bang_inn_id" varchar(64) COLLATE "default",
"qunar_city_info_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."bang_inn_to_qunar_city"."id" IS 'id';
COMMENT ON COLUMN "public"."bang_inn_to_qunar_city"."bang_inn_id" IS '绑定的客栈id';
COMMENT ON COLUMN "public"."bang_inn_to_qunar_city"."qunar_city_info_id" IS '去哪儿city的id';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table bang_inn_to_qunar_city
-- ----------------------------
ALTER TABLE "public"."bang_inn_to_qunar_city" ADD PRIMARY KEY ("id");


--ota
ALTER TABLE "public"."ota_inn_ota"
ADD COLUMN "updated_date" timestamp(6),
ADD COLUMN "created_date" timestamp(6);

