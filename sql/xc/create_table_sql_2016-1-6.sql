-- 新增携程基础酒店信息表
-- ----------------------------
-- Table structure for ctrip_hotel_info
-- ----------------------------
create SEQUENCE xc_parent_hotel_room2_id_seq start 100;
CREATE TABLE "public"."ctrip_parent_hotel" (
"id" varchar(64) COLLATE "default" DEFAULT nextval('xc_parent_hotel_room2_id_seq'::regclass) NOT NULL,
"hotel_name" varchar(30) COLLATE "default",
"child_hotel_id" varchar(40) COLLATE "default",
"parent_hotel_id" varchar(40) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"country_name" varchar(100) COLLATE "default",
"city_name" varchar(100) COLLATE "default",
"address" varchar(200) COLLATE "default",
"telephone" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ctrip_parent_hotel"."country_name" IS '国家名称';
COMMENT ON COLUMN "public"."ctrip_parent_hotel"."city_name" IS '城市名称';
COMMENT ON COLUMN "public"."ctrip_parent_hotel"."address" IS '详细地址';
COMMENT ON COLUMN "public"."ctrip_parent_hotel"."telephone" IS '联系方式';
CREATE INDEX "xc_parent_hotel_hotel_name_child_hotel_id_parent_hotel_id_idx" ON "public"."ctrip_parent_hotel" USING btree (hotel_name, child_hotel_id, parent_hotel_id);
ALTER TABLE "public"."ctrip_parent_hotel" ADD PRIMARY KEY ("id");


create SEQUENCE xc_parent_hotel_room2_id_seq start 100;
CREATE TABLE "public"."ctrip_parent_room" (
"id" varchar(64) COLLATE "default" DEFAULT nextval('xc_parent_hotel_room2_id_seq'::regclass) NOT NULL,
"ctrip_prent_hotel_id" varchar(60) COLLATE "default",
"room_type_name" varchar(30) COLLATE "default",
"room_type_id" varchar(20) COLLATE "default",
"currency" varchar(20) COLLATE "default",
"bed_type" varchar(20) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"
check_in_num" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ctrip_parent_room"."ctrip_prent_hotel_id" IS '携程母酒店id';
COMMENT ON COLUMN "public"."ctrip_parent_room"."
check_in_num" IS '入住人数';

CREATE INDEX "xc_parent_room_xc_prent_hotel_id_room_type_name_room_type_i_idx" ON "public"."ctrip_parent_room" USING btree (ctrip_prent_hotel_id, room_type_name, room_type_id);

ALTER TABLE "public"."ctrip_parent_room" ADD PRIMARY KEY ("id");



create SEQUENCE xc_roomtype_fq_id_seq start 100;
CREATE TABLE "public"."xc_roomtype_fq" (
"id" varchar(32) COLLATE "default" DEFAULT nextval('xc_roomtype_fq_id_seq'::regclass) NOT NULL,
"inn_id" varchar(64) COLLATE "default",
"xc_child_hotel_id" varchar(64) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"fq_roomtype_id" varchar(64) COLLATE "default",
"xc_child_roomtype_id" varchar(64) COLLATE "default",
"room_area" numeric(6,2),
"fq_roomtype_name" varchar(64) COLLATE "default",
"xc_roomtype_name" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"rate_plan_code" varchar(64) COLLATE "default",
"sj" int2,
"bed_num" varchar(16) COLLATE "default",
"bed_len" varchar(16) COLLATE "default",
"bed_wid" varchar(16) COLLATE "default",
"rate_plan_code_name" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;
CREATE INDEX "xc_roomtype_fq_inn_id_xc_child_hotel_id_company_id_fq_roomt_idx" ON "public"."xc_roomtype_fq" USING btree (inn_id, xc_child_hotel_id, company_id, fq_roomtype_id, xc_child_roomtype_id);
ALTER TABLE "public"."xc_roomtype_fq" ADD PRIMARY KEY ("id");


