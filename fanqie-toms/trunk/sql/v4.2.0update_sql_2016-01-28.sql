DROP TABLE IF EXISTS "public"."ota_exception_order";
CREATE TABLE "public"."ota_exception_order" (
"id" varchar(64) COLLATE "default" NOT NULL,
"order_id" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"modifier_person" varchar(64) COLLATE "default",
"modifier_status" varchar(100) COLLATE "default",
"deleted" int2,
"fee_status" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_exception_order"."order_id" IS '订单id';
COMMENT ON COLUMN "public"."ota_exception_order"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ota_exception_order"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."ota_exception_order"."modifier_person" IS '修改人id';
COMMENT ON COLUMN "public"."ota_exception_order"."modifier_status" IS '订单修改后的状态';
COMMENT ON COLUMN "public"."ota_exception_order"."deleted" IS '是否删除';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table ota_exception_order
-- ----------------------------
ALTER TABLE "public"."ota_exception_order" ADD PRIMARY KEY ("id");
