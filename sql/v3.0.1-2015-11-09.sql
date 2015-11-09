ALTER TABLE "public"."otainfo_company_ref" ADD COLUMN "used_price_model" varchar(100);




CREATE TABLE "public"."ota_commission_percent" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"commission_percent" numeric(3),
"created_date" date,
"updated_date" date,
"deleted" int2,
"sjia_model" varchar(20) COLLATE "default",
"ota_id" int8,
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