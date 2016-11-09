/*
Navicat PGSQL Data Transfer

Source Server         : 17
Source Server Version : 90119
Source Host           : 192.168.1.17:5432
Source Database       : dc_pms
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90119
File Encoding         : 65001

Date: 2016-11-09 10:14:54
*/


-- ----------------------------
-- Table structure for bang_inn
-- ----------------------------
DROP TABLE IF EXISTS "public"."bang_inn";
CREATE TABLE "public"."bang_inn" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"bang_date" timestamp(6),
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
"account_id" int4,
"deleted" int2,
"ota_wg_id" varchar(64) COLLATE "default",
"account_id_di" int4,
"updated_date" timestamp(6),
"sj" int2 DEFAULT 1,
"address" varchar(200) COLLATE "default"
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
COMMENT ON COLUMN "public"."bang_inn"."address" IS '客栈地址';

-- ----------------------------
-- Table structure for bang_inn_to_qunar_city
-- ----------------------------
DROP TABLE IF EXISTS "public"."bang_inn_to_qunar_city";
CREATE TABLE "public"."bang_inn_to_qunar_city" (
"id" varchar(64) COLLATE "default" NOT NULL,
"bang_inn_id" varchar(64) COLLATE "default",
"qunar_city_info_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."bang_inn_to_qunar_city"."id" IS 'id';
COMMENT ON COLUMN "public"."bang_inn_to_qunar_city"."bang_inn_id" IS '绑定的客栈id';
COMMENT ON COLUMN "public"."bang_inn_to_qunar_city"."qunar_city_info_id" IS '去哪儿city的id';

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
"user_password" varchar(64) COLLATE "default",
"company_type" varchar(8) COLLATE "default"
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
COMMENT ON COLUMN "public"."company"."company_type" IS '公司类型';

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
-- Table structure for ctrip_homestay_room_ref
-- ----------------------------
DROP TABLE IF EXISTS "public"."ctrip_homestay_room_ref";
CREATE TABLE "public"."ctrip_homestay_room_ref" (
"id" int4 DEFAULT nextval('ctrip_homestay_room_ref_seq'::regclass) NOT NULL,
"ota_id" int4,
"account_id" int4,
"inn_id" int4,
"deleted" int4 DEFAULT 0,
"created_at" timestamp(6) DEFAULT now(),
"update_at" timestamp(6),
"ota_roomtype_id" int4 DEFAULT nextval('ctrip_homestay_room_ref_seq'::regclass),
"roomtype_name" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for ctrip_parent_hotel
-- ----------------------------
DROP TABLE IF EXISTS "public"."ctrip_parent_hotel";
CREATE TABLE "public"."ctrip_parent_hotel" (
"id" varchar(64) COLLATE "default" DEFAULT nextval('xc_parent_hotel_room2_id_seq'::regclass) NOT NULL,
"hotel_name" varchar(60) COLLATE "default",
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

-- ----------------------------
-- Table structure for ctrip_parent_room
-- ----------------------------
DROP TABLE IF EXISTS "public"."ctrip_parent_room";
CREATE TABLE "public"."ctrip_parent_room" (
"id" varchar(64) COLLATE "default" DEFAULT nextval('xc_parent_hotel_room2_id_seq'::regclass) NOT NULL,
"ctrip_prent_hotel_id" varchar(60) COLLATE "default",
"room_type_name" varchar(60) COLLATE "default",
"room_type_id" varchar(20) COLLATE "default",
"currency" varchar(20) COLLATE "default",
"bed_type" varchar(20) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"child_room_id" varchar(20) COLLATE "default",
"check_in_num" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ctrip_parent_room"."ctrip_prent_hotel_id" IS '携程母酒店id';
COMMENT ON COLUMN "public"."ctrip_parent_room"."child_room_id" IS '子房型ID';

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
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS "public"."dictionary";
CREATE TABLE "public"."dictionary" (
"id" varchar(64) COLLATE "default" NOT NULL,
"desc" varchar(100) COLLATE "default",
"url" varchar(100) COLLATE "default",
"type" varchar(20) COLLATE "default",
"value" varchar(30) COLLATE "default",
"v_name" varchar(50) COLLATE "default",
"v_pwd" varchar(64) COLLATE "default",
"asynchronous_url" varchar(200) COLLATE "default",
"weather_asynchronous" int2 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."dictionary"."id" IS 'ID';
COMMENT ON COLUMN "public"."dictionary"."desc" IS '注释';
COMMENT ON COLUMN "public"."dictionary"."url" IS '请求的url';
COMMENT ON COLUMN "public"."dictionary"."type" IS '类型';
COMMENT ON COLUMN "public"."dictionary"."value" IS '值';
COMMENT ON COLUMN "public"."dictionary"."v_name" IS '账号名称';
COMMENT ON COLUMN "public"."dictionary"."v_pwd" IS '账号密码';
COMMENT ON COLUMN "public"."dictionary"."asynchronous_url" IS '异步请求url';
COMMENT ON COLUMN "public"."dictionary"."weather_asynchronous" IS '是否为异步请求';

-- ----------------------------
-- Table structure for fc_area
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_area";
CREATE TABLE "public"."fc_area" (
"id" varchar(64) COLLATE "default" NOT NULL,
"city_code" varchar(30) COLLATE "default",
"area_name" varchar(40) COLLATE "default",
"area_code" varchar(40) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamptz(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."fc_area"."city_code" IS '城市code';
COMMENT ON COLUMN "public"."fc_area"."area_name" IS '区域名称';
COMMENT ON COLUMN "public"."fc_area"."area_code" IS '区域code';

-- ----------------------------
-- Table structure for fc_city
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_city";
CREATE TABLE "public"."fc_city" (
"id" varchar(64) COLLATE "default" NOT NULL,
"province_code" varchar(64) COLLATE "default",
"city_name" varchar(64) COLLATE "default",
"city_code" varchar(20) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."fc_city"."province_code" IS '省份code';
COMMENT ON COLUMN "public"."fc_city"."city_name" IS '城市名称';
COMMENT ON COLUMN "public"."fc_city"."city_code" IS '城市code';

-- ----------------------------
-- Table structure for fc_hotel_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_hotel_info";
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
"business" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
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

-- ----------------------------
-- Table structure for fc_province
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_province";
CREATE TABLE "public"."fc_province" (
"id" varchar(64) COLLATE "default" NOT NULL,
"province_name" varchar(64) COLLATE "default",
"province_code" varchar(30) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."fc_province"."province_name" IS '省份名称';
COMMENT ON COLUMN "public"."fc_province"."province_code" IS '省份code';

-- ----------------------------
-- Table structure for fc_room_type_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_room_type_info";
CREATE TABLE "public"."fc_room_type_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"hotel_id" varchar(50) COLLATE "default",
"room_type_id" varchar(50) COLLATE "default",
"room_type_name" varchar(200) COLLATE "default",
"bed_type" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."fc_room_type_info"."id" IS 'ID';
COMMENT ON COLUMN "public"."fc_room_type_info"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."fc_room_type_info"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."fc_room_type_info"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."fc_room_type_info"."hotel_id" IS '酒店ID';
COMMENT ON COLUMN "public"."fc_room_type_info"."room_type_id" IS '酒店房型ID';
COMMENT ON COLUMN "public"."fc_room_type_info"."room_type_name" IS '酒店名称';
COMMENT ON COLUMN "public"."fc_room_type_info"."bed_type" IS '床型';

-- ----------------------------
-- Table structure for fc_roomtype_fq
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_roomtype_fq";
CREATE TABLE "public"."fc_roomtype_fq" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" varchar(64) COLLATE "default",
"fc_hotel_id" varchar(64) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"ota_inn_ota_id" varchar(64) COLLATE "default",
"ota_info_id" varchar(64) COLLATE "default",
"fq_roomtype_id" varchar(64) COLLATE "default",
"fc_roomtype_id" varchar(64) COLLATE "default",
"room_area" numeric(6,2),
"fq_roomtype_name" varchar(64) COLLATE "default",
"fc_roomtype_name" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"rate_plan_id" varchar(64) COLLATE "default",
"sj" int2,
"bed_num" varchar(16) COLLATE "default",
"bed_len" varchar(16) COLLATE "default",
"bed_wid" varchar(16) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."fc_roomtype_fq"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."fc_hotel_id" IS '房仓酒店id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."ota_inn_ota_id" IS '渠道关联id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."ota_info_id" IS '渠道id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."fq_roomtype_id" IS '番茄房型id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."fc_roomtype_id" IS '房仓房型id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."fq_roomtype_name" IS '番茄房型名称';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."fc_roomtype_name" IS '房仓房型名称';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."rate_plan_id" IS '价格计划id';
COMMENT ON COLUMN "public"."fc_roomtype_fq"."sj" IS '1 上架 0 下架 -1 没有上架';

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
-- Table structure for inn_change_price_msg
-- ----------------------------
DROP TABLE IF EXISTS "public"."inn_change_price_msg";
CREATE TABLE "public"."inn_change_price_msg" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int4 NOT NULL,
"inn_name" varchar(64) COLLATE "default" NOT NULL,
"create_date" timestamp(6) NOT NULL,
"update_date" timestamp(6),
"read" bool NOT NULL,
"message" varchar(255) COLLATE "default" NOT NULL,
"company_type" varchar(8) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."inn_change_price_msg"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."inn_change_price_msg"."inn_name" IS '客栈名称';
COMMENT ON COLUMN "public"."inn_change_price_msg"."create_date" IS '记录创建时间';
COMMENT ON COLUMN "public"."inn_change_price_msg"."read" IS '是否已读，t为已读';
COMMENT ON COLUMN "public"."inn_change_price_msg"."message" IS '消息内容';
COMMENT ON COLUMN "public"."inn_change_price_msg"."company_type" IS '公司类型';

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
-- Table structure for jw_inn_room_mapping
-- ----------------------------
DROP TABLE IF EXISTS "public"."jw_inn_room_mapping";
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
-- Table structure for order_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."order_config";
CREATE TABLE "public"."order_config" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int8,
"company_id" varchar(64) COLLATE "default",
"ota_info_id" varchar(64) COLLATE "default",
"status" int2,
"updated_date" timestamp(6),
"deleted" int2,
"modifier_id" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."order_config"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."order_config"."ota_info_id" IS '渠道id';
COMMENT ON COLUMN "public"."order_config"."status" IS '是否手动(0:自动;1:手动; 默认自动)';
COMMENT ON COLUMN "public"."order_config"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."order_config"."modifier_id" IS '修改人id';

-- ----------------------------
-- Table structure for order_operation_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."order_operation_record";
CREATE TABLE "public"."order_operation_record" (
"id" varchar(64) COLLATE "default" NOT NULL,
"order_id" varchar(64) COLLATE "default",
"before_status" varchar(50) COLLATE "default",
"after_status" varchar(50) COLLATE "default",
"modify_person" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"content" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."order_operation_record"."id" IS 'ID';
COMMENT ON COLUMN "public"."order_operation_record"."order_id" IS '订单ID';
COMMENT ON COLUMN "public"."order_operation_record"."before_status" IS '订单修改之前状态';
COMMENT ON COLUMN "public"."order_operation_record"."after_status" IS '订单修改之后状态';
COMMENT ON COLUMN "public"."order_operation_record"."modify_person" IS '修改人ID';
COMMENT ON COLUMN "public"."order_operation_record"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."order_operation_record"."content" IS '备注';

-- ----------------------------
-- Table structure for order_other_price
-- ----------------------------
DROP TABLE IF EXISTS "public"."order_other_price";
CREATE TABLE "public"."order_other_price" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
"other_consumer_info_id" varchar(64) COLLATE "default",
"price_name" varchar(50) COLLATE "default",
"price" numeric,
"nums" int2,
"consumer_project_name" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."order_other_price"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."order_other_price"."order_id" IS '订单id';
COMMENT ON COLUMN "public"."order_other_price"."other_consumer_info_id" IS '其他费用id';
COMMENT ON COLUMN "public"."order_other_price"."price_name" IS '其他费用名称';
COMMENT ON COLUMN "public"."order_other_price"."price" IS '价格';
COMMENT ON COLUMN "public"."order_other_price"."nums" IS '数量';
COMMENT ON COLUMN "public"."order_other_price"."consumer_project_name" IS '其他费用项目名称';

-- ----------------------------
-- Table structure for order_source
-- ----------------------------
DROP TABLE IF EXISTS "public"."order_source";
CREATE TABLE "public"."order_source" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int4,
"from_name" varchar(30) COLLATE "default",
"live_num" int4,
"income" numeric(10,2),
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
-- Table structure for ota_bang_inn_room
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_bang_inn_room";
CREATE TABLE "public"."ota_bang_inn_room" (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int8,
"room_type_id" int8,
"room_type_name" varchar(25) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"rid" varchar(30) COLLATE "default",
"price_model_id" varchar(64) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"deleted" int2,
"created_date" timestamp(6),
"updated_date" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_bang_inn_room"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."room_type_name" IS '房型名字';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."rid" IS 'TP点房型id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."price_model_id" IS '价格模式id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."ota_wg_id" IS 'ota_inn_ota  id';

-- ----------------------------
-- Table structure for ota_commission_percent
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_commission_percent";
CREATE TABLE "public"."ota_commission_percent" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"commission_percent" numeric(3),
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"sjia_model" varchar(20) COLLATE "default",
"ota_id" int8,
"operate_type" varchar(30) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_commission_percent"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_commission_percent"."commission_percent" IS '佣金比';
COMMENT ON COLUMN "public"."ota_commission_percent"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."ota_commission_percent"."sjia_model" IS '上架模式';
COMMENT ON COLUMN "public"."ota_commission_percent"."ota_id" IS '渠道id';
COMMENT ON COLUMN "public"."ota_commission_percent"."operate_type" IS '类型';

-- ----------------------------
-- Table structure for ota_daily_infos
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_daily_infos";
CREATE TABLE "public"."ota_daily_infos" (
"id" varchar(64) COLLATE "default" NOT NULL,
"day" date,
"created_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
"price" numeric(10,2),
"break_fast_type" varchar(100) COLLATE "default",
"break_fast_num" int4,
"weather_add" int2,
"basic_price" numeric,
"room_type_id" varchar(50) COLLATE "default",
"room_type_name" varchar(60) COLLATE "default",
"room_type_nums" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_daily_infos"."id" IS 'ID';
COMMENT ON COLUMN "public"."ota_daily_infos"."day" IS '时间';
COMMENT ON COLUMN "public"."ota_daily_infos"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ota_daily_infos"."order_id" IS '订单ID';
COMMENT ON COLUMN "public"."ota_daily_infos"."price" IS '价格';
COMMENT ON COLUMN "public"."ota_daily_infos"."break_fast_type" IS '早餐类型';
COMMENT ON COLUMN "public"."ota_daily_infos"."break_fast_num" IS '早餐数量';
COMMENT ON COLUMN "public"."ota_daily_infos"."weather_add" IS '是否加减价';
COMMENT ON COLUMN "public"."ota_daily_infos"."basic_price" IS '下单每日价格';
COMMENT ON COLUMN "public"."ota_daily_infos"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."ota_daily_infos"."room_type_name" IS '房型名称';
COMMENT ON COLUMN "public"."ota_daily_infos"."room_type_nums" IS '房间数量';

-- ----------------------------
-- Table structure for ota_exception_order
-- ----------------------------
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
-- Table structure for ota_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_info";
CREATE TABLE "public"."ota_info" (
"id" varchar COLLATE "default" NOT NULL,
"ota_info" varchar(20) COLLATE "default",
"ota_type" varchar(10) COLLATE "default",
"deleted" int2,
"sort" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_info"."ota_info" IS 'OTA 描述';
COMMENT ON COLUMN "public"."ota_info"."ota_type" IS 'OTA 类型';
COMMENT ON COLUMN "public"."ota_info"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for ota_inn_ota
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_inn_ota";
CREATE TABLE "public"."ota_inn_ota" (
"id" varchar(64) COLLATE "default" NOT NULL,
"wg_hid" varchar(64) COLLATE "default",
"commission_percent" numeric(3),
"company_id" varchar(64) COLLATE "default",
"alias_inn_name" varchar(30) COLLATE "default",
"ota_id" varchar(64) COLLATE "default",
"price_model" varchar(50) COLLATE "default",
"sjia_model" varchar COLLATE "default",
"deleted" int2,
"bang_inn_id" varchar(64) COLLATE "default",
"ota_info_id" varchar(64) COLLATE "default",
"inn_id" int8,
"sj" int2,
"updated_date" timestamp(6),
"created_date" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_inn_ota"."wg_hid" IS 'tp店酒店id';
COMMENT ON COLUMN "public"."ota_inn_ota"."commission_percent" IS '佣金比例';
COMMENT ON COLUMN "public"."ota_inn_ota"."company_id" IS '企业id';
COMMENT ON COLUMN "public"."ota_inn_ota"."alias_inn_name" IS '客栈别名';
COMMENT ON COLUMN "public"."ota_inn_ota"."ota_id" IS 'OTA id';
COMMENT ON COLUMN "public"."ota_inn_ota"."price_model" IS '价格模式';
COMMENT ON COLUMN "public"."ota_inn_ota"."sjia_model" IS '上架模式';
COMMENT ON COLUMN "public"."ota_inn_ota"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for ota_inn_roomtype_goods
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_inn_roomtype_goods";
CREATE TABLE "public"."ota_inn_roomtype_goods" (
"id" varchar(60) COLLATE "default" NOT NULL,
"room_type_id" varchar(64) COLLATE "default",
"inn_id" varchar(64) COLLATE "default",
"rpid" varchar(64) COLLATE "default",
"gid" varchar(64) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"rid" varchar(30) COLLATE "default",
"product_date" timestamp(6),
"deleted" int2 DEFAULT 0,
"created_date" timestamp(6),
"updated_date" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_inn_roomtype_goods"."rid" IS 'tp房型id';
COMMENT ON COLUMN "public"."ota_inn_roomtype_goods"."product_date" IS '商品上架更新时间';

-- ----------------------------
-- Table structure for ota_order_guests
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_order_guests";
CREATE TABLE "public"."ota_order_guests" (
"id" varchar(64) COLLATE "default" NOT NULL,
"name" varchar(32) COLLATE "default",
"room_pos" int4,
"created_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
"nationality" varchar(50) COLLATE "default",
"first_name" varchar(50) COLLATE "default",
"last_name" varchar(50) COLLATE "default",
"guest_province" varchar(40) COLLATE "default",
"guest_city" varchar(40) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_order_guests"."id" IS 'ID';
COMMENT ON COLUMN "public"."ota_order_guests"."name" IS '客人名称';
COMMENT ON COLUMN "public"."ota_order_guests"."room_pos" IS '房间编号';
COMMENT ON COLUMN "public"."ota_order_guests"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ota_order_guests"."order_id" IS '订单ID';
COMMENT ON COLUMN "public"."ota_order_guests"."nationality" IS '国籍';
COMMENT ON COLUMN "public"."ota_order_guests"."first_name" IS '英文名';
COMMENT ON COLUMN "public"."ota_order_guests"."last_name" IS '英文姓';

-- ----------------------------
-- Table structure for ota_pending_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_pending_order";
CREATE TABLE "public"."ota_pending_order" (
"id" varchar(64) COLLATE "default" NOT NULL,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
"modify_status" varchar(50) COLLATE "default",
"reason_desc" varchar(50) COLLATE "default",
"deleted" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_pending_order"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ota_pending_order"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."ota_pending_order"."order_id" IS '订单id';
COMMENT ON COLUMN "public"."ota_pending_order"."modify_status" IS '更改订单状态';
COMMENT ON COLUMN "public"."ota_pending_order"."reason_desc" IS '待处理订单描述';
COMMENT ON COLUMN "public"."ota_pending_order"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for ota_price_model
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_price_model";
CREATE TABLE "public"."ota_price_model" (
"id" varchar(64) COLLATE "default" NOT NULL,
"price_model" varchar(20) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"price_model_value" numeric(6,2),
"created_date" timestamp(6),
"deleted" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_price_model"."price_model" IS '价格模式';
COMMENT ON COLUMN "public"."ota_price_model"."ota_wg_id" IS 'ota_inn_ota 表id';
COMMENT ON COLUMN "public"."ota_price_model"."price_model_value" IS '价格模式值';
COMMENT ON COLUMN "public"."ota_price_model"."deleted" IS '是否删除';

-- ----------------------------
-- Table structure for ota_rate_plan
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_rate_plan";
CREATE TABLE "public"."ota_rate_plan" (
"id" varchar(64) COLLATE "default" NOT NULL,
"rate_plan_name" varchar(30) COLLATE "default",
"currency" varchar(10) COLLATE "default",
"pay_method" varchar(10) COLLATE "default",
"bed_type" varchar(30) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"company_id" varchar(64) COLLATE "default",
"deleted" int2,
"rate_plan_id" int2 DEFAULT nextval('fc_rate_plan_seq'::regclass),
"ota_info_id" varchar(60) COLLATE "default",
"rate_code_default" bool DEFAULT false,
"rate_plan_code" varchar(30) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_rate_plan"."rate_plan_name" IS '名称';
COMMENT ON COLUMN "public"."ota_rate_plan"."currency" IS '币种';
COMMENT ON COLUMN "public"."ota_rate_plan"."pay_method" IS '支付类型';
COMMENT ON COLUMN "public"."ota_rate_plan"."bed_type" IS '床型';
COMMENT ON COLUMN "public"."ota_rate_plan"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ota_rate_plan"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."ota_rate_plan"."ota_info_id" IS '渠道id';
COMMENT ON COLUMN "public"."ota_rate_plan"."rate_plan_code" IS '携程ratePlanCode';

-- ----------------------------
-- Table structure for ota_room_price
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_room_price";
CREATE TABLE "public"."ota_room_price" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"inn_id" int8,
"room_type_id" int8,
"start_date" date,
"end_date" date,
"value" numeric(5,1),
"ota_info_id" varchar(64) COLLATE "default",
"account_id" int8,
"modifier_id" varchar(64) COLLATE "default",
"created_date" timestamp(6),
"deleted" int2,
"room_type_name" varchar(60) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_room_price"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_room_price"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."ota_room_price"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."ota_room_price"."start_date" IS '特殊价格开始时间';
COMMENT ON COLUMN "public"."ota_room_price"."end_date" IS '特殊价格结束时间';
COMMENT ON COLUMN "public"."ota_room_price"."ota_info_id" IS '渠道id';
COMMENT ON COLUMN "public"."ota_room_price"."modifier_id" IS '修改人id';
COMMENT ON COLUMN "public"."ota_room_price"."room_type_name" IS '房型名称';

-- ----------------------------
-- Table structure for ota_taobao_area
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_taobao_area";
CREATE TABLE "public"."ota_taobao_area" (
"id" int4 DEFAULT nextval('ota_taobao_area_id_seq'::regclass) NOT NULL,
"province_code" varchar(20) COLLATE "default",
"province_name" varchar(50) COLLATE "default",
"city_code" varchar(20) COLLATE "default",
"city_name" varchar(50) COLLATE "default",
"area_code" varchar(20) COLLATE "default",
"area_name" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for ota_toms_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."ota_toms_order";
CREATE TABLE "public"."ota_toms_order" (
"id" varchar(64) COLLATE "default" NOT NULL,
"version" int4,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int4,
"modifier_id" varchar(64) COLLATE "default" DEFAULT ''::character varying,
"channel_source" varchar(30) COLLATE "default",
"channel_order_code" varchar(32) COLLATE "default",
"order_status" varchar(20) COLLATE "default",
"inn_id" int4,
"guest_name" varchar(50) COLLATE "default",
"room_type_id" varchar(20) COLLATE "default",
"home_amount" int4,
"live_time" timestamp(6),
"leave_time" timestamp(6),
"total_price" numeric,
"prepay_price" numeric,
"cost_price" numeric,
"ota_price" numeric,
"order_time" timestamp(6),
"ota_room_type_id" varchar(64) COLLATE "default",
"ota_hotel_id" varchar(64) COLLATE "default",
"ota_rate_plan_id" varchar(64) COLLATE "default",
"ota_gid" varchar(64) COLLATE "default",
"eariest_arrive_time" timestamp(6),
"lastest_arrive_time" timestamp(6),
"currency" varchar(30) COLLATE "default",
"payment_type" varchar(32) COLLATE "default",
"guest_mobile" varchar(32) COLLATE "default",
"ota_rate_code" varchar(64) COLLATE "default",
"fee_status" varchar(20) COLLATE "default",
"comment" varchar(255) COLLATE "default",
"reason" varchar(255) COLLATE "default",
"alipay_trade_no" varchar(50) COLLATE "default",
"payment" numeric,
"company_id" varchar(64) COLLATE "default",
"order_code" varchar(100) COLLATE "default",
"guest_email" varchar(100) COLLATE "default",
"special_requirement" varchar(255) COLLATE "default",
"reserved_item" varchar(200) COLLATE "default",
"fc_bed_type" varchar(50) COLLATE "default",
"confirm_type" varchar(50) COLLATE "default",
"percent" numeric,
"used_price_model" varchar(20) COLLATE "default",
"add_price" numeric,
"basic_total_price" numeric,
"partner_code" varchar(100) COLLATE "default",
"oms_order_code" varchar(100) COLLATE "default",
"order_inn_name" varchar(100) COLLATE "default",
"order_room_type_name" varchar(100) COLLATE "default",
"person" int2,
"interface_send_id" varchar(64) COLLATE "default",
"user_id" varchar(64) COLLATE "default",
"xml_data" text COLLATE "default",
"order_source" varchar(50) COLLATE "default",
"refund_status" bool,
"json_data" varchar(500) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ota_toms_order"."id" IS 'ID';
COMMENT ON COLUMN "public"."ota_toms_order"."version" IS '版本号';
COMMENT ON COLUMN "public"."ota_toms_order"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."ota_toms_order"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."ota_toms_order"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."ota_toms_order"."modifier_id" IS '更新者ID';
COMMENT ON COLUMN "public"."ota_toms_order"."channel_source" IS '订单来源渠道';
COMMENT ON COLUMN "public"."ota_toms_order"."channel_order_code" IS '渠道订单号';
COMMENT ON COLUMN "public"."ota_toms_order"."order_status" IS '订单状态';
COMMENT ON COLUMN "public"."ota_toms_order"."inn_id" IS '客栈ID';
COMMENT ON COLUMN "public"."ota_toms_order"."guest_name" IS '客人名称';
COMMENT ON COLUMN "public"."ota_toms_order"."room_type_id" IS '房型ID';
COMMENT ON COLUMN "public"."ota_toms_order"."home_amount" IS '房间数量';
COMMENT ON COLUMN "public"."ota_toms_order"."live_time" IS '入住时间';
COMMENT ON COLUMN "public"."ota_toms_order"."leave_time" IS '离店时间';
COMMENT ON COLUMN "public"."ota_toms_order"."total_price" IS '总价';
COMMENT ON COLUMN "public"."ota_toms_order"."prepay_price" IS '预付价格';
COMMENT ON COLUMN "public"."ota_toms_order"."cost_price" IS '成本价';
COMMENT ON COLUMN "public"."ota_toms_order"."ota_price" IS 'OTA佣金';
COMMENT ON COLUMN "public"."ota_toms_order"."order_time" IS '下单时间';
COMMENT ON COLUMN "public"."ota_toms_order"."ota_room_type_id" IS 'OTA房型ID';
COMMENT ON COLUMN "public"."ota_toms_order"."ota_hotel_id" IS 'OTA酒店ID';
COMMENT ON COLUMN "public"."ota_toms_order"."ota_rate_plan_id" IS 'OTA价格策略ID';
COMMENT ON COLUMN "public"."ota_toms_order"."ota_gid" IS 'OTA商品ID';
COMMENT ON COLUMN "public"."ota_toms_order"."eariest_arrive_time" IS '最早到店时间';
COMMENT ON COLUMN "public"."ota_toms_order"."lastest_arrive_time" IS '最晚到店时间';
COMMENT ON COLUMN "public"."ota_toms_order"."currency" IS '货币类型';
COMMENT ON COLUMN "public"."ota_toms_order"."payment_type" IS '付款方式';
COMMENT ON COLUMN "public"."ota_toms_order"."guest_mobile" IS '联系人电话';
COMMENT ON COLUMN "public"."ota_toms_order"."ota_rate_code" IS 'OTA价格策略';
COMMENT ON COLUMN "public"."ota_toms_order"."fee_status" IS '付款状态';
COMMENT ON COLUMN "public"."ota_toms_order"."comment" IS '备注';
COMMENT ON COLUMN "public"."ota_toms_order"."reason" IS '取消订单原因';
COMMENT ON COLUMN "public"."ota_toms_order"."alipay_trade_no" IS '支付宝交易号';
COMMENT ON COLUMN "public"."ota_toms_order"."payment" IS '付款金额';
COMMENT ON COLUMN "public"."ota_toms_order"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_toms_order"."order_code" IS 'TOMS订单号';
COMMENT ON COLUMN "public"."ota_toms_order"."guest_email" IS '客人邮件联系方式';
COMMENT ON COLUMN "public"."ota_toms_order"."special_requirement" IS '特殊要求';
COMMENT ON COLUMN "public"."ota_toms_order"."reserved_item" IS '附加设置';
COMMENT ON COLUMN "public"."ota_toms_order"."fc_bed_type" IS '天下房仓房间类型';
COMMENT ON COLUMN "public"."ota_toms_order"."confirm_type" IS '订单确认方式';
COMMENT ON COLUMN "public"."ota_toms_order"."percent" IS '价格比例';
COMMENT ON COLUMN "public"."ota_toms_order"."used_price_model" IS '当前价格模式';
COMMENT ON COLUMN "public"."ota_toms_order"."add_price" IS '加减价';
COMMENT ON COLUMN "public"."ota_toms_order"."basic_total_price" IS '订单下单总价';
COMMENT ON COLUMN "public"."ota_toms_order"."partner_code" IS '房仓合作商的code';
COMMENT ON COLUMN "public"."ota_toms_order"."oms_order_code" IS 'oms订单号';
COMMENT ON COLUMN "public"."ota_toms_order"."order_inn_name" IS '客栈名称';
COMMENT ON COLUMN "public"."ota_toms_order"."order_room_type_name" IS '房型名称';
COMMENT ON COLUMN "public"."ota_toms_order"."person" IS '入住人数量';
COMMENT ON COLUMN "public"."ota_toms_order"."interface_send_id" IS '接口确认号';
COMMENT ON COLUMN "public"."ota_toms_order"."user_id" IS '操作人id';
COMMENT ON COLUMN "public"."ota_toms_order"."xml_data" IS '创建订单请求的xml数据';
COMMENT ON COLUMN "public"."ota_toms_order"."order_source" IS '订单来源';
COMMENT ON COLUMN "public"."ota_toms_order"."refund_status" IS '取消订单时，选择是否扣款，ture为扣款，false为不扣款';
COMMENT ON COLUMN "public"."ota_toms_order"."json_data" IS 'json数据';

-- ----------------------------
-- Table structure for otainfo_company_ref
-- ----------------------------
DROP TABLE IF EXISTS "public"."otainfo_company_ref";
CREATE TABLE "public"."otainfo_company_ref" (
"id" varchar(64) COLLATE "default" NOT NULL,
"ota_info_id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"appkey" varchar(30) COLLATE "default",
"appsecret" varchar(100) COLLATE "default",
"sessionkey" varchar(300) COLLATE "default",
"status" int2,
"expired_time" timestamp(6),
"used_price_model" varchar(16) COLLATE "default",
"tb_type" varchar(16) COLLATE "default",
"create_date" timestamp(6),
"xc_user_name" varchar(30) COLLATE "default",
"xc_password" varchar(30) COLLATE "default",
"updated_date" timestamp(6),
"user_id" varchar(30) COLLATE "default",
"vendor_id" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."otainfo_company_ref"."ota_info_id" IS '渠道id(ota_info的主键)';
COMMENT ON COLUMN "public"."otainfo_company_ref"."appkey" IS '携程SupplierID 值';
COMMENT ON COLUMN "public"."otainfo_company_ref"."appsecret" IS '携程的 Brand 值';
COMMENT ON COLUMN "public"."otainfo_company_ref"."status" IS '开关(1:开启，0:关闭)';
COMMENT ON COLUMN "public"."otainfo_company_ref"."expired_time" IS '失效时间';
COMMENT ON COLUMN "public"."otainfo_company_ref"."used_price_model" IS '是否使用卖转低';
COMMENT ON COLUMN "public"."otainfo_company_ref"."tb_type" IS 'TB的直连类型（其余的渠道为null）';
COMMENT ON COLUMN "public"."otainfo_company_ref"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."otainfo_company_ref"."xc_user_name" IS '携程用户名';
COMMENT ON COLUMN "public"."otainfo_company_ref"."xc_password" IS '携程密码';
COMMENT ON COLUMN "public"."otainfo_company_ref"."updated_date" IS '更新时间';
COMMENT ON COLUMN "public"."otainfo_company_ref"."user_id" IS 'user_id 携程线下给。';
COMMENT ON COLUMN "public"."otainfo_company_ref"."vendor_id" IS '系统商名称';

-- ----------------------------
-- Table structure for other_consumer_function
-- ----------------------------
DROP TABLE IF EXISTS "public"."other_consumer_function";
CREATE TABLE "public"."other_consumer_function" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"deleted" int2,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"status" bool,
"myself_channel_status" bool,
"pms_channel_name_status" bool
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."other_consumer_function"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."other_consumer_function"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."other_consumer_function"."status" IS '状态（f未开启，t开启）';
COMMENT ON COLUMN "public"."other_consumer_function"."myself_channel_status" IS '是否开启自定义渠道';
COMMENT ON COLUMN "public"."other_consumer_function"."pms_channel_name_status" IS 'pms客栈名称设置';

-- ----------------------------
-- Table structure for other_consumer_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."other_consumer_info";
CREATE TABLE "public"."other_consumer_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"consumer_fun_id" varchar(64) COLLATE "default",
"level" int2,
"consumer_project_name" varchar(50) COLLATE "default",
"price_name" varchar(50) COLLATE "default",
"price" numeric(5,1),
"parent_id" varchar(64) COLLATE "default",
"deleted" int2,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"status" bool,
"modifier_id" varchar(64) COLLATE "default",
"creator_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."other_consumer_info"."consumer_fun_id" IS '配置(other_consumer_function)表id';
COMMENT ON COLUMN "public"."other_consumer_info"."consumer_project_name" IS '消费项目名称';

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
-- Table structure for qunar_city_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."qunar_city_info";
CREATE TABLE "public"."qunar_city_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"city_code" varchar(50) COLLATE "default",
"city_type" varchar(50) COLLATE "default",
"name" varchar(100) COLLATE "default",
"en_name" varchar(200) COLLATE "default",
"area_path" varchar(200) COLLATE "default",
"is_rootcity" varchar(10) COLLATE "default"
)
WITH (OIDS=FALSE)

;

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
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS "public"."test";
CREATE TABLE "public"."test" (
"name" varchar(10) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for timer_rate_price
-- ----------------------------
DROP TABLE IF EXISTS "public"."timer_rate_price";
CREATE TABLE "public"."timer_rate_price" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"inn_id" int8,
"room_type_id" int8,
"ota_info_id" varchar(64) COLLATE "default",
"account_id" varchar(64) COLLATE "default",
"error_content" varchar(255) COLLATE "default",
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int2,
"rate_type" varchar(16) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."timer_rate_price"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."timer_rate_price"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."timer_rate_price"."ota_info_id" IS '渠道id （淘宝id：1；房仓id：2）';
COMMENT ON COLUMN "public"."timer_rate_price"."error_content" IS '错误信息';
COMMENT ON COLUMN "public"."timer_rate_price"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."timer_rate_price"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."timer_rate_price"."rate_type" IS '错误类型';

-- ----------------------------
-- Table structure for toms_myself_channel
-- ----------------------------
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
-- Table structure for toms_pms_channel_name
-- ----------------------------
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
"deleted" int2 DEFAULT 0,
"company_type" varchar(8) COLLATE "default"
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
COMMENT ON COLUMN "public"."user_info"."company_type" IS '公司类型（冗余字段）';

-- ----------------------------
-- Table structure for xc_roomtype_fq
-- ----------------------------
DROP TABLE IF EXISTS "public"."xc_roomtype_fq";
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
"rate_plan_code_name" varchar(50) COLLATE "default",
"fq_rate_plan_code" varchar(50) COLLATE "default",
"room_type_code" varchar(64) COLLATE "default",
"inn_code" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."xc_roomtype_fq"."fq_rate_plan_code" IS '番茄价格计划code';
COMMENT ON COLUMN "public"."xc_roomtype_fq"."room_type_code" IS 'otaid_roomTypeId';
COMMENT ON COLUMN "public"."xc_roomtype_fq"."inn_code" IS 'otaId_innId';

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Indexes structure for table bang_inn
-- ----------------------------
CREATE INDEX "bang_inn_company_id_inn_id_idx" ON "public"."bang_inn" USING btree (company_id, inn_id, inn_label_id, sj, id NULLS FIRST);
CREATE INDEX "bang_inn_id_key" ON "public"."bang_inn" USING btree (inn_id);
CREATE INDEX "bang_inn_name_key" ON "public"."bang_inn" USING btree (inn_name);

-- ----------------------------
-- Uniques structure for table bang_inn
-- ----------------------------
ALTER TABLE "public"."bang_inn" ADD UNIQUE ("company_id", "inn_id");

-- ----------------------------
-- Primary Key structure for table bang_inn
-- ----------------------------
ALTER TABLE "public"."bang_inn" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bang_inn_to_qunar_city
-- ----------------------------
ALTER TABLE "public"."bang_inn_to_qunar_city" ADD PRIMARY KEY ("id");

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
-- Primary Key structure for table ctrip_homestay_room_ref
-- ----------------------------
ALTER TABLE "public"."ctrip_homestay_room_ref" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ctrip_parent_hotel
-- ----------------------------
CREATE INDEX "xc_parent_hotel_hotel_name_child_hotel_id_parent_hotel_id_idx" ON "public"."ctrip_parent_hotel" USING btree (hotel_name, child_hotel_id, parent_hotel_id);

-- ----------------------------
-- Primary Key structure for table ctrip_parent_hotel
-- ----------------------------
ALTER TABLE "public"."ctrip_parent_hotel" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ctrip_parent_room
-- ----------------------------
CREATE INDEX "xc_parent_room_xc_prent_hotel_id_room_type_name_room_type_i_idx" ON "public"."ctrip_parent_room" USING btree (ctrip_prent_hotel_id, room_type_name, room_type_id);

-- ----------------------------
-- Primary Key structure for table ctrip_parent_room
-- ----------------------------
ALTER TABLE "public"."ctrip_parent_room" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dc_user
-- ----------------------------
ALTER TABLE "public"."dc_user" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dictionary
-- ----------------------------
ALTER TABLE "public"."dictionary" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table fc_area
-- ----------------------------
CREATE INDEX "fc_area_city_code_area_name_idx" ON "public"."fc_area" USING btree (city_code, area_name);

-- ----------------------------
-- Primary Key structure for table fc_area
-- ----------------------------
ALTER TABLE "public"."fc_area" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table fc_city
-- ----------------------------
CREATE INDEX "fc_city_province_code_city_code_idx" ON "public"."fc_city" USING btree (province_code, city_code);

-- ----------------------------
-- Primary Key structure for table fc_city
-- ----------------------------
ALTER TABLE "public"."fc_city" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table fc_hotel_info
-- ----------------------------
CREATE UNIQUE INDEX "hotel_id_idx_copy" ON "public"."fc_hotel_info" USING btree (hotel_id);

-- ----------------------------
-- Primary Key structure for table fc_hotel_info
-- ----------------------------
ALTER TABLE "public"."fc_hotel_info" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table fc_province
-- ----------------------------
CREATE INDEX "fc_province_province_code_idx" ON "public"."fc_province" USING btree (province_code);

-- ----------------------------
-- Primary Key structure for table fc_province
-- ----------------------------
ALTER TABLE "public"."fc_province" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table fc_room_type_info
-- ----------------------------
CREATE UNIQUE INDEX "hotel_id_and_room_type_id_idx_copy" ON "public"."fc_room_type_info" USING btree (hotel_id, room_type_id);

-- ----------------------------
-- Primary Key structure for table fc_room_type_info
-- ----------------------------
ALTER TABLE "public"."fc_room_type_info" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table fc_roomtype_fq
-- ----------------------------
CREATE INDEX "fc_roomtype_fq_inn_id_fc_hotel_id_company_id_ota_inn_ota_id_idx" ON "public"."fc_roomtype_fq" USING btree (inn_id, fc_hotel_id, company_id, ota_inn_ota_id, ota_info_id, fq_roomtype_id, fc_roomtype_id, rate_plan_id);

-- ----------------------------
-- Primary Key structure for table fc_roomtype_fq
-- ----------------------------
ALTER TABLE "public"."fc_roomtype_fq" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table inn_active
-- ----------------------------
CREATE INDEX "inn_active_inn_id_create_date_idx" ON "public"."inn_active" USING btree (inn_id, create_date);

-- ----------------------------
-- Primary Key structure for table inn_active
-- ----------------------------
ALTER TABLE "public"."inn_active" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table inn_change_price_msg
-- ----------------------------
CREATE INDEX "created_at_index_key" ON "public"."inn_change_price_msg" USING btree (create_date DESC NULLS LAST);
CREATE INDEX "msg_inn_id_index_key" ON "public"."inn_change_price_msg" USING btree (inn_id);

-- ----------------------------
-- Primary Key structure for table inn_change_price_msg
-- ----------------------------
ALTER TABLE "public"."inn_change_price_msg" ADD PRIMARY KEY ("id");

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
-- Indexes structure for table jw_inn_room_mapping
-- ----------------------------
CREATE INDEX "jw_inn_room_mapping_inn_id_room_type_id_inn_code_room_type__idx" ON "public"."jw_inn_room_mapping" USING btree (inn_id, room_type_id, inn_code, room_type_id_code);

-- ----------------------------
-- Primary Key structure for table jw_inn_room_mapping
-- ----------------------------
ALTER TABLE "public"."jw_inn_room_mapping" ADD PRIMARY KEY ("id");

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
-- Indexes structure for table order_config
-- ----------------------------
CREATE INDEX "order_config_inn_id_company_id_otainfo_id_idx" ON "public"."order_config" USING btree (inn_id, company_id, ota_info_id);

-- ----------------------------
-- Primary Key structure for table order_config
-- ----------------------------
ALTER TABLE "public"."order_config" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table order_operation_record
-- ----------------------------
ALTER TABLE "public"."order_operation_record" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table order_other_price
-- ----------------------------
CREATE INDEX "order_id_order_other_price_key" ON "public"."order_other_price" USING btree (order_id);

-- ----------------------------
-- Primary Key structure for table order_other_price
-- ----------------------------
ALTER TABLE "public"."order_other_price" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table order_source
-- ----------------------------
CREATE INDEX "order_source_inn_id_idx" ON "public"."order_source" USING btree (inn_id);

-- ----------------------------
-- Primary Key structure for table order_source
-- ----------------------------
ALTER TABLE "public"."order_source" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_bang_inn_room
-- ----------------------------
CREATE INDEX "ota_bang_inn_room_company_id_ota_wg_id_idx" ON "public"."ota_bang_inn_room" USING btree (company_id, ota_wg_id, rid, inn_id, room_type_id);

-- ----------------------------
-- Primary Key structure for table ota_bang_inn_room
-- ----------------------------
ALTER TABLE "public"."ota_bang_inn_room" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_commission_percent
-- ----------------------------
CREATE INDEX "ota_commission_percent_company_id_sjia_model_ota_id_operate_idx" ON "public"."ota_commission_percent" USING btree (company_id, sjia_model, ota_id, operate_type);

-- ----------------------------
-- Primary Key structure for table ota_commission_percent
-- ----------------------------
ALTER TABLE "public"."ota_commission_percent" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_daily_infos
-- ----------------------------
CREATE INDEX "order_id_ota_daily_infos_key" ON "public"."ota_daily_infos" USING btree (order_id);

-- ----------------------------
-- Primary Key structure for table ota_daily_infos
-- ----------------------------
ALTER TABLE "public"."ota_daily_infos" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_exception_order
-- ----------------------------
ALTER TABLE "public"."ota_exception_order" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_info
-- ----------------------------
ALTER TABLE "public"."ota_info" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_inn_ota
-- ----------------------------
CREATE INDEX "ota_inn_ota_wg_hid_company_id_bang_inn_id_ota_info_id_idx" ON "public"."ota_inn_ota" USING btree (wg_hid, company_id, bang_inn_id, ota_info_id, sj);

-- ----------------------------
-- Uniques structure for table ota_inn_ota
-- ----------------------------
ALTER TABLE "public"."ota_inn_ota" ADD UNIQUE ("wg_hid", "company_id", "ota_id", "bang_inn_id", "ota_info_id", "inn_id");

-- ----------------------------
-- Primary Key structure for table ota_inn_ota
-- ----------------------------
ALTER TABLE "public"."ota_inn_ota" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_inn_roomtype_goods
-- ----------------------------
CREATE INDEX "ota_inn_roomtype_goods_rid_idx" ON "public"."ota_inn_roomtype_goods" USING btree (rid, room_type_id, ota_wg_id);

-- ----------------------------
-- Primary Key structure for table ota_inn_roomtype_goods
-- ----------------------------
ALTER TABLE "public"."ota_inn_roomtype_goods" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_order_guests
-- ----------------------------
ALTER TABLE "public"."ota_order_guests" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_pending_order
-- ----------------------------
ALTER TABLE "public"."ota_pending_order" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_price_model
-- ----------------------------
CREATE INDEX "ota_price_model_ota_wg_id_idx" ON "public"."ota_price_model" USING btree (ota_wg_id);

-- ----------------------------
-- Primary Key structure for table ota_price_model
-- ----------------------------
ALTER TABLE "public"."ota_price_model" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_rate_plan
-- ----------------------------
ALTER TABLE "public"."ota_rate_plan" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_room_price
-- ----------------------------
ALTER TABLE "public"."ota_room_price" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ota_taobao_area
-- ----------------------------
ALTER TABLE "public"."ota_taobao_area" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ota_toms_order
-- ----------------------------
CREATE INDEX "inn_id_toms_order_key" ON "public"."ota_toms_order" USING btree (inn_id);
CREATE INDEX "ota_toms_order_order_status_inn_id_room_type_id_ota_room_ty_idx" ON "public"."ota_toms_order" USING btree (order_status, inn_id, room_type_id, ota_room_type_id, company_id);
CREATE INDEX "search_toms_order_leave_time_key" ON "public"."ota_toms_order" USING btree (company_id, order_time, channel_source);
CREATE INDEX "search_toms_order_live_time_key" ON "public"."ota_toms_order" USING btree (company_id, live_time, channel_source);
CREATE INDEX "search_toms_order_time_key" ON "public"."ota_toms_order" USING btree (company_id, leave_time, channel_source);

-- ----------------------------
-- Primary Key structure for table ota_toms_order
-- ----------------------------
ALTER TABLE "public"."ota_toms_order" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table otainfo_company_ref
-- ----------------------------
CREATE INDEX "otainfo_company_ref_ota_info_id_company_id_idx" ON "public"."otainfo_company_ref" USING btree (ota_info_id, company_id);

-- ----------------------------
-- Primary Key structure for table otainfo_company_ref
-- ----------------------------
ALTER TABLE "public"."otainfo_company_ref" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table other_consumer_function
-- ----------------------------
CREATE INDEX "other_consumer_function_company_id_idx" ON "public"."other_consumer_function" USING btree (company_id);

-- ----------------------------
-- Primary Key structure for table other_consumer_function
-- ----------------------------
ALTER TABLE "public"."other_consumer_function" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table other_consumer_info
-- ----------------------------
CREATE INDEX "other_consumer_info_company_id_parent_id_idx" ON "public"."other_consumer_info" USING btree (company_id, parent_id);

-- ----------------------------
-- Primary Key structure for table other_consumer_info
-- ----------------------------
ALTER TABLE "public"."other_consumer_info" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table qunar_city_info
-- ----------------------------
ALTER TABLE "public"."qunar_city_info" ADD PRIMARY KEY ("id");

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
-- Indexes structure for table timer_rate_price
-- ----------------------------
CREATE INDEX "timer_rate_price_company_id_ota_info_id_account_id_idx" ON "public"."timer_rate_price" USING btree (company_id, ota_info_id, room_type_id, deleted);

-- ----------------------------
-- Primary Key structure for table timer_rate_price
-- ----------------------------
ALTER TABLE "public"."timer_rate_price" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table toms_myself_channel
-- ----------------------------
ALTER TABLE "public"."toms_myself_channel" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table toms_pms_channel_name
-- ----------------------------
ALTER TABLE "public"."toms_pms_channel_name" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user_info
-- ----------------------------
CREATE INDEX "user_role_idx" ON "public"."user_info" USING btree (company_id, role_id);

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table xc_roomtype_fq
-- ----------------------------
CREATE INDEX "xc_roomtype_fq_inn_id_xc_child_hotel_id_company_id_fq_roomt_idx" ON "public"."xc_roomtype_fq" USING btree (inn_id, xc_child_hotel_id, company_id, fq_roomtype_id, xc_child_roomtype_id);

-- ----------------------------
-- Primary Key structure for table xc_roomtype_fq
-- ----------------------------
ALTER TABLE "public"."xc_roomtype_fq" ADD PRIMARY KEY ("id");
