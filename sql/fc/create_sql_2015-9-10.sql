CREATE TABLE "public"."fc_hotel_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"hotel_id" varchar(50) COLLATE "default",
"hotel_name" varchar(100) COLLATE "default",
"hotel_address" varchar(200) COLLATE "default",
"telephone" varchar(200) COLLATE "default",
"website_url" varchar(200) COLLATE "default",
"hotel_star" int2,
"city" varchar(100) COLLATE "default",
"fc_distinct" varchar(100) COLLATE "default",
"business" varchar(100) COLLATE "default",
CONSTRAINT "fc_hotel_info_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_hotel_info" OWNER TO "ota";

COMMENT ON COLUMN "public"."fc_hotel_info"."id" IS 'ID';

COMMENT ON COLUMN "public"."fc_hotel_info"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."fc_hotel_info"."updated_date" IS '更新时间';

COMMENT ON COLUMN "public"."fc_hotel_info"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_id" IS '天下房仓酒店ID';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_name" IS '酒店名称';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_address" IS '酒店地址';

COMMENT ON COLUMN "public"."fc_hotel_info"."telephone" IS '联系方式';

COMMENT ON COLUMN "public"."fc_hotel_info"."website_url" IS '网站地址';

COMMENT ON COLUMN "public"."fc_hotel_info"."hotel_star" IS '酒店星级';

COMMENT ON COLUMN "public"."fc_hotel_info"."city" IS '所在城市';

COMMENT ON COLUMN "public"."fc_hotel_info"."fc_distinct" IS '行政区';

COMMENT ON COLUMN "public"."fc_hotel_info"."business" IS '商业区';



CREATE UNIQUE INDEX "hotel_id_idx" ON "public"."fc_hotel_info" USING btree (hotel_id);


CREATE TABLE "public"."fc_room_type_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"hotel_id" varchar(50) COLLATE "default",
"room_type_id" varchar(50) COLLATE "default",
"room_type_name" varchar(200) COLLATE "default",
"bed_type" varchar(100) COLLATE "default",
CONSTRAINT "fc_room_type_info_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."fc_room_type_info" OWNER TO "ota";

COMMENT ON COLUMN "public"."fc_room_type_info"."id" IS 'ID';

COMMENT ON COLUMN "public"."fc_room_type_info"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."fc_room_type_info"."updated_date" IS '更新时间';

COMMENT ON COLUMN "public"."fc_room_type_info"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."fc_room_type_info"."hotel_id" IS '酒店ID';

COMMENT ON COLUMN "public"."fc_room_type_info"."room_type_id" IS '酒店房型ID';

COMMENT ON COLUMN "public"."fc_room_type_info"."room_type_name" IS '酒店名称';

COMMENT ON COLUMN "public"."fc_room_type_info"."bed_type" IS '床型';



CREATE UNIQUE INDEX "hotel_id_and_room_type_id_idx" ON "public"."fc_room_type_info" USING btree (hotel_id, room_type_id);