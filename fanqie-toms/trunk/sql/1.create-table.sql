/*
Navicat PGSQL Data Transfer

Source Server         : 192.168.1.158
Source Server Version : 90113
Source Host           : 192.168.1.158:5432
Source Database       : dc
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90113
File Encoding         : 65001

Date: 2015-06-05 15:01:52
*/


-- ----------------------------
-- Table structure for bang_inn
-- ----------------------------
DROP TABLE IF EXISTS "public"."bang_inn";
CREATE TABLE "public"."bang_inn" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"bang_date" date,
"lose_date" date,
"start_date" date,
"inn_name" varchar(50) COLLATE "default",
"code" varchar(50) COLLATE "default",
"inn_label_id" varchar(64) COLLATE "default",
"mobile" varchar(20) COLLATE "default",
"inn_code" varchar(50) COLLATE "default",
"user_id" varchar(64) COLLATE "default",
"type" varchar(10) COLLATE "default",
"inn_id" int4,
"account_id" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."bang_inn"."id" IS 'ID';
COMMENT ON COLUMN "public"."bang_inn"."company_id" IS '所属公司ID';
COMMENT ON COLUMN "public"."bang_inn"."bang_date" IS '绑定时间';
COMMENT ON COLUMN "public"."bang_inn"."lose_date" IS '失效时间';
COMMENT ON COLUMN "public"."bang_inn"."start_date" IS '发起时间';
COMMENT ON COLUMN "public"."bang_inn"."inn_name" IS '客栈名称';
COMMENT ON COLUMN "public"."bang_inn"."code" IS '编号';
COMMENT ON COLUMN "public"."bang_inn"."inn_label_id" IS '客栈标签ID';
COMMENT ON COLUMN "public"."bang_inn"."mobile" IS '客栈电话';
COMMENT ON COLUMN "public"."bang_inn"."inn_code" IS '客栈账户';
COMMENT ON COLUMN "public"."bang_inn"."user_id" IS '管理员ID';
COMMENT ON COLUMN "public"."bang_inn"."type" IS '绑定类型（状态）';
COMMENT ON COLUMN "public"."bang_inn"."inn_id" IS '客栈名称';
COMMENT ON COLUMN "public"."bang_inn"."account_id" IS '客栈对应的accountId';

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS "public"."company";
CREATE TABLE "public"."company" (
"id" varchar(64) COLLATE "default" NOT NULL,
"creator_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date,
"modifier_id" varchar(64) COLLATE "default",
"version" float4,
"company_name" varchar(100) COLLATE "default",
"deleted" int2 DEFAULT 0,
"company_code" varchar(50) COLLATE "default",
"type" int2,
"ota_id" int4,
"user_account" varchar(50) COLLATE "default",
"user_password" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."company"."id" IS 'ID';
COMMENT ON COLUMN "public"."company"."creator_id" IS '创建者ID';
COMMENT ON COLUMN "public"."company"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."company"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."company"."modifier_id" IS '修改者ID';
COMMENT ON COLUMN "public"."company"."version" IS '版本号';
COMMENT ON COLUMN "public"."company"."company_name" IS '公司名称';
COMMENT ON COLUMN "public"."company"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."company"."company_code" IS '公司唯一标识码';
COMMENT ON COLUMN "public"."company"."ota_id" IS '第三方公司渠道id';
COMMENT ON COLUMN "public"."company"."user_account" IS 'oms 给第三方开通的账号';
COMMENT ON COLUMN "public"."company"."user_password" IS 'oms 给第三方开通的账号密码';

-- ----------------------------
-- Table structure for company_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."company_permission";
CREATE TABLE "public"."company_permission" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"permission_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."company_permission"."id" IS 'ID';
COMMENT ON COLUMN "public"."company_permission"."company_id" IS '公司ID';
COMMENT ON COLUMN "public"."company_permission"."permission_id" IS '权限ID';
COMMENT ON COLUMN "public"."company_permission"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."company_permission"."updated_date" IS '更新时间';

-- ----------------------------
-- Table structure for dc_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."dc_user";
CREATE TABLE "public"."dc_user" (
"id" int4 NOT NULL,
"name" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for inn_active
-- ----------------------------
DROP TABLE IF EXISTS "public"."inn_active";
CREATE TABLE "public"."inn_active" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int4,
"inn_name" varchar(60) COLLATE "default",
"mobile" varchar(20) COLLATE "default",
"create_date" date,
"order_num" int4,
"check_in_num" int4,
"check_out_num" int4,
"msg_num" int4,
"operate_num" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."inn_active"."id" IS 'ID';
COMMENT ON COLUMN "public"."inn_active"."inn_id" IS '客栈ID';
COMMENT ON COLUMN "public"."inn_active"."inn_name" IS '客栈名称';
COMMENT ON COLUMN "public"."inn_active"."mobile" IS '客栈联系方式';
COMMENT ON COLUMN "public"."inn_active"."create_date" IS '统计时间哪一天';
COMMENT ON COLUMN "public"."inn_active"."order_num" IS '订单数';
COMMENT ON COLUMN "public"."inn_active"."check_in_num" IS '入住数';
COMMENT ON COLUMN "public"."inn_active"."check_out_num" IS '退房数';
COMMENT ON COLUMN "public"."inn_active"."msg_num" IS '短息数';
COMMENT ON COLUMN "public"."inn_active"."operate_num" IS '操作数';

-- ----------------------------
-- Table structure for inn_customer
-- ----------------------------
DROP TABLE IF EXISTS "public"."inn_customer";
CREATE TABLE "public"."inn_customer" (
"id" varchar(64) COLLATE "default" NOT NULL,
"num" int4,
"province" varchar(15) COLLATE "default",
"city" varchar(10) COLLATE "default",
"percent" varchar(5) COLLATE "default",
"inn_id" int4,
"create_date" date
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."inn_customer"."id" IS 'ID';
COMMENT ON COLUMN "public"."inn_customer"."num" IS '客户数';
COMMENT ON COLUMN "public"."inn_customer"."province" IS '所在省份';
COMMENT ON COLUMN "public"."inn_customer"."city" IS '所在城市';
COMMENT ON COLUMN "public"."inn_customer"."percent" IS '所占比例';
COMMENT ON COLUMN "public"."inn_customer"."inn_id" IS '客栈ID';
COMMENT ON COLUMN "public"."inn_customer"."create_date" IS '创建时间';

-- ----------------------------
-- Table structure for inn_label
-- ----------------------------
DROP TABLE IF EXISTS "public"."inn_label";
CREATE TABLE "public"."inn_label" (
"id" varchar(64) COLLATE "default" NOT NULL,
"creator_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date,
"modifier_id" varchar(64) COLLATE "default",
"version" float4,
"label_name" varchar(30) COLLATE "default",
"indexed" int2,
"company_id" varchar(64) COLLATE "default",
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."inn_label"."id" IS 'ID';
COMMENT ON COLUMN "public"."inn_label"."creator_id" IS '创建者ID';
COMMENT ON COLUMN "public"."inn_label"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."inn_label"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."inn_label"."modifier_id" IS '修改者ID';
COMMENT ON COLUMN "public"."inn_label"."version" IS '版本号';
COMMENT ON COLUMN "public"."inn_label"."label_name" IS '标签名称';
COMMENT ON COLUMN "public"."inn_label"."indexed" IS '排序字段';
COMMENT ON COLUMN "public"."inn_label"."company_id" IS '所属公司ID';
COMMENT ON COLUMN "public"."inn_label"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for notice_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."notice_template";
CREATE TABLE "public"."notice_template" (
"id" varchar(64) COLLATE "default" NOT NULL,
"creator_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date,
"modifier_id" varchar(64) COLLATE "default",
"version" float4,
"notice_title" varchar(100) COLLATE "default",
"notice_content" varchar(255) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."notice_template"."id" IS 'ID';
COMMENT ON COLUMN "public"."notice_template"."creator_id" IS '创建者ID';
COMMENT ON COLUMN "public"."notice_template"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."notice_template"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."notice_template"."modifier_id" IS '修改者ID';
COMMENT ON COLUMN "public"."notice_template"."version" IS '版本号';
COMMENT ON COLUMN "public"."notice_template"."notice_title" IS '标题';
COMMENT ON COLUMN "public"."notice_template"."notice_content" IS '内容';
COMMENT ON COLUMN "public"."notice_template"."company_id" IS '所属公司ID';
COMMENT ON COLUMN "public"."notice_template"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for operate_trend
-- ----------------------------
DROP TABLE IF EXISTS "public"."operate_trend";
CREATE TABLE "public"."operate_trend" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int4,
"total_income" int4,
"total_room_num" int4,
"real_live_num" int4,
"empty_room_num" int4,
"avg_price" numeric(10,2),
"live_percent" numeric(5,2),
"inn_code" varchar(20) COLLATE "default",
"mobile" varchar(20) COLLATE "default",
"create_date" date,
"inn_name" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."operate_trend"."id" IS 'ID';
COMMENT ON COLUMN "public"."operate_trend"."inn_id" IS '客栈ID';
COMMENT ON COLUMN "public"."operate_trend"."total_income" IS '营业收入';
COMMENT ON COLUMN "public"."operate_trend"."total_room_num" IS '房间总数';
COMMENT ON COLUMN "public"."operate_trend"."real_live_num" IS '实住房间数';
COMMENT ON COLUMN "public"."operate_trend"."empty_room_num" IS '空置房间数';
COMMENT ON COLUMN "public"."operate_trend"."avg_price" IS '房间均价';
COMMENT ON COLUMN "public"."operate_trend"."live_percent" IS '入住率';
COMMENT ON COLUMN "public"."operate_trend"."inn_code" IS '客栈账号';
COMMENT ON COLUMN "public"."operate_trend"."mobile" IS '客栈联系方式';
COMMENT ON COLUMN "public"."operate_trend"."create_date" IS '创建时间（统计那个时间段的数据）';

-- ----------------------------
-- Table structure for order_source
-- ----------------------------
DROP TABLE IF EXISTS "public"."order_source";
CREATE TABLE "public"."order_source" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int4,
"from_name" varchar(30) COLLATE "default",
"live_num" int4,
"income" numeric(8,2),
"create_date" date,
"from_id" int4,
"order_num" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."order_source"."id" IS 'ID';
COMMENT ON COLUMN "public"."order_source"."inn_id" IS '客栈ID';
COMMENT ON COLUMN "public"."order_source"."from_name" IS '订单类型';
COMMENT ON COLUMN "public"."order_source"."live_num" IS '订单间夜数';
COMMENT ON COLUMN "public"."order_source"."income" IS '订单收入';
COMMENT ON COLUMN "public"."order_source"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."order_source"."from_id" IS '渠道id';
COMMENT ON COLUMN "public"."order_source"."order_num" IS '订单数';

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."permission";
CREATE TABLE "public"."permission" (
"id" varchar(64) COLLATE "default" NOT NULL,
"creator_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date,
"modifier_id" varchar(64) COLLATE "default",
"version" float4,
"permission_name" varchar(50) COLLATE "default",
"url" varchar(64) COLLATE "default",
"parent_id" varchar(64) COLLATE "default",
"indexed" int2,
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."permission"."id" IS 'ID';
COMMENT ON COLUMN "public"."permission"."creator_id" IS '创建者ID';
COMMENT ON COLUMN "public"."permission"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."permission"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."permission"."modifier_id" IS '修改人ID';
COMMENT ON COLUMN "public"."permission"."version" IS '版本号';
COMMENT ON COLUMN "public"."permission"."permission_name" IS '权限名称';
COMMENT ON COLUMN "public"."permission"."url" IS '权限链接';
COMMENT ON COLUMN "public"."permission"."parent_id" IS '权限父ID';
COMMENT ON COLUMN "public"."permission"."indexed" IS '排序字段';
COMMENT ON COLUMN "public"."permission"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS "public"."role";
CREATE TABLE "public"."role" (
"id" varchar(64) COLLATE "default" NOT NULL,
"creator_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date,
"role_desc" varchar(64) COLLATE "default",
"role_name" varchar(30) COLLATE "default",
"role_key" varchar(30) COLLATE "default",
"indexed" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."role"."id" IS 'ID';
COMMENT ON COLUMN "public"."role"."creator_id" IS '创建者ID';
COMMENT ON COLUMN "public"."role"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."role"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."role"."role_desc" IS '角色描述';
COMMENT ON COLUMN "public"."role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."role"."role_key" IS '角色KEY';
COMMENT ON COLUMN "public"."role"."indexed" IS '排序字段';

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."role_permission";
CREATE TABLE "public"."role_permission" (
"id" varchar(64) COLLATE "default" NOT NULL,
"role_id" varchar(64) COLLATE "default",
"permission_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."role_permission"."id" IS 'ID';
COMMENT ON COLUMN "public"."role_permission"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."role_permission"."permission_id" IS '权限ID';
COMMENT ON COLUMN "public"."role_permission"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."role_permission"."updated_date" IS '更新时间';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_info";
CREATE TABLE "public"."user_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"creator_id" varchar(64) COLLATE "default",
"created_date" date,
"updated_date" date,
"modifier_id" varchar(64) COLLATE "default",
"version" float4,
"user_type" varchar(30) COLLATE "default",
"login_name" varchar(50) COLLATE "default",
"telephone" varchar(30) COLLATE "default",
"user_name" varchar(30) COLLATE "default",
"password" varchar(64) COLLATE "default",
"data_permission" char(32) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"role_id" varchar(64) COLLATE "default",
"deleted" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."user_info"."id" IS 'ID';
COMMENT ON COLUMN "public"."user_info"."creator_id" IS '创建人ID';
COMMENT ON COLUMN "public"."user_info"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."user_info"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."user_info"."modifier_id" IS '最后修改者ID';
COMMENT ON COLUMN "public"."user_info"."version" IS '版本号';
COMMENT ON COLUMN "public"."user_info"."user_type" IS '用户类型';
COMMENT ON COLUMN "public"."user_info"."login_name" IS '登陆账号';
COMMENT ON COLUMN "public"."user_info"."telephone" IS '电话号码';
COMMENT ON COLUMN "public"."user_info"."user_name" IS '用户姓名';
COMMENT ON COLUMN "public"."user_info"."password" IS '密码';
COMMENT ON COLUMN "public"."user_info"."data_permission" IS '数据权限';
COMMENT ON COLUMN "public"."user_info"."company_id" IS '所属公司ID';
COMMENT ON COLUMN "public"."user_info"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."user_info"."deleted" IS '是否删除';

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Indexes structure for table bang_inn
-- ----------------------------
CREATE INDEX "bang_inn_company_id_inn_id_idx" ON "public"."bang_inn" USING btree (company_id, inn_id, inn_label_id);

-- ----------------------------
-- Primary Key structure for table bang_inn
-- ----------------------------
ALTER TABLE "public"."bang_inn" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table company
-- ----------------------------
ALTER TABLE "public"."company" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table company_permission
-- ----------------------------
CREATE INDEX "company_permission_company_id_permission_id_idx" ON "public"."company_permission" USING btree (company_id, permission_id);

-- ----------------------------
-- Primary Key structure for table company_permission
-- ----------------------------
ALTER TABLE "public"."company_permission" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dc_user
-- ----------------------------
ALTER TABLE "public"."dc_user" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table inn_active
-- ----------------------------
CREATE INDEX "inn_active_inn_id_create_date_idx" ON "public"."inn_active" USING btree (inn_id, create_date);

-- ----------------------------
-- Primary Key structure for table inn_active
-- ----------------------------
ALTER TABLE "public"."inn_active" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table inn_customer
-- ----------------------------
CREATE INDEX "inn_customer_create_date_idx" ON "public"."inn_customer" USING btree (create_date);
CREATE INDEX "inn_customer_inn_id_idx" ON "public"."inn_customer" USING btree (inn_id);

-- ----------------------------
-- Primary Key structure for table inn_customer
-- ----------------------------
ALTER TABLE "public"."inn_customer" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table inn_label
-- ----------------------------
CREATE INDEX "inn_label_company_idx" ON "public"."inn_label" USING btree (company_id);

-- ----------------------------
-- Primary Key structure for table inn_label
-- ----------------------------
ALTER TABLE "public"."inn_label" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table notice_template
-- ----------------------------
CREATE INDEX "notice_company_idx" ON "public"."notice_template" USING btree (company_id);

-- ----------------------------
-- Primary Key structure for table notice_template
-- ----------------------------
ALTER TABLE "public"."notice_template" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table operate_trend
-- ----------------------------
CREATE INDEX "operate_trend_create_date_idx" ON "public"."operate_trend" USING btree (create_date);
CREATE INDEX "operate_trend_inn_id_idx" ON "public"."operate_trend" USING btree (inn_id);

-- ----------------------------
-- Primary Key structure for table operate_trend
-- ----------------------------
ALTER TABLE "public"."operate_trend" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table order_source
-- ----------------------------
CREATE INDEX "order_source_inn_id_idx" ON "public"."order_source" USING btree (inn_id);

-- ----------------------------
-- Primary Key structure for table order_source
-- ----------------------------
ALTER TABLE "public"."order_source" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table role
-- ----------------------------
CREATE INDEX "role_key_ids" ON "public"."role" USING btree (role_key);

-- ----------------------------
-- Primary Key structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table role_permission
-- ----------------------------
CREATE INDEX "role_permission_idx" ON "public"."role_permission" USING btree (role_id, permission_id);

-- ----------------------------
-- Primary Key structure for table role_permission
-- ----------------------------
ALTER TABLE "public"."role_permission" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user_info
-- ----------------------------
CREATE INDEX "user_role_idx" ON "public"."user_info" USING btree (company_id, role_id);

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info" ADD PRIMARY KEY ("id");
