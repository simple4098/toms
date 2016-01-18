
CREATE TABLE "public"."jw_inn_room_mapping" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int8,
"room_type_id" int8,
"room_type_name" varchar(30) COLLATE "default",
"inn_code" varchar(30) COLLATE "default",
"room_type_id_code" varchar(30) COLLATE "default",
"rate_plan_code" varchar(30) COLLATE "default",
"create_date" timestamp(6),
"update_date" timestamp(6),
"deleted" int2,
"sj" int2,
"company_id" varchar(64) COLLATE "default",
"currency_code" varchar(20) COLLATE "default",
"ota_info_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."room_type_name" IS '房型名称';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."inn_code" IS '客栈id(otaid_innid)';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."room_type_id_code" IS '房型id(otaid_房型id)';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."rate_plan_code" IS '价格计划代码';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."sj" IS '是否上架 (0 下架 1 上架)';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."currency_code" IS '货币代码';
COMMENT ON COLUMN "public"."jw_inn_room_mapping"."ota_info_id" IS '渠道id';


CREATE INDEX "jw_inn_room_mapping_inn_id_room_type_id_inn_code_room_type__idx" ON "public"."jw_inn_room_mapping" USING btree (inn_id, room_type_id, inn_code, room_type_id_code);


ALTER TABLE "public"."jw_inn_room_mapping" ADD PRIMARY KEY ("id");


INSERT INTO  ota_info(id,ota_info,ota_type,deleted,sort)  VALUES ('4', '众荟', 'ZH', 0, 4);