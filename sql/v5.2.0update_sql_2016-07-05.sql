ALTER TABLE "public"."other_consumer_function"
ADD COLUMN "myself_channel_status" bool;

COMMENT ON COLUMN "public"."other_consumer_function"."myself_channel_status" IS '是否开启自定义渠道';

ALTER TABLE "public"."other_consumer_function"
ADD COLUMN "pms_channel_name_status" bool;

COMMENT ON COLUMN "public"."other_consumer_function"."pms_channel_name_status" IS 'pms客栈名称设置';

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "json_data" varchar(255);
COMMENT ON COLUMN "public"."ota_toms_order"."json_data" IS 'json数据';
DROP TABLE IF EXISTS "public"."toms_myself_channel";
CREATE TABLE "public"."toms_myself_channel" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"creator_id" varchar(64) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"channel_name" varchar(100) COLLATE "default",
"channel_code" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."toms_myself_channel"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."toms_myself_channel"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."toms_myself_channel"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."toms_myself_channel"."creator_id" IS '创建者id';
COMMENT ON COLUMN "public"."toms_myself_channel"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."toms_myself_channel"."channel_name" IS '自定义渠道名称';
COMMENT ON COLUMN "public"."toms_myself_channel"."channel_code" IS '自定义渠道code';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table toms_myself_channel
-- ----------------------------
ALTER TABLE "public"."toms_myself_channel" ADD PRIMARY KEY ("id");




DROP TABLE IF EXISTS "public"."toms_pms_channel_name";
CREATE TABLE "public"."toms_pms_channel_name" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"creator_id" varchar(64) COLLATE "default",
"deleted" int2,
"pms_channel_name" varchar(50) COLLATE "default",
"company_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."toms_pms_channel_name"."id" IS 'ID';
COMMENT ON COLUMN "public"."toms_pms_channel_name"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."toms_pms_channel_name"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."toms_pms_channel_name"."creator_id" IS '创建者id';
COMMENT ON COLUMN "public"."toms_pms_channel_name"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."toms_pms_channel_name"."pms_channel_name" IS 'pms渠道名';
COMMENT ON COLUMN "public"."toms_pms_channel_name"."company_id" IS '公司id';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table toms_pms_channel_name
-- ----------------------------
ALTER TABLE "public"."toms_pms_channel_name" ADD PRIMARY KEY ("id");


-- 新增权限控制url
/personality/info/myself_channel_page
/personality/info/pms_channel_name_page
/personality/info/otherConsumer
-- 修改其他消费
/personality/info/test
