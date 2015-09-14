
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