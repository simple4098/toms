
DROP TABLE IF EXISTS "public"."tb_base_config";
CREATE TABLE "public"."tb_base_config" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"company_id" varchar(64) COLLATE "default",
"receipt_type" varchar(30) COLLATE "default",
"receipt_other_type_desc" varchar(30) COLLATE "default",
"rate_plan_name" varchar(15) COLLATE "default",
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."tb_base_config"."company_id" IS '企业id';
COMMENT ON COLUMN "public"."tb_base_config"."receipt_type" IS '发票类型';
COMMENT ON COLUMN "public"."tb_base_config"."receipt_other_type_desc" IS '其他发票说明';
COMMENT ON COLUMN "public"."tb_base_config"."rate_plan_name" IS '价格计划名字';
COMMENT ON COLUMN "public"."tb_base_config"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for tb_breakfast_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_breakfast_config";
CREATE TABLE "public"."tb_breakfast_config" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"company_id" varchar(64) COLLATE "default",
"breakfast_type" varchar(30) COLLATE "default",
"breakfast_value" int2,
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."tb_breakfast_config"."company_id" IS '企业id';
COMMENT ON COLUMN "public"."tb_breakfast_config"."breakfast_value" IS '含早类型 0：不含早1：含单早2：含双早N：含N早（1-99可选）';
COMMENT ON COLUMN "public"."tb_breakfast_config"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for tb_room_td_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_room_td_config";
CREATE TABLE "public"."tb_room_td_config" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"inn_id" int8,
"room_type_id" int8,
"obj_type" varchar(30) COLLATE "default",
"obj_id" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."tb_room_td_config"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."tb_room_td_config"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."tb_room_td_config"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."tb_room_td_config"."obj_type" IS '关联类型';
COMMENT ON COLUMN "public"."tb_room_td_config"."obj_id" IS '对应id';

-- ----------------------------
-- Table structure for tb_td_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_td_config";
CREATE TABLE "public"."tb_td_config" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"cancel_policy" varchar(30) COLLATE "default",
"cancel_type" varchar(30) COLLATE "default",
"deleted" int2 DEFAULT 0,
"company_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."tb_td_config"."cancel_policy" IS '退订政策';
COMMENT ON COLUMN "public"."tb_td_config"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."tb_td_config"."company_id" IS '企业id';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table tb_base_config
-- ----------------------------
ALTER TABLE "public"."tb_base_config" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_breakfast_config
-- ----------------------------
ALTER TABLE "public"."tb_breakfast_config" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table tb_room_td_config
-- ----------------------------
CREATE INDEX "room_tb_config_company_id_inn_id_room_type_id_obj_type_obj__idx" ON "public"."tb_room_td_config" USING btree (company_id, inn_id, room_type_id, obj_type, obj_id);

-- ----------------------------
-- Primary Key structure for table tb_room_td_config
-- ----------------------------
ALTER TABLE "public"."tb_room_td_config" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_td_config
-- ----------------------------
ALTER TABLE "public"."tb_td_config" ADD PRIMARY KEY ("id");
