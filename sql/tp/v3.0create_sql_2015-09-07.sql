CREATE TABLE "public"."order_operation_record" (
"id" varchar(64) COLLATE "default" NOT NULL,
"order_id" varchar(64) COLLATE "default",
"before_status" varchar(50) COLLATE "default",
"after_status" varchar(50) COLLATE "default",
"modify_person" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"content" varchar(255) COLLATE "default",
CONSTRAINT "order_operation_record_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."order_operation_record" OWNER TO "ota";

COMMENT ON COLUMN "public"."order_operation_record"."id" IS 'ID';

COMMENT ON COLUMN "public"."order_operation_record"."order_id" IS '订单ID';

COMMENT ON COLUMN "public"."order_operation_record"."before_status" IS '订单修改之前状态';

COMMENT ON COLUMN "public"."order_operation_record"."after_status" IS '订单修改之后状态';

COMMENT ON COLUMN "public"."order_operation_record"."modify_person" IS '修改人ID';

COMMENT ON COLUMN "public"."order_operation_record"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."order_operation_record"."content" IS '备注';