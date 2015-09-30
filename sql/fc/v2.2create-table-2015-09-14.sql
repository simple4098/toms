
/*订单表，新增字段*/
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "guest_email" varchar(100),
ADD COLUMN "special_requirement" varchar(255),
ADD COLUMN "confirm_type" varchar(50),
ADD COLUMN "fc_bed_type" varchar(50),
ADD COLUMN "reserved_item" varchar(200);

COMMENT ON COLUMN "public"."ota_toms_order"."guest_email" IS '客人邮件联系方式';

COMMENT ON COLUMN "public"."ota_toms_order"."special_requirement" IS '特殊要求';

COMMENT ON COLUMN "public"."ota_toms_order"."reserved_item" IS '附加设置';

COMMENT ON COLUMN "public"."ota_toms_order"."fc_bed_type" IS '天下房仓房间类型';

COMMENT ON COLUMN "public"."ota_toms_order"."confirm_type" IS '订单确认方式';


-- 每日房间信息
ALTER TABLE "public"."ota_daily_infos"
ADD COLUMN "break_fast_type" varchar(100),
ADD COLUMN "break_fast_num" int4;

COMMENT ON COLUMN "public"."ota_daily_infos"."break_fast_type" IS '早餐类型';

COMMENT ON COLUMN "public"."ota_daily_infos"."break_fast_num" IS '早餐数量';

-- 入住人信息
ALTER TABLE "public"."ota_order_guests"
ADD COLUMN "nationality" varchar(50);

COMMENT ON COLUMN "public"."ota_order_guests"."nationality" IS '国籍';

CREATE TABLE "public"."fc_hotel_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"hotel_id" varchar(50) COLLATE "default",
"hotel_name" varchar(100) COLLATE "default",
"hotel_address" varchar(200) COLLATE "default",
"telephone" varchar(200) COLLATE "default",
"website_url" varchar(200) COLLATE "default",
"hotel_star" int2,
"city" varchar(100) COLLATE "default",
"fc_distinct" varchar(100) COLLATE "default",
"business" varchar(100) COLLATE "default",
CONSTRAINT "fc_hotel_info_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_hotel_info" OWNER TO "ota";

COMMENT ON COLUMN "public"."fc_hotel_info"."id" IS 'ID';

COMMENT ON COLUMN "public"."fc_hotel_info"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."fc_hotel_info"."updated_date" IS '更新时间';

COMMENT ON COLUMN "public"."fc_hotel_info"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_id" IS '天下房仓酒店ID';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_name" IS '酒店名称';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_address" IS '酒店地址';

COMMENT ON COLUMN "public"."fc_hotel_info"."telephone" IS '联系方式';

COMMENT ON COLUMN "public"."fc_hotel_info"."website_url" IS '网站地址';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_star" IS '酒店星级';

COMMENT ON COLUMN "public"."fc_hotel_info"."city" IS '所在城市';

COMMENT ON COLUMN "public"."fc_hotel_info"."fc_distinct" IS '行政区';

COMMENT ON COLUMN "public"."fc_hotel_info"."business" IS '商业区';



CREATE UNIQUE INDEX "hotel_id_idx" ON "public"."fc_hotel_info" USING btree (hotel_id);


CREATE TABLE "public"."fc_room_type_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"hotel_id" varchar(50) COLLATE "default",
"room_type_id" varchar(50) COLLATE "default",
"room_type_name" varchar(200) COLLATE "default",
"bed_type" varchar(100) COLLATE "default",
CONSTRAINT "fc_room_type_info_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_room_type_info" OWNER TO "ota";

COMMENT ON COLUMN "public"."fc_room_type_info"."id" IS 'ID';

COMMENT ON COLUMN "public"."fc_room_type_info"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."fc_room_type_info"."updated_date" IS '更新时间';

COMMENT ON COLUMN "public"."fc_room_type_info"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."fc_room_type_info"."hotel_id" IS '酒店ID';

COMMENT ON COLUMN "public"."fc_room_type_info"."room_type_id" IS '酒店房型ID';

COMMENT ON COLUMN "public"."fc_room_type_info"."room_type_name" IS '酒店名称';

COMMENT ON COLUMN "public"."fc_room_type_info"."bed_type" IS '床型';



CREATE UNIQUE INDEX "hotel_id_and_room_type_id_idx" ON "public"."fc_room_type_info" USING btree (hotel_id, room_type_id);





DROP TABLE IF EXISTS "public"."fc_area";
CREATE TABLE "public"."fc_area" (
"id" varchar(64) COLLATE "default" NOT NULL,
"city_code" varchar(30) COLLATE "default",
"area_name" varchar(40) COLLATE "default",
"area_code" varchar(40) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamptz(6),
CONSTRAINT "fc_area_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_area" OWNER TO "ota";
COMMENT ON COLUMN "public"."fc_area"."city_code" IS '城市code';
COMMENT ON COLUMN "public"."fc_area"."area_name" IS '区域名称';
COMMENT ON COLUMN "public"."fc_area"."area_code" IS '区域code';
CREATE INDEX "fc_area_city_code_area_name_idx" ON "public"."fc_area" USING btree (city_code, area_name);

DROP TABLE IF EXISTS "public"."fc_city";
CREATE TABLE "public"."fc_city" (
"id" varchar(64) COLLATE "default" NOT NULL,
"province_code" varchar(64) COLLATE "default",
"city_name" varchar(64) COLLATE "default",
"city_code" varchar(20) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
CONSTRAINT "fc_city_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_city" OWNER TO "ota";
COMMENT ON COLUMN "public"."fc_city"."province_code" IS '省份code';
COMMENT ON COLUMN "public"."fc_city"."city_name" IS '城市名称';
COMMENT ON COLUMN "public"."fc_city"."city_code" IS '城市code';
CREATE INDEX "fc_city_province_code_city_code_idx" ON "public"."fc_city" USING btree (province_code, city_code);

DROP TABLE IF EXISTS "public"."fc_province";
CREATE TABLE "public"."fc_province" (
"id" varchar(64) COLLATE "default" NOT NULL,
"province_name" varchar(64) COLLATE "default",
"province_code" varchar(30) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
CONSTRAINT "fc_province_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;
ALTER TABLE "public"."fc_province" OWNER TO "ota";
COMMENT ON COLUMN "public"."fc_province"."province_name" IS '省份名称';
COMMENT ON COLUMN "public"."fc_province"."province_code" IS '省份code';
CREATE INDEX "fc_province_province_code_idx" ON "public"."fc_province" USING btree (province_code);

DROP TABLE IF EXISTS "public"."fc_roomtype_fq";
CREATE TABLE "public"."fc_roomtype_fq" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" varchar(64) COLLATE "default",
"fc_hotel_id" varchar(64) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"ota_inn_ota_id" varchar(64) COLLATE "default",
"ota_info_id" varchar(64) COLLATE "default",
"fq_roomtype_id" varchar(64) COLLATE "default",
"fc_roomtype_id" varchar(64) COLLATE "default",
"room_area" numeric(6,2),
"fq_roomtype_name" varchar(64) COLLATE "default",
"fc_roomtype_name" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"rate_plan_id" varchar(64) COLLATE "default",
"sj" int2,
CONSTRAINT "fc_roomtype_fq_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_roomtype_fq" OWNER TO "ota";

COMMENT ON COLUMN "public"."fc_roomtype_fq"."inn_id" IS '客栈id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."fc_hotel_id" IS '房仓酒店id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."company_id" IS '公司id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."ota_inn_ota_id" IS '渠道关联id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."ota_info_id" IS '渠道id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."fq_roomtype_id" IS '番茄房型id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."fc_roomtype_id" IS '房仓房型id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."fq_roomtype_name" IS '番茄房型名称';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."fc_roomtype_name" IS '房仓房型名称';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."rate_plan_id" IS '价格计划id';

COMMENT ON COLUMN "public"."fc_roomtype_fq"."sj" IS '1 上架 0 下架 -1 没有上架';

CREATE INDEX "fc_roomtype_fq_inn_id_fc_hotel_id_company_id_ota_inn_ota_id_idx" ON "public"."fc_roomtype_fq" USING btree (inn_id, fc_hotel_id, company_id, ota_inn_ota_id, ota_info_id, fq_roomtype_id, fc_roomtype_id, rate_plan_id);

create SEQUENCE fc_rate_plan_seq start 10000;
DROP TABLE IF EXISTS "public"."fc_rate_plan";
CREATE TABLE "public"."fc_rate_plan" (
"id" varchar(64) COLLATE "default" NOT NULL,
"rate_plan_name" varchar(30) COLLATE "default",
"currency" varchar(10) COLLATE "default",
"pay_method" varchar(10) COLLATE "default",
"bed_type" varchar(30) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"company_id" varchar(64) COLLATE "default",
"deleted" int2,
"rate_plan_id" int2 DEFAULT nextval('fc_rate_plan_seq'::regclass),
CONSTRAINT "fc_rate_plan_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_rate_plan" OWNER TO "ota";

COMMENT ON COLUMN "public"."fc_rate_plan"."rate_plan_name" IS '名称';

COMMENT ON COLUMN "public"."fc_rate_plan"."currency" IS '币种';

COMMENT ON COLUMN "public"."fc_rate_plan"."pay_method" IS '支付类型';

COMMENT ON COLUMN "public"."fc_rate_plan"."bed_type" IS '床型';

COMMENT ON COLUMN "public"."fc_rate_plan"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."fc_rate_plan"."updated_date" IS '更新时间';