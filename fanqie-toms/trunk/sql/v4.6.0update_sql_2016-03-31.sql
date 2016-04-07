-- 创建订单其他费用明细表
DROP TABLE IF EXISTS "public"."order_other_price";
CREATE TABLE "public"."order_other_price" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
"other_consumer_info_id" varchar(64) COLLATE "default",
"leven" int2,
"price_name" varchar(50) COLLATE "default",
"price" numeric
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."order_other_price"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."order_other_price"."order_id" IS '订单id';
COMMENT ON COLUMN "public"."order_other_price"."other_consumer_info_id" IS '其他费用id';
COMMENT ON COLUMN "public"."order_other_price"."leven" IS '级别';
COMMENT ON COLUMN "public"."order_other_price"."price_name" IS '其他费用名称';
COMMENT ON COLUMN "public"."order_other_price"."price" IS '价格';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table order_other_price
-- ----------------------------
ALTER TABLE "public"."order_other_price" ADD PRIMARY KEY ("id");

ALTER TABLE "public"."order_other_price"
ADD COLUMN "nums" int2;

COMMENT ON COLUMN "public"."order_other_price"."nums" IS '数量';

-- 修改每日价格信息表中字段

ALTER TABLE "public"."ota_daily_infos"
ADD COLUMN "room_type_id" varchar(50),
ADD COLUMN "room_type_name" varchar(60),
ADD COLUMN "room_type_nums" int2;

COMMENT ON COLUMN "public"."ota_daily_infos"."room_type_id" IS '房型id';

COMMENT ON COLUMN "public"."ota_daily_infos"."room_type_name" IS '房型名称';

COMMENT ON COLUMN "public"."ota_daily_infos"."room_type_nums" IS '房间数量';

ALTER TABLE "public"."order_other_price"
ADD COLUMN "consumer_project_name" varchar(50);

COMMENT ON COLUMN "public"."order_other_price"."consumer_project_name" IS '其他费用项目名称';



ALTER TABLE "public"."order_other_price"
DROP COLUMN "leven";

