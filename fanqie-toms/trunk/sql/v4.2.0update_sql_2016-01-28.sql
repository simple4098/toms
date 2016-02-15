CREATE TABLE "public"."ota_exception_order" (
"id" varchar(64) NOT NULL,
"order_id" varchar(64),
"created_date" timestamp,
"updated_date" timestamp,
"modifer_person" varchar(64),
"modifer_status" varchar(100),
"deleted" int2,
PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

COMMENT ON COLUMN "public"."ota_exception_order"."order_id" IS '订单id';

COMMENT ON COLUMN "public"."ota_exception_order"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."ota_exception_order"."updated_date" IS '更新时间';

COMMENT ON COLUMN "public"."ota_exception_order"."modifer_person" IS '修改人id';

COMMENT ON COLUMN "public"."ota_exception_order"."modifer_status" IS '订单修改后的状态';

COMMENT ON COLUMN "public"."ota_exception_order"."deleted" IS '是否删除';

