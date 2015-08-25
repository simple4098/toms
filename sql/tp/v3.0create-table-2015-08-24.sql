--订单配置表
DROP TABLE IF EXISTS "public"."order_config";
CREATE TABLE "public"."order_config" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int8,
"company_id" varchar(64) COLLATE "default",
"ota_info_id" varchar(64) COLLATE "default",
"status" int2,
"updated_date" timestamp(6),
"deleted" int2,
"modifier_id" varchar COLLATE "default",
CONSTRAINT "order_config_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;
ALTER TABLE "public"."order_config" OWNER TO "ota";
COMMENT ON COLUMN "public"."order_config"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."order_config"."ota_info_id" IS '渠道id';
COMMENT ON COLUMN "public"."order_config"."status" IS '是否手动(0:自动;1:手动; 默认自动)';
COMMENT ON COLUMN "public"."order_config"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."order_config"."modifier_id" IS '修改人id';
CREATE INDEX "order_config_inn_id_company_id_otainfo_id_idx" ON "public"."order_config" USING btree (inn_id, company_id, ota_info_id);

--客栈房型特殊价
DROP TABLE IF EXISTS "public"."ota_room_price";
CREATE TABLE "public"."ota_room_price" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"inn_id" int8,
"room_type_id" int8,
"start_date" date,
"end_date" date,
"value" numeric(5,1),
"ota_info_id" varchar(64) COLLATE "default",
"account_id" int8,
"modifier_id" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"deleted" int2,
"room_type_name" varchar(60) COLLATE "default",
CONSTRAINT "ota_room_price_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;
ALTER TABLE "public"."ota_room_price" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_room_price"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_room_price"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."ota_room_price"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."ota_room_price"."start_date" IS '特殊价格开始时间';
COMMENT ON COLUMN "public"."ota_room_price"."end_date" IS '特殊价格结束时间';
COMMENT ON COLUMN "public"."ota_room_price"."ota_info_id" IS '渠道id';
COMMENT ON COLUMN "public"."ota_room_price"."modifier_id" IS '修改人id';
COMMENT ON COLUMN "public"."ota_room_price"."room_type_name" IS '房型名称';

--公司开通渠道关联表
DROP TABLE IF EXISTS "public"."otainfo_company_ref";
CREATE TABLE "public"."otainfo_company_ref" (
"id" varchar(64) COLLATE "default" NOT NULL,
"ota_info_id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"appkey" varchar(30) COLLATE "default",
"appsecret" varchar(100) COLLATE "default",
"sessionkey" varchar(300) COLLATE "default",
"status" int2,
"expired_time" timestamp(6),
CONSTRAINT "otainfo_company_ref_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."otainfo_company_ref" OWNER TO "ota";

COMMENT ON COLUMN "public"."otainfo_company_ref"."ota_info_id" IS '渠道id(ota_info的主键)';

COMMENT ON COLUMN "public"."otainfo_company_ref"."status" IS '开关(1:开启，0:关闭)';

COMMENT ON COLUMN "public"."otainfo_company_ref"."expired_time" IS '失效时间';



CREATE INDEX "otainfo_company_ref_ota_info_id_company_id_idx" ON "public"."otainfo_company_ref" USING btree (ota_info_id, company_id);
