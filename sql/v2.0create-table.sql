--增加company表字段
alter table company add column appkey varchar(64);
alter table company add column appsecret varchar(64);
alter table company add column sessionkey varchar(64);
alter table company add column expired_time timestamp(64);
--增加bang_inn表字段
alter table bang_inn add column deleted int2;
alter table bang_inn add column ota_wg_id varchar(64);
alter table bang_inn add column account_id_di int4;
update bang_inn set deleted=0;


CREATE TABLE  ota_inn_ota (
"id" varchar(64) COLLATE "default" NOT NULL,
"wg_hid" varchar(64) COLLATE "default",
"commission_percent" numeric(3,2),
"company_id" varchar(64) COLLATE "default",
"alias_inn_name" varchar(30) COLLATE "default",
"ota_id" varchar(64) COLLATE "default",
"price_model" varchar(50) COLLATE "default",
"sjia_model" varchar COLLATE "default",
"deleted" int2,
CONSTRAINT "pk_ota_inn_ota" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_inn_ota" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_inn_ota"."wg_hid" IS 'tp店酒店id';
COMMENT ON COLUMN "public"."ota_inn_ota"."commission_percent" IS '佣金比例';
COMMENT ON COLUMN "public"."ota_inn_ota"."company_id" IS '企业id';
COMMENT ON COLUMN "public"."ota_inn_ota"."alias_inn_name" IS '客栈别名';
COMMENT ON COLUMN "public"."ota_inn_ota"."ota_id" IS 'OTA id';
COMMENT ON COLUMN "public"."ota_inn_ota"."price_model" IS '价格模式';
COMMENT ON COLUMN "public"."ota_inn_ota"."sjia_model" IS '上架模式';
COMMENT ON COLUMN "public"."ota_inn_ota"."deleted" IS '是否删除';



CREATE TABLE  ota_price_model (
"id" varchar(64) COLLATE "default" NOT NULL,
"price_model" varchar(20) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"price_model_value" numeric(6,2),
"created_date" timestamp(6),
"deleted" int2,
CONSTRAINT "pk_ota_price_model" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_price_model" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_price_model"."price_model" IS '价格模式';
COMMENT ON COLUMN "public"."ota_price_model"."ota_wg_id" IS 'ota_inn_ota 表id';
COMMENT ON COLUMN "public"."ota_price_model"."price_model_value" IS '价格模式值';
COMMENT ON COLUMN "public"."ota_price_model"."deleted" IS '是否删除';
CREATE INDEX "ota_price_model_ota_wg_id_idx" ON "public"."ota_price_model" USING btree (ota_wg_id);


CREATE TABLE ota_bang_inn_room (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int8,
"room_type_id" int8,
"room_type_name" varchar(25) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"rid" varchar(30) COLLATE "default",
"price_model_id" varchar(64) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
CONSTRAINT "pk_ota_bang_inn_room" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_bang_inn_room" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_bang_inn_room"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."room_type_name" IS '房型名字';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."rid" IS 'TP点房型id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."price_model_id" IS '价格模式id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."ota_wg_id" IS 'ota_inn_ota  id';
CREATE INDEX "ota_bang_inn_room_company_id_ota_wg_id_idx" ON "public"."ota_bang_inn_room" USING btree (company_id, ota_wg_id);


CREATE TABLE ota_info (
"id" varchar COLLATE "default" NOT NULL,
"ota_info" varchar(20) COLLATE "default",
"type" varchar(10) COLLATE "default",
"deleted" int2,
CONSTRAINT "pk_ota_info" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_info" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_info"."ota_info" IS 'OTA 描述';
COMMENT ON COLUMN "public"."ota_info"."type" IS 'OTA 类型';
COMMENT ON COLUMN "public"."ota_info"."deleted" IS '是否删除';


CREATE TABLE  ota_inn_roomtype_goods (
"id" varchar(60) COLLATE "default" NOT NULL,
"room_type_id" varchar(30) COLLATE "default",
"inn_id" varchar(30) COLLATE "default",
"rpid" varchar(30) COLLATE "default",
"gid" varchar(30) COLLATE "default",
"company_id" varchar(30) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"rid" varchar(30) COLLATE "default",
CONSTRAINT "pk_ota_inn_roomtype_goods" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_inn_roomtype_goods" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_inn_roomtype_goods"."rid" IS 'tp房型id';
CREATE INDEX "ota_inn_roomtype_goods_rid_idx" ON "public"."ota_inn_roomtype_goods" USING btree (rid);