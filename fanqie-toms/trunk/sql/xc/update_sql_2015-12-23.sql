-- 新增toms订单中入住人数量，接口确认号
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "person" int2,
ADD COLUMN "interface_send_id" varchar(64);

COMMENT ON COLUMN "public"."ota_toms_order"."person" IS '入住人数量';

COMMENT ON COLUMN "public"."ota_toms_order"."interface_send_id" IS '接口确认号';

--新增入住人信息表中英文名
ALTER TABLE "public"."ota_order_guests"
ADD COLUMN "first_name" varchar(50),
ADD COLUMN "last_name" varchar(50);

COMMENT ON COLUMN "public"."ota_order_guests"."first_name" IS '英文名';

COMMENT ON COLUMN "public"."ota_order_guests"."last_name" IS '英文姓';

-- 新增携程基础酒店信息表
-- ----------------------------
-- Table structure for ctrip_hotel_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."ctrip_hotel_info";
CREATE TABLE "public"."ctrip_hotel_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"hotel_id" varchar(30) COLLATE "default",
"hotel_name" varchar(100) COLLATE "default",
"country_name" varchar(100) COLLATE "default",
"city_name" varchar(100) COLLATE "default",
"address" varchar(100) COLLATE "default",
"telephone" varchar(20) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ctrip_hotel_info"."id" IS 'ID';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."hotel_id" IS '携程酒店id';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."hotel_name" IS '携程酒店名称';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."country_name" IS '酒店所在国家名称';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."city_name" IS '酒店所在城市名称';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."address" IS '详细地址';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."telephone" IS '联系电话';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."ctrip_hotel_info"."deleted" IS '是否删除';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table ctrip_hotel_info
-- ----------------------------
ALTER TABLE "public"."ctrip_hotel_info" ADD PRIMARY KEY ("id");

