
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