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

Date: 2016-11-09 10:33:25
*/


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
-- Records of company
-- ----------------------------
INSERT INTO "public"."company" VALUES ('0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', null, '2015-11-19', '2015-11-19', null, '1', 'sds1121是滴是滴', '0', '92793998', '1', '111', '1212', '1212', 'OPEN');
INSERT INTO "public"."company" VALUES ('17ef83c0-89bc-4947-afcc-ea55f762cb1b', null, '2015-12-04', '2015-12-04', null, '0', 'egg', '0', '68051118', '1', '888', 'xc', '111111', 'SALE');
INSERT INTO "public"."company" VALUES ('19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', null, '2015-07-03', '2015-07-15', null, '3', '成都番茄来了技术有限公司', '0', '54041688', '1', '101', 'CS', 'cs123', null);
INSERT INTO "public"."company" VALUES ('1e72f3a4-6e0e-4f1e-8562-5732906dc728', null, '2015-08-26', '2015-11-19', null, '7', '第四城', '0', '34203727', '1', '903', 'TB', 'tb', 'SALE');
INSERT INTO "public"."company" VALUES ('33592699-8f9a-4e39-8082-110d33afb6c0', null, '2016-09-08', '2016-09-08', null, '0', '携程民宿', '0', '45470283', '1', '936', 'XCMS', 'XCMS654', 'SALE');
INSERT INTO "public"."company" VALUES ('43d60bf4-a467-4f88-b451-cb45d346a0b7', null, '2016-03-09', '2016-03-09', null, '0', '测试用', '0', '68931469', '1', '901', 'MTB', 'mt911', 'OPEN');
INSERT INTO "public"."company" VALUES ('5a779fe6-4fb6-46cc-9d35-d0683719f78a', null, '2016-08-17', '2016-08-17', null, '0', '番茄精品活动', '0', '95293351', '1', '922', 'active', 'active922', 'SALE');
INSERT INTO "public"."company" VALUES ('60978e73-851b-429d-9cf4-415300a64739', null, '2015-09-11', '2015-11-20', null, '5', '番茄运营', '0', '56425584', '1', '906', 'FQFCDX', 'fqfcdx', 'SALE');
INSERT INTO "public"."company" VALUES ('60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', null, '2015-07-24', '2015-07-24', null, '0', '深圳笃行客', '1', '75010522', '1', '915', 'ota915', 'OTA915', null);
INSERT INTO "public"."company" VALUES ('6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', null, '2015-11-19', '2015-11-19', null, '1', 'sdsd221zk', '0', '104509771', '1', '232', '1121q', 'sdsd', null);
INSERT INTO "public"."company" VALUES ('70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', null, '2016-03-16', '2016-04-12', null, '2', '番茄信用住', '0', '105752558', '1', '106', 'XYZ', 'xyz106', 'CREDIT');
INSERT INTO "public"."company" VALUES ('7c872002-cbee-4aab-8161-2a0b64609304', null, '2015-12-04', '2015-12-04', null, '1', 'egg', '0', '94804456', '1', '999', 'qunaer', '111111', 'OPEN');
INSERT INTO "public"."company" VALUES ('8356b7d9-9d6e-4bb1-a99d-e4b156a09280', null, '2016-06-07', '2016-06-07', null, '0', '胡夫人', '1', '69779149', '1', '123', '胡', '夫人', 'OPEN');
INSERT INTO "public"."company" VALUES ('97356a44-1dd5-47b7-a013-aec959427629', null, '2015-11-19', '2015-11-19', null, '0', 'dsds', '0', '24796464', '1', '56', 'dsd', '111', null);
INSERT INTO "public"."company" VALUES ('CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', null, '2015-06-30', '2015-07-02', null, '4', '向左时光连锁', '0', '58412369', '1', '912', 'QKZ', 'qkz912', null);
INSERT INTO "public"."company" VALUES ('d0392bc8-131c-48a4-846e-c81c66097781', null, '2015-05-15', '2016-05-12', null, '2', '丽江漫途信息技术有限公司', '0', '56458653', '1', '901', 'MTB', 'mt911', 'OPEN');
INSERT INTO "public"."company" VALUES ('d0392bc8-131c-8989-846e-c81c66011111', null, '2015-07-13', '2015-11-20', null, '2', '飞鸟', '0', '89894098', '1', '903', 'TB', 'tb', 'SALE');
INSERT INTO "public"."company" VALUES ('d6d5316c-4756-426e-a019-6909a84fa8aa', null, '2015-07-24', '2015-07-24', null, '0', '深圳笃行客', '0', '85715336', '1', '915', 'ota915', 'ota915', null);
INSERT INTO "public"."company" VALUES ('d82f3bce-b1de-4d31-a582-f2695397bbbd', null, '2016-04-12', '2016-04-12', null, '0', '车上1', '0', '62739164', '1', '222', '大时代', '1545', 'CREDIT');
INSERT INTO "public"."company" VALUES ('e9781645-ccee-4eaf-8808-d09cc76a61e2', null, '2016-04-15', '2016-04-15', null, '0', '去哪儿', '0', '18085293', '1', '904', 'QUNAR', 'qunar123', 'SALE');
INSERT INTO "public"."company" VALUES ('fanqie-shoudong-init', null, '2015-06-30', '2015-06-30', null, '0', '亲的客栈', '0', '20150630', '1', '912', 'QKZ', 'qkz912', 'OPEN');

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
-- Records of company_permission
-- ----------------------------
INSERT INTO "public"."company_permission" VALUES ('02008746-1a4d-4eb7-acc7-ceed7ee9f93c', '60978e73-851b-429d-9cf4-415300a64739', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('021091b2-0b1d-4d85-8007-58a7b8ebeed3', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('03d48fdb-e1e9-487c-b4e6-606146167e0a', '60978e73-851b-429d-9cf4-415300a64739', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('05470ee9-c74a-4355-8dd4-50b3eb31f7a5', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('090473cc-be33-410b-89c0-cf4d6f479402', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('09394bb5-754c-4c51-889c-baa8c693a70f', 'fanqie-shoudong-init', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('0bfc7d16-72b2-4caf-90a1-e7110cab9b8c', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('0ced6fb2-22e6-4fc6-b558-5e8612ff4a1f', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('0d3b1c0d-f743-4866-914d-fba374052242', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('0ebd8f4a-5568-4974-86af-f14829a363a3', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('0fa66a74-3740-4491-92bc-b2c98df5c4ed', '7c872002-cbee-4aab-8161-2a0b64609304', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('0ffd0bad-bd6b-461f-bd2b-cf35e7d377ae', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('1040204b-76f8-487c-9e4f-a466f4eec039', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('109f17c7-d95d-4bf3-9f4f-2758053606b2', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('10a1707f-286e-446e-8f45-0572f122f95d', '60978e73-851b-429d-9cf4-415300a64739', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('111615f1-26ff-4c68-8ddb-42ca59efd223', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '9414cf03-338c-4994-890e-a91463e4d792', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('113', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('11a6a347-9bd4-4203-94b7-e64ea21a5cae', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('11c30b4a-0b42-4278-96a7-ed05d0e59620', '97356a44-1dd5-47b7-a013-aec959427629', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('122c009b-b417-4239-8d0d-0f0ed838df4a', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('12ac63de-82a9-408a-b139-e1809280a47a', 'd6d5316c-4756-426e-a019-6909a84fa8aa', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('12c1b754-2a20-45b2-9e5e-4b27a3373635', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('134a0fe4-0aa5-44bb-8cd4-3759b643af6a', 'd0392bc8-131c-8989-846e-c81c66011111', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('148c967d-b08b-4d90-a89f-29b4e66c50e7', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('167f335a-6a53-419e-87ec-b75e8f116d97', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('178e947e-a96d-4932-9c19-f8a84709f8d1', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('17dc21ab-ec92-4e24-b879-3669646e5dd4', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('187af15d-b706-469c-9375-d66b92a8d62f', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('188ba18f-a3c7-421f-b864-9a692d53cb25', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('194d146b-1936-4c32-9863-7e3715783628', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('19bca1ff-2b71-43b1-8222-b3c36015b55b', 'd0392bc8-131c-8989-846e-c81c66011111', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('1c545f8f-3a05-4c57-89d8-6022c64e7e4f', '97356a44-1dd5-47b7-a013-aec959427629', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('1cfcb8da-c190-4ee2-ae22-530d1d4885a6', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('1e76e0cc-bfcf-4bf2-b2df-51727baaa6d0', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('1ec21a51-3cd2-4543-a371-d773b14157e5', '60978e73-851b-429d-9cf4-415300a64739', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('1fe44eb5-50c0-44db-89e9-2d14bb1d640b', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('20', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('20152870-8ce1-47c9-a49f-e3f0425a9bac', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('21', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('218584f4-dcf3-4a8a-bd64-021e79173a95', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('22', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('23', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('24', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('25', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('252ddf7a-a275-480a-bd46-207f72cdd149', '7c872002-cbee-4aab-8161-2a0b64609304', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('25cae916-1cc5-42df-b46e-5a33cf4b94de', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('25dfe337-0aca-487e-b251-dd2913707fc7', 'd0392bc8-131c-48a4-846e-c81c66097781', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('26', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('266577e1-6917-43e9-b307-8390dbf9771c', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'wewewss', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('2698e1d9-77b4-46c3-97b4-6326dc56e388', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('26a55515-4e3b-416d-9a21-384a394ffa97', 'd0392bc8-131c-8989-846e-c81c66011111', '9414cf03-338c-4994-890e-a91463e4d792', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('27', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('273feb0b-e798-47dc-b3cd-b879b8f27468', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('27b71f2c-b657-4c4a-807f-8201fe5afec9', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('27fa3b27-d78e-42c1-be44-8a5917ec0bf8', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('28', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-01', '2015-07-01');
INSERT INTO "public"."company_permission" VALUES ('28ed1f12-790e-4992-bc93-482aae5055ab', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('292ddfae-4eaf-421f-a453-21a23a3832e2', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('2994507e-b7f9-4c56-bda3-fa290cc0d814', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('299bb6d1-d59e-453c-8004-5e376af03e12', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('2a2876c2-619f-4f3d-8229-2d214affc25b', 'd0392bc8-131c-48a4-846e-c81c66097781', '65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('2aaab418-a710-4ae5-975b-6a681731c39d', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('2c67c2f2-3732-440e-8120-c336bc322636', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('2e17e8f3-4b45-4769-8847-ce20e869a44e', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('2e2378cc-8b9c-460f-8a82-0840e127c0d1', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('2e962e99-edcb-47d3-b17d-b0462fafea04', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('2ecbe26a-3b09-4d80-b02c-42d8f957de55', '7c872002-cbee-4aab-8161-2a0b64609304', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('306d7b3f-4fc9-4fa0-a760-53956e2d66f9', 'fanqie-shoudong-init', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('30f1476b-a5d2-40b5-bcd6-8c81fe3388d6', 'fanqie-shoudong-init', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('34625714-f837-4b5b-9d06-d7ab6d47b2c4', 'd0392bc8-131c-8989-846e-c81c66011111', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('353a71b0-1251-4f8a-b83d-71fbb8a95702', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('3573ee53-4a26-42ba-a5c2-12adde46cae0', '33592699-8f9a-4e39-8082-110d33afb6c0', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-09-08', '2016-09-08');
INSERT INTO "public"."company_permission" VALUES ('3659b0ea-b11d-4ed6-aae7-f3abd57aef8c', '7c872002-cbee-4aab-8161-2a0b64609304', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('36db59a3-1eaa-4662-8064-f90b82d4cb54', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('380e443f-5864-4d5c-8605-679b3ebe9494', 'd0392bc8-131c-48a4-846e-c81c66097781', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('38ce312f-be9b-432e-a685-bc9c93a596c7', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('3a8d770a-8bf6-4bde-b883-fac4738aca4d', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('3aa3e30b-6e3a-485a-b2db-88425685cda9', 'd0392bc8-131c-48a4-846e-c81c66097781', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('3ab3e386-e3bb-4c55-a335-9edfefb52921', 'fanqie-shoudong-init', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('3af1da3a-029b-4de8-9c50-bd07b62b6849', '7c872002-cbee-4aab-8161-2a0b64609304', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('3b4a5f3c-e7ee-4158-9a1f-32f38c55b509', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('3b9deb9e-9b0a-4414-8a48-7c793c94fbd7', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('3d536a7b-84e9-41f8-b7be-fafa115c397f', '60978e73-851b-429d-9cf4-415300a64739', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('3e5175ad-c2e8-45e8-ac98-81e0ee4ddb2f', '7c872002-cbee-4aab-8161-2a0b64609304', '9414cf03-338c-4994-890e-a91463e4d792', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('3f8fde0b-f03a-411b-b7d7-19bd0ea2dcfc', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('3f9167c2-6c0e-404f-af31-bd62f29cb357', 'd0392bc8-131c-8989-846e-c81c66011111', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('4000fb27-9503-4861-943c-fc1f69437bf6', 'd6d5316c-4756-426e-a019-6909a84fa8aa', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('4004b3c7-72f8-4b4d-ba64-b69709461faf', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '9414cf03-338c-4994-890e-a91463e4d792', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('410ab9c7-5066-4251-8f54-27085f72a970', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('4139c8b5-82e8-4889-87f5-29c21de968e8', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('41831e01-297b-4992-982b-6ba3c03893a1', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('428428ee-2fda-4fd4-9fe0-9ca044b14156', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('42a75a6f-faf5-408e-b678-26cee13c1e3d', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('44823e5c-e3b1-40ef-b255-75a03822c8d6', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('448f353d-d87d-4268-8fab-4a6afbf730b6', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('44e51eea-4045-4964-9dd0-f8936bc6c57d', '97356a44-1dd5-47b7-a013-aec959427629', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('457a805d-b6df-4c16-8433-3673b6cf6101', '60978e73-851b-429d-9cf4-415300a64739', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('469990b3-f9b6-4cff-8a6c-9e549643513c', '60978e73-851b-429d-9cf4-415300a64739', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('46ebef3d-7898-4a54-b242-6ea00ded97e3', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('46f93338-28f2-4053-beea-d4d5a601d097', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('47242f65-41c4-439f-a195-a974af27b476', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('475f90d6-3262-40fa-969f-a53f85c79295', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('479839f8-3548-4ab4-b036-ec762495ef28', '97356a44-1dd5-47b7-a013-aec959427629', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('4949102a-1f64-4a3c-8243-6d83c930cf1f', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('49f1807f-bf40-4c1c-85dc-6174b7b6c177', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('4b1f2f67-c22b-4740-aa84-902315a6e837', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('4ba70dfd-5478-49c9-a5cb-ac252ce6da66', '43d60bf4-a467-4f88-b451-cb45d346a0b7', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('4bdb17fa-4022-430d-8134-34ba8f64156f', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '9414cf03-338c-4994-890e-a91463e4d792', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('4ccfbe66-ce3f-46ac-8284-edd1b7ae3fdc', '97356a44-1dd5-47b7-a013-aec959427629', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('4dac0f21-25b7-48c4-93df-b7facde3cfe8', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('4e1994ab-a473-4ac5-82d6-1be4faa552d2', '7c872002-cbee-4aab-8161-2a0b64609304', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('4e5313a7-55fc-452a-8218-c20f5d0848ca', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('504229d7-2718-4386-a277-c73c0d6098bb', '43d60bf4-a467-4f88-b451-cb45d346a0b7', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('513978f7-6601-4402-93b7-e0f4726c29e8', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('519ad2a3-b04f-4bf6-b224-b6967c5a08a8', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('52cd55fd-f463-48cc-bbd6-f0d99076d599', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('52d1b2e8-9795-48fd-9a77-4773ee5f10cc', 'fanqie-shoudong-init', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('53514843-94ab-48a2-92a0-b46217104060', 'd0392bc8-131c-48a4-846e-c81c66097781', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('5410e9d1-006b-46b2-a9fd-347b8b837233', '60978e73-851b-429d-9cf4-415300a64739', '9414cf03-338c-4994-890e-a91463e4d792', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('5598b7a7-de4f-4b68-be29-7bb0f5757d25', 'd0392bc8-131c-8989-846e-c81c66011111', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('562a5275-9614-45e5-ab82-9b78405e1a32', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('5703eec6-c526-46dd-bb33-10de67130b73', '60978e73-851b-429d-9cf4-415300a64739', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('57bb741f-d5f8-4a7c-aafd-3f0978ca650c', 'd0392bc8-131c-8989-846e-c81c66011111', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('58f4ec78-7ca9-4fae-b6de-d09524e8b3a3', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('5936ec9d-ea37-4252-ad85-fe6ea035fe53', 'd6d5316c-4756-426e-a019-6909a84fa8aa', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('5a22162a-28a6-4544-bef9-386fadb46d24', '60978e73-851b-429d-9cf4-415300a64739', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('5c50d8a8-0e6f-4b68-9714-cf75935317da', '97356a44-1dd5-47b7-a013-aec959427629', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('5d214fa7-04d0-4e7d-a42d-48a0b1e4615e', 'fanqie-shoudong-init', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('5dcd1c52-2015-4bff-a9e6-209f6f331eb1', 'd0392bc8-131c-8989-846e-c81c66011111', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('5ddd47f6-d996-472a-b131-0b36e23567e7', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('5e6509e6-46e1-45d5-8a4b-1963d46d16bb', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('5ee21df1-6cd0-4584-8d27-bc756668a48b', 'd0392bc8-131c-8989-846e-c81c66011111', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('5f131458-b685-4a8b-9f52-320eedadf515', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('5f7d361f-61d2-4600-8641-38964c66ae9f', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('605d2f59-33f4-4a0f-af69-89d7a65db8cd', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('6281afed-856a-4a6f-95cc-438d204ca42a', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '9414cf03-338c-4994-890e-a91463e4d792', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('6346df4e-495d-4fe6-a57a-b4646b81a98a', 'd0392bc8-131c-8989-846e-c81c66011111', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('66f9f57d-7ff4-49c5-8dd0-55bfd7c396b5', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('6811c3f6-d88e-472b-b088-694c10466e2c', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('6855c95a-169d-4705-858c-d4c9640813dd', 'd0392bc8-131c-48a4-846e-c81c66097781', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('696fcd67-2b4d-4947-b9dd-8dcb252c9b4e', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('6990141b-772b-4266-a4df-49bd2df42c1b', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('69d246e8-eeb2-4247-9b3d-4deb8d41c59a', '60978e73-851b-429d-9cf4-415300a64739', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('6ad3b3b7-a71e-4157-9750-990e1a818c73', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('6b2b79e0-780e-4fcf-b3f1-4a8f87b36941', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('6bad0c38-881c-479a-b5cd-c1cedc7d3484', 'fanqie-shoudong-init', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('6ca42564-f5a2-4f71-b9e4-599feaae451e', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('6cc796b9-a1f3-4da6-bd36-1f2ad35ddf53', '7c872002-cbee-4aab-8161-2a0b64609304', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('6d14d66c-3e29-4c27-bf66-3d75e42b4c5c', '43d60bf4-a467-4f88-b451-cb45d346a0b7', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('6d44cd58-6119-4811-a55d-90639966e216', 'd0392bc8-131c-48a4-846e-c81c66097781', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('6eec1ad2-9824-4f8e-b041-46c1e880fc8d', '33592699-8f9a-4e39-8082-110d33afb6c0', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-09-08', '2016-09-08');
INSERT INTO "public"."company_permission" VALUES ('71126738-20a6-451d-b246-40bc4f07ca1e', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('725eca12-f31a-45d5-8c3f-97cac629ab07', '97356a44-1dd5-47b7-a013-aec959427629', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('7297b660-34fd-4da5-920e-f0b83b2ba631', 'd6d5316c-4756-426e-a019-6909a84fa8aa', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('73062e31-be37-4dd6-a83b-09ec0a7b0847', '97356a44-1dd5-47b7-a013-aec959427629', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('732f691f-b252-4031-887b-605456058691', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('7361bebc-635e-472b-b09d-7b0852d8f1d7', 'd0392bc8-131c-48a4-846e-c81c66097781', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('73e56fcc-bdb0-4aea-9ab9-f8f0b7c2a413', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('743265a3-daba-41cd-be60-63412f6f87f2', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('75661ff3-bc54-4657-9d5a-071d726c8648', '7c872002-cbee-4aab-8161-2a0b64609304', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('758de271-e9ba-45ea-9aa2-270f6017530b', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('771fc62c-15bc-472f-89c0-b253080181c8', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('77f31775-c88d-43ac-b3ba-31c17b407166', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('78031db5-9617-42cd-9948-9067ca922403', '7c872002-cbee-4aab-8161-2a0b64609304', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('780973db-2e04-43c9-921a-1a74e86d4dc1', 'd0392bc8-131c-8989-846e-c81c66011111', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('7ac63683-d0f9-4b42-a4de-76901f7899a1', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('7ad1cc11-e38c-464b-b0b4-e182698dca0c', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('7c056c91-6b5a-4398-8e58-ff25d4119f33', 'd0392bc8-131c-8989-846e-c81c66011111', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('7d060e1e-b2e9-48de-be2d-35d15467408a', 'fanqie-shoudong-init', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('7eb086a7-ae8e-42d8-adb3-10ce2d9a23ea', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('7f2f952b-5161-49fc-852c-2fba07c226a6', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('7f593e66-4fd9-4f9b-a037-b282a581bc87', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('8072eb90-7efd-4ac6-9833-bd85e0316ec4', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('81c8b1aa-fcf5-493d-8df1-6e1a657474c6', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('8361a8cd-e44f-4fb4-b8c2-84ab31612a08', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('83ec82a9-7d54-4a3f-a4e5-ff1dde4dfbac', 'd0392bc8-131c-48a4-846e-c81c66097781', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('84072f9a-26b9-40bb-acaa-4ee044421327', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('850ffe4d-dc64-4a57-97ef-c0e7ffe69447', 'd0392bc8-131c-8989-846e-c81c66011111', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('8524fbd4-a5be-4a47-a33b-ba370783ba2c', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('858b50d5-0307-4b16-883f-8787f42e4070', '7c872002-cbee-4aab-8161-2a0b64609304', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('86995bdf-ba95-49fc-bcd6-94b02caa2e33', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('869da865-ab46-48d1-8cb7-6292909f2c65', 'd0392bc8-131c-8989-846e-c81c66011111', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('877df2ce-fb88-402b-bfdc-f981a0c09ea2', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('8819fa27-2073-4ebf-8493-7e12fcc6fc1c', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('8940069b-dc74-48bc-b7bb-a8e208f8121b', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('8aa8a169-7c66-45cb-9b1f-05a2d73d38d3', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('8b895bfe-683b-457c-957c-9d02186e3c0a', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('8bdbd290-db13-48e0-a1ae-7b8474d74488', '97356a44-1dd5-47b7-a013-aec959427629', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('8c2b5490-5993-4fab-afa7-11921fb73b3c', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '9414cf03-338c-4994-890e-a91463e4d792', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('8dac733b-2fd8-46b2-917f-ebcdea75167e', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('8db1c5b0-3122-45d5-8789-8b7790f4fd69', '60978e73-851b-429d-9cf4-415300a64739', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('8dd8c075-13d9-4a27-8289-9bee737b84ee', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('8e45c219-96e3-4d8f-9373-2a700e157234', '7c872002-cbee-4aab-8161-2a0b64609304', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('8e64ba0e-159d-4a83-b271-7fa2b5d69253', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('8ebf9df6-d0d8-4f85-830e-096501daed3a', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('8f3dec11-ee0e-4394-924e-28c82b785138', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('91740a18-62f2-40d3-a0c1-197c13d7cc16', '97356a44-1dd5-47b7-a013-aec959427629', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('91d03556-f65b-4695-97a8-6ca303458e7a', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('91e1fea7-3f3a-4484-b680-c9a041d892ab', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('92e32d29-3822-4a9d-ae7a-adc3a82eca5e', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '9414cf03-338c-4994-890e-a91463e4d792', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('9483adce-74c4-4571-9991-d31f5d4e76b0', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('9533e0ad-6e0d-4105-bd93-b8366c84e0cf', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('95c3a709-e631-4627-ba66-5312727d7b2c', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('95e87dad-3c97-4f77-b18a-178ed39411e8', 'd0392bc8-131c-48a4-846e-c81c66097781', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('95f5b90d-724b-4d1f-97fc-5ccd895daaa0', 'd0392bc8-131c-8989-846e-c81c66011111', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('962b6860-d311-4016-b664-b962321f3016', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('97004ec3-36dd-415a-8d98-61c2c58d27c7', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('981580ce-2282-48c7-bb74-2b63bbca3d64', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('998cd833-ca3d-4b0d-96ae-77c82896c122', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('9a0ba7a6-58d6-4958-afcd-234065cf0ef0', 'd0392bc8-131c-8989-846e-c81c66011111', 'd105e433-477e-48c0-8405-a5ea8d0d8de9', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('9a782f08-c843-4b17-9c43-9d40ab50b7f9', 'd0392bc8-131c-48a4-846e-c81c66097781', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('9bad96b0-1b1b-4a32-864a-37f6e5523240', '60978e73-851b-429d-9cf4-415300a64739', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('9c460f93-4441-4e4a-8ac4-98ecc4566914', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('9c8b415f-2f6e-4c52-b9d1-adf2c022df7f', '97356a44-1dd5-47b7-a013-aec959427629', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('9d235235-73ca-44bf-a08f-a9dd148551c3', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('9f386e4e-78bb-4beb-9e62-57bba31b5931', 'd0392bc8-131c-48a4-846e-c81c66097781', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('a10e2324-fe95-4002-907a-4aadd4fb6836', 'd6d5316c-4756-426e-a019-6909a84fa8aa', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('a11a1710-177c-4397-af49-4aa22126600e', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('a123b8b8-7b9c-4831-8d08-880b7d1327e9', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('a351e5cb-212e-4527-a325-a5494ab70b7f', '60978e73-851b-429d-9cf4-415300a64739', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('a4b7650a-e4b4-4019-9681-8947fbad41c6', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('a4c25429-ef1f-4b39-a066-396599314290', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('a535a7b1-b8f7-4d3b-be3e-60dab81efcd8', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('a5da4fae-d3db-4f48-baae-ffaf91ca1dcf', 'd0392bc8-131c-8989-846e-c81c66011111', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('a6e7d538-0747-4fee-9160-fb146d0bc5e9', 'd0392bc8-131c-48a4-846e-c81c66097781', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('a7e1bd1a-c735-424c-8779-d74483cbd882', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('ab5186ba-d99e-4c62-afee-458485e0eaf3', 'fanqie-shoudong-init', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('ac75b4c3-d35b-4eac-8e58-51868eb0290c', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('af6f4dfc-6e0f-4f7f-b4af-75e4d40cce6c', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '9414cf03-338c-4994-890e-a91463e4d792', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('b077cf1c-024f-478b-b7a3-9262b59ad3f4', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('b0f18c03-0ad8-4dd4-b061-6741b2f27c84', '97356a44-1dd5-47b7-a013-aec959427629', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('b1466e2c-dfc6-46c1-98a8-661678b042c9', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('b1d6cc4c-ad0f-4350-b0e6-7c8d3089763e', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('b1ebde24-aef4-49af-9897-2657bb7ddd11', 'd6d5316c-4756-426e-a019-6909a84fa8aa', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('b23999be-f83a-4930-9305-500a42223b1e', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('b310f2d7-346f-4e78-9692-84ee3d09f7cc', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('b31d1495-f2f7-4b58-819d-d6ad733bb17c', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('b3aee7f5-5bab-4166-928e-d8fb6acc88dc', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('b4982c62-8d1c-4475-bfee-5fb56c6a79d3', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('b4ec8c1a-8f21-477e-8c1d-fb87295305ea', '7c872002-cbee-4aab-8161-2a0b64609304', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('b58816ea-307a-427c-bcea-1e9ec8b6c3d4', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('b72c4b97-1bbf-4d51-bf88-4b3ad62719a5', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('b8130a38-9576-46a6-a301-94d6f9ea8233', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('b90a498d-8544-4381-9e4b-8bf9a3c1f360', 'd0392bc8-131c-8989-846e-c81c66011111', 'wewewss', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('b9b08f6e-23db-445d-a1a7-e6a2e6a6ee90', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('bbb472e7-5ead-49ae-b972-6c179ca45d4c', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('be834937-1c2f-4445-8e33-3b87b0673b18', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('bee7fabe-b916-465b-b9e8-4b1e2b328ddd', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('bf4bd20b-203a-4ce3-976a-2f5e0eacdd02', '97356a44-1dd5-47b7-a013-aec959427629', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('bfc6b9ce-b0a2-4be5-bfb2-9eefdd798f51', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('c0021316-0e2e-4d3a-9a44-fe86ec49dbc7', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('c12aa58c-0577-4e3b-a5ef-238a6c88ed70', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('c3a1e1c2-b31f-45c2-9afb-a95d5058d5c5', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('c3e1d9f5-7626-4e3e-bd22-10878caea362', '97356a44-1dd5-47b7-a013-aec959427629', '9414cf03-338c-4994-890e-a91463e4d792', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('c4c72ea9-0178-4eb7-8060-29a78f32d393', '7c872002-cbee-4aab-8161-2a0b64609304', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('c5452715-4034-4219-b589-60b80c66c491', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('c698a27a-d0d9-42a7-a72a-9730ab05c337', 'fanqie-shoudong-init', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('c6b145bd-4fec-498f-88e8-b47a32919405', 'fanqie-shoudong-init', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('c7a5d78f-4962-438a-87d6-17ba36b885de', 'd6d5316c-4756-426e-a019-6909a84fa8aa', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('c840861d-6cf8-4a9a-8407-503e035f7087', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('c8b93e72-1c86-45d7-b5f5-249680688cc0', '97356a44-1dd5-47b7-a013-aec959427629', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('c9affffd-d2b9-469f-aaa6-ec357f26107e', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('cb392819-f19c-4215-aa85-26732fd40720', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'd105e433-477e-48c0-8405-a5ea8d0d8de9', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('cc13d235-19aa-4cc7-955a-2741a28322c5', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('cc46ad70-26a4-45fd-97cc-31d8007842ed', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('ccc8c9e3-4546-4e59-97d0-bb888a78ddf4', '7c872002-cbee-4aab-8161-2a0b64609304', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('cda36ff7-abd9-451e-a24a-3e9862a177d0', '60978e73-851b-429d-9cf4-415300a64739', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('ce2f9da9-2715-4d49-a34c-b885fcef89d7', '60978e73-851b-429d-9cf4-415300a64739', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('ce9c119a-3d16-4a06-8098-dcf794c002d6', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('ceedbace-7b63-43fb-8ba2-ee1c61c680c1', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('cf22c698-f5e1-44e3-9a70-39e2c9a0d350', 'd0392bc8-131c-48a4-846e-c81c66097781', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('cf5e7a2e-71d3-43c7-a688-ee7a4a91bd1c', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('d008ccd1-4a8d-45ce-9e05-f569389ad0bf', 'd0392bc8-131c-8989-846e-c81c66011111', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('d010eb35-e160-4d7e-8039-8c365087b5c0', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('d148619b-6569-49bf-992b-c0a80e761692', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('d1c8868f-de58-45db-a32b-ee8a2fffbd42', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('d339a2a2-a45f-4a65-a0d6-4a8cee4c4683', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('d499f0ba-d40c-4cdc-bc50-5c46fea9dfa0', '43d60bf4-a467-4f88-b451-cb45d346a0b7', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('d5eb9abe-3484-4090-b12d-82ccef5df7bf', '97356a44-1dd5-47b7-a013-aec959427629', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('d601a7f9-6b18-483d-a508-f401c39b09df', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('d7324c77-2ec7-47a9-9dbf-f5381bb00f25', '60978e73-851b-429d-9cf4-415300a64739', 'd105e433-477e-48c0-8405-a5ea8d0d8de9', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('d73424be-0cf4-4068-b336-bcee6da86ec1', '33592699-8f9a-4e39-8082-110d33afb6c0', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-09-08', '2016-09-08');
INSERT INTO "public"."company_permission" VALUES ('d88a8cbe-4f04-4419-bfa7-a656939ef290', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('d9aa188a-3172-485d-9298-f5d9a598204e', '43d60bf4-a467-4f88-b451-cb45d346a0b7', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('d9b6563c-dad8-4044-b06c-a6cc567b387f', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '9414cf03-338c-4994-890e-a91463e4d792', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('da8ce251-db6c-4aec-b0f1-8c056436137f', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('da95c324-11d9-4f49-9c80-8a458c0df87d', '60978e73-851b-429d-9cf4-415300a64739', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('dad60efb-e350-4288-bb0d-b31b6d8878ab', '60978e73-851b-429d-9cf4-415300a64739', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('db064e78-81a9-4759-84d7-e98a046c7710', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('dc45a22a-91ac-463d-9dbd-d4bc9ac8ab06', '43d60bf4-a467-4f88-b451-cb45d346a0b7', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-05-12', '2016-05-12');
INSERT INTO "public"."company_permission" VALUES ('dd76abde-2336-4f57-ac90-50bdc1ca180e', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('ded635eb-d5b8-4e0d-9b0a-6bfc33c58d0f', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('def7ab59-d70a-4aef-a29a-b330d0aeadbd', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('df1da776-afb7-4303-8add-b9799b025bbe', '7c872002-cbee-4aab-8161-2a0b64609304', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('df2a01fb-ef22-4526-98f3-1aa5d2956947', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('dff3962c-38d5-4687-889f-ea9a4107af81', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('e011849c-e210-4701-9010-51483d1c02f3', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('e2e27af6-88d1-48dc-a5c3-ef37d752054d', 'd0392bc8-131c-48a4-846e-c81c66097781', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('e3c57f96-c62e-4e0e-9b98-7abd85edbbfd', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('e45b16f1-cb07-4a58-a660-5ffaa2f27de5', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('e4a23282-922e-4da1-887e-3f106b7dbbed', 'd0392bc8-131c-8989-846e-c81c66011111', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('e4cb499e-477c-4967-b55b-a1ea30864354', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('e4e463b1-2682-4d8e-b6c4-69864420e4cb', '60978e73-851b-429d-9cf4-415300a64739', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('e51751e2-1280-4c03-8270-0d95fab86da5', 'd0392bc8-131c-48a4-846e-c81c66097781', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-07-13', '2016-07-13');
INSERT INTO "public"."company_permission" VALUES ('e6425d33-585a-425c-a129-0651b8049f8e', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-07-22', '2015-07-22');
INSERT INTO "public"."company_permission" VALUES ('e7177c31-ed87-4545-92c1-05d105922aa3', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('e7933d69-7b1b-4c5e-a823-38c33cce459a', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('e815ba0d-102c-4836-8ccc-39ce876d0ca1', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-04-15', '2016-04-15');
INSERT INTO "public"."company_permission" VALUES ('e86565fb-29e5-4395-bfd9-3244d8f779ac', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('e98f86cd-f83f-4099-9174-073151fcb764', '60978e73-851b-429d-9cf4-415300a64739', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('eac74c3a-9dce-469d-ba10-d797e5df66b1', 'd6d5316c-4756-426e-a019-6909a84fa8aa', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('ed66cf41-da7f-46b9-b290-571fc01cd56e', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('ed9e6a32-ffc8-412b-8115-f1a5cb060025', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('ee066975-5d6c-4158-860b-c823c3678b5a', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', '9414cf03-338c-4994-890e-a91463e4d792', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('ee4f7e62-c8c5-4947-8a04-c34e66f2f59d', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('ef15a947-5dc3-4ce9-b3c4-0197dd8f4555', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('ef7250b8-fe00-40ca-ad2a-097934d44e59', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('f072c64c-4840-438d-a0d9-0bbb46457419', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('f0e0483a-e6e2-44a4-9c11-7243eb93cb42', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('f0e0bfc5-4c48-45b5-949b-0abd4f323d99', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-06-07', '2016-06-07');
INSERT INTO "public"."company_permission" VALUES ('f35eac44-51f8-4d7f-b73e-beb6933b24fe', '97356a44-1dd5-47b7-a013-aec959427629', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-19', '2015-11-19');
INSERT INTO "public"."company_permission" VALUES ('f4555242-75d6-471a-b04a-43b702ecd26c', '7c872002-cbee-4aab-8161-2a0b64609304', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('f59aab30-90fc-46f4-9197-12ddd46a4aa9', 'fanqie-shoudong-init', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('f6730a98-b7b0-45fe-9dc0-e2c6da5c3103', '33592699-8f9a-4e39-8082-110d33afb6c0', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-09-08', '2016-09-08');
INSERT INTO "public"."company_permission" VALUES ('f6c6a102-d629-4fa7-8b68-9d66e822a7a7', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('f73578ce-2d20-4d29-8905-dafc0e0dab0b', 'd0392bc8-131c-8989-846e-c81c66011111', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('f76a0011-eb65-4a36-b00b-99abf89a6054', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-24', '2015-07-24');
INSERT INTO "public"."company_permission" VALUES ('f8590f03-6da8-42e6-87fc-b4aa3dc8d2c1', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '12121', '2016-09-06', '2016-09-06');
INSERT INTO "public"."company_permission" VALUES ('f87cfc15-349c-43f8-a83c-83ece5789193', 'd0392bc8-131c-8989-846e-c81c66011111', '65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', '2016-07-12', '2016-07-12');
INSERT INTO "public"."company_permission" VALUES ('f9a4e40d-beb7-4728-8f3b-3779ea30c6cd', '60978e73-851b-429d-9cf4-415300a64739', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-08-11', '2016-08-11');
INSERT INTO "public"."company_permission" VALUES ('fc1cdac1-c025-4b18-b2a0-e91b0fa3479e', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-12-04', '2015-12-04');
INSERT INTO "public"."company_permission" VALUES ('fcd7d249-2387-4bab-9902-7cbc42f0ce7d', 'fanqie-shoudong-init', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-30', '2015-11-30');
INSERT INTO "public"."company_permission" VALUES ('fd0c0969-87ef-4d83-83ea-4fe984c79b2f', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', '9414cf03-338c-4994-890e-a91463e4d792', '2015-08-26', '2015-08-26');
INSERT INTO "public"."company_permission" VALUES ('fda25038-8081-449c-9e6b-2cb5f7bd3f1b', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-04-12', '2016-04-12');
INSERT INTO "public"."company_permission" VALUES ('ffdf1760-fb6c-496a-925e-a5dd01929e40', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-08-17', '2016-08-17');
INSERT INTO "public"."company_permission" VALUES ('ffe1ece7-84d3-430b-b539-e0920ae501c7', 'fanqie-shoudong-init', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-30', '2015-11-30');

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
-- Records of otainfo_company_ref
-- ----------------------------
INSERT INTO "public"."otainfo_company_ref" VALUES ('04d4b064-1ffd-4ff8-a6f5-b9287a194da3', '5', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '1023319947', 'sandbox0872868bb898eb33554bfb1f4', '61020123fa63a57e1742134b442c4401f10e754c64a17723688597511', '1', null, 'MAI', 'CREDIT', '2016-03-18 11:13:09.49659', null, null, '2016-03-18 11:13:09.49659', null, 'tomasky');
INSERT INTO "public"."otainfo_company_ref" VALUES ('0dbbf5eb-d743-437c-af54-933a350dd640', '6', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', null, null, '91713731322844469A9CC1ADF36ABB0F', '1', null, 'MAI2DI', 'DEFAULT', '2016-05-24 17:52:02.174904', null, null, '2016-05-24 17:52:02.174904', null, null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('12121', '2', '60978e73-851b-429d-9cf4-415300a64739', 'S10030835', 'security_code_S10030835', null, '1', null, 'MAI2DI', null, '2015-10-01 10:24:42', null, null, null, null, null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('232', '1', 'd0392bc8-131c-8989-846e-c81c66011111', '1023192376', 'sandboxfbdf281c93b167601781cd228', '610292034b687595ff506a4df4ca1b387a29ea3a7267f7d3688597511', '1', '2015-08-24 20:05:00', 'MAI', 'NOT_HAVE', '2016-09-30 00:00:00', null, null, null, null, null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('23232', '7', '33592699-8f9a-4e39-8082-110d33afb6c0', '23232', '23232', '23232', '1', null, 'MAI', 'DEFAULT', null, null, null, null, null, null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('d2b97424-cffe-4669-b5cf-7144d1e6a0c3', '3', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', '123', '123', null, '1', null, 'MAI2DI', 'DEFAULT', '2016-08-17 16:21:46.131432', '123', '123', '2016-08-17 16:21:46.131432', '123', null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('e9441866-2323', '4', 'd0392bc8-131c-8989-846e-c81c66011111', '是多少1', '是谁1', '事实上', '0', null, 'MAI2DI', 'DEFAULT', '2016-01-11 19:05:30.171417', '', '', '2016-01-11 19:05:30.171417', '', null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('e9441866-4710-43a2-a901-350a77542e41', '4', '60978e73-851b-429d-9cf4-415300a64739', '是多少', '是谁', '12121hi的', '1', null, 'MAI2DI', 'DEFAULT', '2016-01-11 19:05:30.171417', null, null, '2016-01-11 19:05:30.171417', null, null);
INSERT INTO "public"."otainfo_company_ref" VALUES ('fa8a58b8-8e17-4684-8989-f53703820656', '3', '60978e73-851b-429d-9cf4-415300a64739', '18', '', null, '1', null, 'MAI2DI', 'DEFAULT', '2015-12-29 11:12:04.348621', 'zhilianjishuzhuanshu', 'zhilianzhuanshu11!!', '2015-12-29 11:12:04.348621', '181', null);

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
-- Records of permission
-- ----------------------------
INSERT INTO "public"."permission" VALUES ('09df35b9-2076-4010-adda-4a72c1c57caf', null, null, null, null, null, '房态房量', '/oms/obtRoomType', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('12121', null, null, null, null, null, '下单111', '/SubmitOrder', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('26c5ceb0-a1ca-4600-bf54-eff51066fd7c', null, null, null, null, null, '客栈列表', '/inn_manage/find_inns', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('2e2b17f1-202f-4665-88ea-26806bc45660', null, null, null, null, null, '订单管理', '/order/find_orders', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('31f85247-f3af-49f2-98c0-8872240363a1', null, null, null, null, null, '房价管理', '/distribution/fangPrice', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', null, null, null, null, null, '异常订单管理', '/exceptionOrder/find_exceptionOrders', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('37b68f35-62ec-49cf-98c6-1e3755bb54c1', null, null, null, null, null, '客栈活跃', '/inn_manage/activeInn', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('38a8145c-9cc7-41ae-a3a2-8285879419e8', null, null, null, null, null, '接单设置', '/distribution/orderConfig', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('499cbd11-a80e-4fc5-a38e-3d503fc3eb40', null, null, null, null, null, '账号设置', '/user/find_users', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('55ac4fe9-6bec-400a-b0c0-89f2c8993c97', null, null, null, null, null, '订单来源', '/operate/order', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('594867b0-b7e6-4bc9-8993-b8651469a83a', null, null, null, null, null, '匹配客栈', '/innMatch/', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', null, null, null, null, null, 'PMS渠道名设置', '/personality/info/pms_channel_name_page', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('6e3a7472-e62e-4cfa-bff6-5a30218a4c95', null, null, null, null, null, '客栈标签', '/system/find_labels', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('7a6f7b47-d779-41a9-a09f-5289479cab0e', null, null, null, null, null, '公司管理', '/system/find_companys', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', null, null, null, null, null, '客户分布', '/operate/kf', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('7b7393a1-0ba5-45cc-887e-4b998ba5607b', null, null, null, null, null, '消息提醒', '/message/*', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', null, null, null, null, null, '个性功能', '/personality/function', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('9414cf03-338c-4994-890e-a91463e4d792', null, null, null, null, null, '图片管理', '/system/images', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('a07c453e-77ff-4b2d-883f-7a645086d1e7', null, null, null, null, null, '渠道列表', '/distribution/otaList', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('b4d62ee9-4e05-4367-b526-ec1e40c26eb6', null, null, null, null, null, '运营趋势', '/operate/qs', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('d105e433-477e-48c0-8405-a5ea8d0d8de9', null, null, null, null, null, '其他消费管理', '/personality/info/otherConsumer', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('d2b4eddb-9f4b-4e9b-be04-3254286f14a7', null, null, null, null, null, '自定义渠道', '/personality/info/myself_channel_page', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('d8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', null, null, null, null, null, '通知模板', '/system/find_notices', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('ddce3409-6b90-4f71-a640-58d79c17f259', null, null, null, null, null, '其他消费', '/personality/info/test', null, '0', '1');
INSERT INTO "public"."permission" VALUES ('e37849ca-c47c-4558-a48d-2b2c6b53b0f4', null, null, null, null, null, '消息通知', '/notice/find_notices', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', null, null, null, null, null, '客户资料分析', '/operate/customer_analysis', null, '0', '0');
INSERT INTO "public"."permission" VALUES ('wewewss', null, null, null, null, null, '信用住手动推送', '/mt/*', null, '0', '0');

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
-- Records of role
-- ----------------------------
INSERT INTO "public"."role" VALUES ('07dceee1-200c-4524-b1c7-b09a1cffa0ea', null, '2015-11-19', null, 'ROLE_21', 'ROLE_21', 'ROLE_21', '21');
INSERT INTO "public"."role" VALUES ('0ccfce6e-0df0-4fef-ad74-dfc90ddad343', null, '2016-05-19', null, 'ROLE_35', 'ROLE_35', 'ROLE_35', '35');
INSERT INTO "public"."role" VALUES ('0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', null, '2015-09-11', null, 'ROLE_19', 'ROLE_19', 'ROLE_19', '19');
INSERT INTO "public"."role" VALUES ('196c6f3d-6817-4106-b5d5-397f68c95905', null, '2016-06-28', null, 'ROLE_40', 'ROLE_40', 'ROLE_40', '40');
INSERT INTO "public"."role" VALUES ('20a26beb-a7ff-41ef-8c22-6bf4084d951f', null, '2016-09-08', null, 'ROLE_42', 'ROLE_42', 'ROLE_42', '42');
INSERT INTO "public"."role" VALUES ('27c4e2d9-0890-4c31-8fed-2c02eabee9b0', null, '2016-03-09', null, 'ROLE_27', 'ROLE_27', 'ROLE_27', '27');
INSERT INTO "public"."role" VALUES ('2e2b17f1-35566-4665-88ea-123456789r', null, '2015-06-12', null, 'ROLE_FEI', 'ROLE_FEI', 'ROLE_10', '10');
INSERT INTO "public"."role" VALUES ('3bafe101-e6d7-420b-8e8e-86c440f17e1e', null, '2015-12-04', null, 'ROLE_24', 'ROLE_24', 'ROLE_24', '24');
INSERT INTO "public"."role" VALUES ('43dfc018-1dc2-4881-8506-39bec6bbfe70', null, '2016-06-28', null, 'ROLE_39', 'ROLE_39', 'ROLE_39', '39');
INSERT INTO "public"."role" VALUES ('4f81e45c-51b2-4fd9-a44c-2ca623b19841', null, '2016-04-05', null, 'ROLE_30', 'ROLE_30', 'ROLE_30', '30');
INSERT INTO "public"."role" VALUES ('6d3fcaba-f822-456e-848c-6102784004f5', null, '2015-12-04', null, 'ROLE_25', 'ROLE_25', 'ROLE_25', '25');
INSERT INTO "public"."role" VALUES ('6edfe10c-9842-4883-af7f-92043a225fdf', null, '2015-08-26', null, 'ROLE_17', 'ROLE_17', 'ROLE_17', '17');
INSERT INTO "public"."role" VALUES ('7c56e6d0-ed7e-442c-9702-11c057a67500', null, '2016-04-12', null, 'ROLE_32', 'ROLE_32', 'ROLE_32', '32');
INSERT INTO "public"."role" VALUES ('88888888role', null, '2015-06-12', null, 'ROLE_FEI', 'ROLE_FEI', 'ROLE_10', '10');
INSERT INTO "public"."role" VALUES ('899c6339-85bd-44ba-8f5f-9f3e7290944d', null, '2016-05-19', null, 'ROLE_36', 'ROLE_36', 'ROLE_36', '36');
INSERT INTO "public"."role" VALUES ('8a0219eb-88e8-4cd9-9b25-a6ebd1111111', null, '2015-06-30', null, null, 'admin', 'ROLE_A', '1');
INSERT INTO "public"."role" VALUES ('8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', null, '2015-05-14', null, null, 'admin', 'ROLE_ADMIN', '0');
INSERT INTO "public"."role" VALUES ('8fe49390-856d-4690-b338-2eb0fd5bbc12', null, '2015-11-19', null, 'ROLE_23', 'ROLE_23', 'ROLE_23', '23');
INSERT INTO "public"."role" VALUES ('95E29E42-6ECC-BB6B-16D0-8911F42D6DA4', null, '2015-06-30', null, null, 'admin', 'ROLE_B', '0');
INSERT INTO "public"."role" VALUES ('9bfe80cd-33a4-44b8-a76e-42253ddc714c', null, '2016-04-11', null, 'ROLE_31', 'ROLE_31', 'ROLE_31', '31');
INSERT INTO "public"."role" VALUES ('ac1a9395-be3b-4754-966d-19b8c75278c6', null, '2015-07-24', null, 'ROLE_15', 'ROLE_15', 'ROLE_15', '15');
INSERT INTO "public"."role" VALUES ('b10a754c-a12e-4d66-8ab9-edd07a65747e', null, '2016-08-17', null, 'ROLE_41', 'ROLE_41', 'ROLE_41', '41');
INSERT INTO "public"."role" VALUES ('ba10ea08-8ba1-4770-9be6-9225fe52308f', null, '2016-06-07', null, 'ROLE_38', 'ROLE_38', 'ROLE_38', '38');
INSERT INTO "public"."role" VALUES ('bc226803-a195-43dd-a627-a11fb9945ab3', null, '2015-10-15', null, 'ROLE_20', 'ROLE_20', 'ROLE_20', '20');
INSERT INTO "public"."role" VALUES ('c41b5efb-e81d-4da1-8516-409015e4932d', null, '2016-04-15', null, 'ROLE_34', 'ROLE_34', 'ROLE_34', '34');
INSERT INTO "public"."role" VALUES ('c8accf20-81a4-4831-b493-6262194ca9b0', null, '2016-03-16', null, 'ROLE_28', 'ROLE_28', 'ROLE_28', '28');
INSERT INTO "public"."role" VALUES ('cd5bef57-3376-450f-a13d-4a829dec071b', null, '2015-07-03', null, 'ROLE_SUPER', 'ROLE_SUPER', 'ROLE_SUPER', '13');
INSERT INTO "public"."role" VALUES ('cda2f464-2835-4e22-ad2e-e93967d35db2', null, '2015-11-19', null, 'ROLE_22', 'ROLE_22', 'ROLE_22', '22');
INSERT INTO "public"."role" VALUES ('cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', null, '2015-08-26', null, 'ROLE_18', 'ROLE_18', 'ROLE_18', '18');
INSERT INTO "public"."role" VALUES ('db9bff73-e7b3-4d7d-9e3a-18882b860f82', null, '2015-07-24', null, 'ROLE_14', 'ROLE_14', 'ROLE_14', '14');
INSERT INTO "public"."role" VALUES ('e00faa40-71e8-4740-856b-a4137af0418c', null, '2016-01-06', null, 'ROLE_26', 'ROLE_26', 'ROLE_26', '26');
INSERT INTO "public"."role" VALUES ('f09c6461-0bbe-436c-9fea-fe18819459fa', null, '2016-06-07', null, 'ROLE_37', 'ROLE_37', 'ROLE_37', '37');
INSERT INTO "public"."role" VALUES ('f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', null, '2016-03-16', null, 'ROLE_29', 'ROLE_29', 'ROLE_29', '29');
INSERT INTO "public"."role" VALUES ('f6c00bc5-b041-4c6f-806c-7643dc95a5f1', null, '2015-07-24', null, 'ROLE_16', 'ROLE_16', 'ROLE_16', '16');
INSERT INTO "public"."role" VALUES ('f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', null, '2016-04-12', null, 'ROLE_33', 'ROLE_33', 'ROLE_33', '33');

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
-- Records of role_permission
-- ----------------------------
INSERT INTO "public"."role_permission" VALUES ('005eda70-a479-4562-9d4f-c9c41c88180b', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('0207f1e8-7cdd-461e-9f73-a4c4bf2f059b', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('02aec3c5-9c8a-494d-b6ee-4db74af25176', '2e2b17f1-35566-4665-88ea-123456789r', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('02b53ce3-f754-411b-96c2-9a669b820cc0', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('02de858f-5b53-4019-824c-bb632d74b91f', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('034111a3-07d4-40cc-aad1-5c73dc0c1a48', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('04542ca1-3230-4c95-9e49-1c721f65931e', '6d3fcaba-f822-456e-848c-6102784004f5', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('046f6550-a87a-4bac-b312-1df636cc9e6f', '95E29E42-6ECC-BB6B-16D0-8911F42D6DA4', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-07-01', '2015-07-01');
INSERT INTO "public"."role_permission" VALUES ('04f2b2f1-d932-45ff-9557-efa6112868d4', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('04f93ac6-f3ac-4eb7-b7cd-a43e5a3f408c', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('0622a0cb-5016-4347-8213-a79fe90ecf45', '196c6f3d-6817-4106-b5d5-397f68c95905', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('0690b68f-d117-4b1f-8628-b01d36bc1ddd', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('07dc0f56-8b25-4cb5-8f15-05f347370222', 'e00faa40-71e8-4740-856b-a4137af0418c', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('07e3d14a-3c8f-48ad-b8c2-5c12999cb6cf', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('07fd32bf-a16e-4c48-9860-7fa71b4019d7', '2e2b17f1-35566-4665-88ea-123456789r', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('087bbfb1-99a6-4507-9c5c-8087581ebe96', '2e2b17f1-35566-4665-88ea-123456789r', 'd105e433-477e-48c0-8405-a5ea8d0d8de9', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('08bbc982-bbbd-4685-979f-cde03532c22b', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('0a478a40-3bc4-4caa-88b1-2cb70b07a583', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('0ac824e5-95ec-416a-bd7c-81b9b0db9c8d', '196c6f3d-6817-4106-b5d5-397f68c95905', '9414cf03-338c-4994-890e-a91463e4d792', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('0af00bfd-392b-4752-b2a2-5382f6390360', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('0c6f91fc-7566-4800-8143-7792e19b7632', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '12121', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('0cefbddd-5c53-43a1-a2c6-3b18a77441dc', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('0d196130-6294-48a7-84ae-32b6fdd7a942', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '9414cf03-338c-4994-890e-a91463e4d792', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('0d90f602-6b21-42cd-919c-9e1700856909', '6edfe10c-9842-4883-af7f-92043a225fdf', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('0d9c4b99-eb08-46f5-a48c-7a3712211585', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('0f321715-89cf-4c8a-b24b-8f9a99017f77', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('0f3557fd-bb51-43d2-a505-73df04415ad8', '2e2b17f1-35566-4665-88ea-123456789r', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('1204efc3-826e-482e-b170-fe777313857d', 'e00faa40-71e8-4740-856b-a4137af0418c', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('135dc348-ad83-4d54-b57f-5749933038cc', '7c56e6d0-ed7e-442c-9702-11c057a67500', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('13779ed0-e8a3-4c0a-bc91-9dad89c1a0bf', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('13846156-81a8-4ba1-9d27-105210389f50', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('13bc561b-63b5-4eeb-b00e-e47da2030f97', '196c6f3d-6817-4106-b5d5-397f68c95905', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('1485c55d-b57b-4c16-9f1a-870579d0125e', '8fe49390-856d-4690-b338-2eb0fd5bbc12', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('14ba8c92-409c-48d9-a69a-f21cb5eb63f2', '899c6339-85bd-44ba-8f5f-9f3e7290944d', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('154169d0-874c-42b4-bc52-665350e28d94', 'ac1a9395-be3b-4754-966d-19b8c75278c6', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('166cd1e7-8e97-4580-bdb6-5b2f2df05777', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('17c690a8-5e75-434a-b285-d2a051c74e39', '2e2b17f1-35566-4665-88ea-123456789r', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('197d6ba2-9d86-4f16-bafa-4b7f6b606247', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('19883738-0c44-4a70-80af-44db281276b9', 'e00faa40-71e8-4740-856b-a4137af0418c', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('19b66485-c059-465c-a21f-05216f517955', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('19ec4287-334b-4b17-868d-d63ddf4392c1', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('1ad31d04-54d3-4270-b61d-19b1a8fda51a', '6edfe10c-9842-4883-af7f-92043a225fdf', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('1ae32d08-385a-4957-86e5-5bf8b7151602', '2e2b17f1-35566-4665-88ea-123456789r', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('1b306239-d456-4d58-8ec8-5c4d5732f4e5', '2e2b17f1-35566-4665-88ea-123456789r', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('1b5ead60-eb11-4e97-a33f-3a9dc0fa5691', 'e00faa40-71e8-4740-856b-a4137af0418c', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('1c3801b8-0b54-4a0d-979f-6a572a4406bf', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '9414cf03-338c-4994-890e-a91463e4d792', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('1c764a34-043c-40e9-969b-cd5aa7b395d3', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('1e0d4caa-eb4b-4ac2-a062-f42a6c6b0b6b', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('1e16b562-865b-4628-85b8-fab169d8dd64', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('1e3907cd-b6dd-40c4-a279-6e29d26c6a7c', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('1f29d023-aded-413a-9caf-a7c08d246997', '6edfe10c-9842-4883-af7f-92043a225fdf', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('1f2bcbc6-2775-4afc-b5cd-0637d11722d4', '7c56e6d0-ed7e-442c-9702-11c057a67500', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('1f43aad8-dd7e-4026-bec6-0d32ee94ee20', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('1f7e31ac-6df2-45a3-a176-e0046f569457', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('1f8d37a2-235a-4a43-a79b-f00d293e027f', '43dfc018-1dc2-4881-8506-39bec6bbfe70', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('1fba4058-c273-4991-80dc-f6372d78e5fd', 'c8accf20-81a4-4831-b493-6262194ca9b0', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('222510b4-ecc7-437b-8f3d-f3eb4d7340eb', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('24075bbb-a69f-485f-95e0-3a9ecc8e891c', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('244505ee-b694-4609-93e7-969502d71132', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('2447e138-f607-42ae-aa93-050d1fb4a4f9', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('24d0584a-7b45-4097-95ff-4226bd12708a', '7c56e6d0-ed7e-442c-9702-11c057a67500', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('25eb586a-ab2e-4aa0-bf71-82657a667d86', '7c56e6d0-ed7e-442c-9702-11c057a67500', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('2663bebf-9915-4062-97cd-491918199a6b', '196c6f3d-6817-4106-b5d5-397f68c95905', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('2695e6fe-9d28-4b0e-9bf0-34b6fbd1bd5b', 'e00faa40-71e8-4740-856b-a4137af0418c', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('28750969-4473-4c37-8671-29b47ff32167', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('288ee45c-968d-49f8-ac7c-2b02d68f2a1a', 'cda2f464-2835-4e22-ad2e-e93967d35db2', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('295bddf5-a72c-462a-ba1e-dbbad40d6236', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('29d7c664-19e5-4bc6-b9e5-e601f769091c', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('2a696684-8245-409b-a63d-511eda2d3de8', 'cda2f464-2835-4e22-ad2e-e93967d35db2', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('2ac16192-ae36-4601-b071-677c6d842751', 'e00faa40-71e8-4740-856b-a4137af0418c', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('2b219345-3738-44af-bba2-391774c9f177', '196c6f3d-6817-4106-b5d5-397f68c95905', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('2be8be29-1f02-4e1b-8189-9f034884f6f9', '6d3fcaba-f822-456e-848c-6102784004f5', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('2c9d2061-d419-4b10-8b5a-0f12c8c7aa2b', '6edfe10c-9842-4883-af7f-92043a225fdf', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('2ca64336-c8f8-45f7-b766-b494a3d63a61', 'ac1a9395-be3b-4754-966d-19b8c75278c6', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('2cd12498-8cff-45c1-bdfb-17fa19e27da7', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('2e582ba1-d255-4a8f-b0bf-08e6a07b3a89', '8fe49390-856d-4690-b338-2eb0fd5bbc12', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('2ee230ac-cae7-468c-9914-695d2f75f5f1', 'c8accf20-81a4-4831-b493-6262194ca9b0', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('2eedd757-ab5a-4ad2-821c-6fe0f676fcdc', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('2eef0a1a-df81-4c3e-a5be-6622e227ee1c', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('2f564bcc-7d78-48f1-91a9-0cfa0532ae9b', 'ac1a9395-be3b-4754-966d-19b8c75278c6', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('2f5fc720-68e2-4985-a3ac-7e0de9417a2d', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('2f85997a-6449-4139-9387-9d6bb046d934', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('313ace71-d377-4e98-8a6c-a64f7d76ef2e', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('31ba06c2-c70b-49fd-a152-a62035a70473', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('31c0b0c6-3725-4f4e-adc1-4c292bfd6591', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('3281a96f-30a3-4e82-9ccd-1e34d6f8a6ca', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('328be03d-82db-4b53-9b1c-8f23625f9e53', '6d3fcaba-f822-456e-848c-6102784004f5', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('32b8c063-f304-48fa-a132-e2b016c95d87', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('3318b5a2-8409-4526-adb0-6845635c63c0', '7c56e6d0-ed7e-442c-9702-11c057a67500', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('33893b9a-4095-4448-9f37-f5bb808205c4', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('338e097c-e96b-4ce8-901d-e5d170450604', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('34a976a8-16af-4e58-8b44-5c5b2495e764', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('351cabc2-51af-48d1-851e-415ddbb2ccbd', '6d3fcaba-f822-456e-848c-6102784004f5', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('35334fa9-4e76-411a-a6b3-3b10f9482af5', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('35bd9936-4e56-4c95-846c-37af6b570548', '2e2b17f1-35566-4665-88ea-123456789r', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('3641ed0c-74c1-45b9-8cd5-4abc6a6acbef', '6d3fcaba-f822-456e-848c-6102784004f5', '9414cf03-338c-4994-890e-a91463e4d792', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('36726978-53c7-406e-916d-225490c4b7c9', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('36ec5987-c79b-4905-a34a-505bba44289c', 'cda2f464-2835-4e22-ad2e-e93967d35db2', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('370a16bd-db94-4042-b3c0-20283b2c1789', 'c8accf20-81a4-4831-b493-6262194ca9b0', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('371adffa-4e43-4d6b-b2cc-e280a0ec0974', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('375330aa-2411-4591-8f33-7adc2dfbbca4', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('380d6d2f-b29f-425e-a139-643f62e8852f', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('383f3cda-a255-4ae0-8667-33632991778b', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('38ede5c4-cb52-418f-a61c-1255066b32cd', 'c8accf20-81a4-4831-b493-6262194ca9b0', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('3999bd3b-2602-4066-afda-c1164ac02de6', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('39c88beb-1cb1-4bc2-97ac-01306e35b040', '196c6f3d-6817-4106-b5d5-397f68c95905', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('3a158f28-8c53-4815-bef4-3f3786550e1b', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('3a79b90d-b050-477d-80b8-5b69053e0379', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('3aadf3cc-975d-4b34-bddf-7a7e8c1a2037', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('3b1a070b-284a-4213-95a1-5902293daccf', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('3b93cb28-2dd8-493e-b649-f9f7559e050b', '6edfe10c-9842-4883-af7f-92043a225fdf', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('3cfde952-eff2-44f2-a1be-f4b6a63d2e9f', '8fe49390-856d-4690-b338-2eb0fd5bbc12', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('3db0ec83-0c26-40d5-b94c-f36c2592cbe4', 'c8accf20-81a4-4831-b493-6262194ca9b0', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('3e10459d-82f0-49f7-8d23-c77202ef39fb', '6edfe10c-9842-4883-af7f-92043a225fdf', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('3e449ad5-79fd-49d9-96f6-c8a9d6f2640b', '7c56e6d0-ed7e-442c-9702-11c057a67500', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('3f2cecd3-3111-49f5-98b1-970d54bbd9f1', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('3f91182c-254c-4edb-ac58-22b4fb8c8b2a', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('402cf8ba-0749-4070-b765-027e983a30f1', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('403fc602-60c3-4b28-91fe-a3a69181aab8', 'e00faa40-71e8-4740-856b-a4137af0418c', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('40b33a00-a331-4786-9a3d-ee8c627152d0', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('42108168-2d3d-423d-a373-a0e2cff6488d', '43dfc018-1dc2-4881-8506-39bec6bbfe70', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('4380c272-b5c1-4231-a6f6-a0328978456e', '8fe49390-856d-4690-b338-2eb0fd5bbc12', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('4389d4e1-7bfd-4b38-a67f-e2d2267ca2a4', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('449786aa-2cee-4c11-805b-eaf59c024bfa', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('44991fc3-845f-49f2-a065-011fe580326f', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('44bf091a-e52d-4327-9bec-dd9f97ef3148', 'c41b5efb-e81d-4da1-8516-409015e4932d', '9414cf03-338c-4994-890e-a91463e4d792', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('44e6cc20-37b1-47b7-8f97-225bcbec047b', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('4580257d-f1b8-4f6b-9905-33f10cee3186', '7c56e6d0-ed7e-442c-9702-11c057a67500', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('467e6a38-6b1a-489c-8252-c0ea14c5ba7f', '7c56e6d0-ed7e-442c-9702-11c057a67500', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('468b6106-9baf-47fa-84ad-50fdc304fbe8', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('468d2947-2e1b-448e-8482-7ddbc11f6fbb', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('46cf31d1-2522-4ce5-890c-5f12d426a912', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('4738a87b-0828-426e-b67f-128360e3fe2f', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('476c0da7-99a6-4f17-a3b6-98befc916106', '196c6f3d-6817-4106-b5d5-397f68c95905', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('47a10dc3-6e1e-49f4-859d-8748733d97d9', '7c56e6d0-ed7e-442c-9702-11c057a67500', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('480d0b94-9413-4cd0-a4fc-4cf30b8d9335', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('4851804f-344c-403d-b67e-cc2db4cea06b', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('48544e00-67b9-4dfa-8526-e5780f8353bd', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('49e3ecce-1133-444f-8de0-8f53d1ea5260', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('4a15f203-c774-429b-908a-506a1106be75', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('4a3ac1e3-836a-4e0d-a518-3e4d9bc5df4f', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('4b5318bf-3f1d-4e1b-9722-9287057ffbdd', 'c41b5efb-e81d-4da1-8516-409015e4932d', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('4bd4efb4-9824-4c21-8426-6e8b6383cac5', 'c41b5efb-e81d-4da1-8516-409015e4932d', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('4c906016-b5fd-4b5c-b4ee-f59ec80f65a3', 'ac1a9395-be3b-4754-966d-19b8c75278c6', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('4cba508e-aa34-42aa-8823-a5ca7abcfa61', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('4d020772-a267-4418-84fd-66b6133164af', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('4e1ca7e2-3570-4dfe-85f4-70ee4431d6d6', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('4e476023-d146-4d12-a738-a5b9f5a4d2a1', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('4e5079c0-fbbf-43d8-a799-ca2ab4d53a65', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('4ebb3870-87a6-4c31-bd44-cfda16c24641', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('4f8a2c6e-acbb-4fc8-a287-abc6beef80a3', '6d3fcaba-f822-456e-848c-6102784004f5', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('4fa3942f-0134-49cf-955c-d05129fe1348', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('5004faec-2242-4fc9-ae8e-cb7d1280b8c2', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('501b6077-e85f-4df1-9e2e-23596972520c', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('50474b15-666a-48b0-b75e-4709a5024933', '7c56e6d0-ed7e-442c-9702-11c057a67500', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('510f8236-70b5-4f24-8f12-06d7218e4f30', 'e00faa40-71e8-4740-856b-a4137af0418c', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('51fefa02-912a-49b4-b5da-3b871ee0e841', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('525d9f68-4fcf-4135-8a3a-a7a3953bc455', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('52acb300-5694-495e-97e3-cb765af13ae8', 'e00faa40-71e8-4740-856b-a4137af0418c', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('537ca254-a06a-401c-8d6f-56f41b79642e', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('5418f2e7-ae08-4c29-853e-a1720d7fd5ff', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '9414cf03-338c-4994-890e-a91463e4d792', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('548b6303-c091-4c1e-bc0d-040eacf270dc', 'c41b5efb-e81d-4da1-8516-409015e4932d', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('56385278-cc3f-4beb-b23e-afc0b3a5d4a7', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('56f534c9-83e3-4411-9e06-37d41d016c13', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '9414cf03-338c-4994-890e-a91463e4d792', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('57465f1b-56e9-4505-aa93-6205777a9f6c', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('5900a00e-0e25-4237-b094-1af75c6c935d', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('59799009-d4cc-4f4a-94b9-df08ccf73ac1', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('59ac15e7-9ccc-4ffb-855a-c60f76d6c937', 'ac1a9395-be3b-4754-966d-19b8c75278c6', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('5b71a1bd-db35-4cd1-b097-ce76d3714cab', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('5bb69a40-f2c3-40ac-a145-606d97e04c89', '6d3fcaba-f822-456e-848c-6102784004f5', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('5c108f1b-9033-4f78-8fdb-02f1fbe95c1b', 'e00faa40-71e8-4740-856b-a4137af0418c', '9414cf03-338c-4994-890e-a91463e4d792', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('5c6fdd77-ae53-4af9-93e5-6cf477e499f4', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('5c90b80b-df97-4d72-bd3c-dede8f2c56b3', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('5d18a6c1-626f-4725-b7ba-4a856b627517', 'e00faa40-71e8-4740-856b-a4137af0418c', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('5dccbe6d-9217-4cbc-9881-1a185ac650d4', '7c56e6d0-ed7e-442c-9702-11c057a67500', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('5df76532-83f4-40db-9551-b514cdf6ed43', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('5eb6cb30-b3df-4d5c-b662-34db3a99002e', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('5f63f497-d80b-4940-b437-0fc3bb57be8b', '899c6339-85bd-44ba-8f5f-9f3e7290944d', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('5fa75d07-8663-4b06-a182-95998071cf3e', '6d3fcaba-f822-456e-848c-6102784004f5', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('600d6f27-e168-4ae7-81d3-cfae67cf1f2a', 'e00faa40-71e8-4740-856b-a4137af0418c', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('60dc8201-5001-43b3-97b7-93f3cae28c65', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'd2b4eddb-9f4b-4e9b-be04-3254286f14a7', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('615f5a0a-041d-412a-9904-e86fc277e664', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('619cfa30-5217-4f82-9dd8-139791932bfc', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('62593ead-f75c-4144-9d37-500ed650982d', '196c6f3d-6817-4106-b5d5-397f68c95905', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('63bc8c9c-e317-4d69-bf4c-6b37fc9a0c09', 'c41b5efb-e81d-4da1-8516-409015e4932d', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('63cc5116-6dd5-4685-911e-3b974f56288b', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('64272add-25ef-49de-990f-14f24f9c1596', '6edfe10c-9842-4883-af7f-92043a225fdf', '9414cf03-338c-4994-890e-a91463e4d792', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('6445f86b-4482-4657-b945-b3b83278dd0f', '6d3fcaba-f822-456e-848c-6102784004f5', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('64b60ca5-5ffc-4d0b-8ba9-f066925f5a3d', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('658597d1-203f-4d9a-9c5f-3fa64fffd281', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('66703ad3-cc94-4175-a999-6ead179ccc43', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('6697038a-a8f7-41a4-bae3-c12b459d3df5', '6edfe10c-9842-4883-af7f-92043a225fdf', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('66988864-9a06-4ec4-8ce9-5282221a30e5', 'c41b5efb-e81d-4da1-8516-409015e4932d', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('6a23d66c-c071-419e-a8f2-89129580ea5f', '196c6f3d-6817-4106-b5d5-397f68c95905', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('6a594566-29f2-4b1d-876f-986d1b12f3bd', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('6aa20f54-f45e-4b7f-aa54-509a8928aca4', '2e2b17f1-35566-4665-88ea-123456789r', '9414cf03-338c-4994-890e-a91463e4d792', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('6af68a3a-41ff-4899-b220-f0677821d555', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('6c942c2c-ce2f-43df-9b3e-18463d399e4c', 'c8accf20-81a4-4831-b493-6262194ca9b0', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('6d02632b-f9f9-4da5-ba73-5af51a906504', '7c56e6d0-ed7e-442c-9702-11c057a67500', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('6de54786-81eb-44e0-88a7-4eb1a1134052', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('6e2c0499-3a10-436e-a681-1ae1744db1d3', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('6e7bf075-ecf9-4f4b-bfbe-35cc75cd775b', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('6ea8f2d7-6d26-4ad2-ace3-f6f1bfcd86b8', '7c56e6d0-ed7e-442c-9702-11c057a67500', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('70035a04-b170-4805-914a-71b71271e178', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('713e16a5-1cdf-41c9-aef2-78a3198b26c7', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('716ccdc8-bd03-42e3-b328-ef06b0e8cd50', '196c6f3d-6817-4106-b5d5-397f68c95905', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('71f44b6e-93ec-4e54-8706-606d95e8762b', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('728ef125-b515-46f2-85ed-143a237edad6', '196c6f3d-6817-4106-b5d5-397f68c95905', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('7314682d-07ee-4f3c-94da-69221ff2049c', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('74c98e5c-05a7-4a18-9be5-a52afccaa1f9', 'c8accf20-81a4-4831-b493-6262194ca9b0', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('74d29b38-8fc9-418a-862a-6cd97ae0e5c2', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('7506e100-bc33-427e-a8d7-83dd76dc6375', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('76ee7e9b-3308-42d3-a11d-46a5df89ff1f', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '9414cf03-338c-4994-890e-a91463e4d792', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('770e1a72-8172-4edc-bdb2-b52cad582d47', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('77dfd1a5-8023-4da4-9264-5e6ce88de383', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('77f08d08-abeb-4de4-ad09-d8adff2dfade', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('7801839a-9184-4e69-9593-94175e40e2cf', '95E29E42-6ECC-BB6B-16D0-8911F42D6DA4', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-01', '2015-07-01');
INSERT INTO "public"."role_permission" VALUES ('780fc4f1-9ea7-4c7f-a28f-20dd2aadca91', '2e2b17f1-35566-4665-88ea-123456789r', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('78596183-6673-4635-b5da-8b5e1c9465e2', 'c41b5efb-e81d-4da1-8516-409015e4932d', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('7960f549-d5e6-4bcb-a89f-3a4ba40524d8', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('79dd4564-e468-43af-96e3-5ff2fd2eb564', '196c6f3d-6817-4106-b5d5-397f68c95905', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('79e409f5-8b6f-4457-b845-815ec5624bc5', 'c8accf20-81a4-4831-b493-6262194ca9b0', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('7a2e6e86-fea5-4f2a-9181-99b9e5e80fd2', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '9414cf03-338c-4994-890e-a91463e4d792', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('7a46b353-c94a-415c-a806-88d027383182', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('7a53e77c-197d-4d57-b3e3-7ace5d283d56', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('7ac88b8e-054e-4efe-b661-87749fa760b1', 'c41b5efb-e81d-4da1-8516-409015e4932d', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('7c501ecb-0d68-43ab-b778-1f802d7ff285', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('7cf36f85-5326-4078-9921-8913a67d0a9b', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('7d757db9-afbf-4f6c-9e64-5cc649eb6b28', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '9414cf03-338c-4994-890e-a91463e4d792', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('7dd17b42-3d25-4b7b-88ce-2ff0aa2856d0', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('7ed78d48-b5d4-4573-8d92-0da425d608bc', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('7f0c1c20-4714-4fd6-99c5-0f619617d378', '6edfe10c-9842-4883-af7f-92043a225fdf', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('7f55eeb8-4479-4087-8de7-a5e086bf8264', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('8070bcdb-d74a-42a4-83e5-7287609ba176', 'c8accf20-81a4-4831-b493-6262194ca9b0', 'b88a915f-56bc-4ede-a2b9-59cf364a5576', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('80b99b97-a27c-4368-922a-e0374b5553ef', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('817de158-a0dd-40d0-9ce4-7e52f6cd165b', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('81c09cd1-05ae-42ab-a01b-98a68f146ac5', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('81d55d85-687d-4372-9a31-ce73dc54f26b', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('827fee93-df50-4123-a44e-8a9d6fed6d3c', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('83e1fabf-0a6e-4dd9-8f14-e82b7ac555fc', '196c6f3d-6817-4106-b5d5-397f68c95905', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('856e17bd-db2e-401d-8a17-a922234b0940', 'ac1a9395-be3b-4754-966d-19b8c75278c6', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('856f8e01-856d-4557-8f11-0ea762781c70', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('8727a903-9c1c-4102-8751-a6df46cca08b', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('876d5807-9ef2-4c22-a033-f7f521fce2a0', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('883eefb5-aae8-4f8a-b364-9cb9cc97a4a9', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('8897e656-dd67-4bd8-b9fd-621b0b8bd412', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('89dc7ec9-3b5e-4bf8-9a3b-a2128e300b80', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('8a10944a-a1e1-4b85-958c-2f96c1dc8e95', '2e2b17f1-35566-4665-88ea-123456789r', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('8a47ec3c-c32c-4468-b5c9-ecb711e2ee9b', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('8ac4001d-8c27-471b-986f-d71bc743d80b', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('8b1562d3-8b4d-4eb9-9dab-9b4856a007a6', 'ac1a9395-be3b-4754-966d-19b8c75278c6', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('8c760505-6e42-4a1d-99f8-5fc3dfa44ebb', '196c6f3d-6817-4106-b5d5-397f68c95905', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('8cca7bd7-c63d-4c38-903d-f61cbf6d6e56', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('8de373be-ec2f-44a1-9ec3-6c2d8b603afd', '2e2b17f1-35566-4665-88ea-123456789r', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('8ecd59e6-ac68-4c58-816e-211a6b23bbec', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('8f002c82-9913-4512-9485-1695b74f2739', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('8f46f028-a186-4e97-b5f3-f1cae7481701', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('8f698859-d12f-4472-99bc-44beca8b73cd', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('9143d007-2289-4b21-87ba-1e513f2b0a52', '2e2b17f1-35566-4665-88ea-123456789r', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('9200d9ec-bca9-4137-ba75-ec8409482ce5', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('9211caff-c777-49c7-984c-edb99db2b764', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('92e9c80a-240b-44a7-8aaf-bebe6b842b31', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('931d8746-3d22-4c06-b563-3fa56234936e', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '9414cf03-338c-4994-890e-a91463e4d792', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('93566ff7-3dbc-4d14-80af-fdfb9872a8eb', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('93dd6588-a2fe-41a4-9dcd-0731d32dfe12', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('951a15d8-cd8b-4b5e-ae4b-602f9b5b8876', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('959c6b6c-37e8-42d0-8dbc-3fc83f6ffb6e', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('966eaac4-dc18-4068-8126-6bd4932402d6', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('969058f2-487c-4683-933c-54bfa60f34af', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'wewewss', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('96ba0111-609a-47bb-a211-7ad08965cfed', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '9414cf03-338c-4994-890e-a91463e4d792', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('96d114bf-ef48-4b12-a6fc-a12f10182e5f', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('96fe38af-a3a0-4c57-97d0-38c365510a01', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('970f89da-9c67-4f59-b5f6-2706c18c8ec9', 'cd5bef57-3376-450f-a13d-4a829dec071b', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('971873df-7c7d-4221-bad3-f2c557a142cc', 'c41b5efb-e81d-4da1-8516-409015e4932d', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('9a446a6d-5f53-4460-9915-7fbdea23d7ed', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('9a8e6b3e-f15e-4bc4-b4e6-623ea7abf355', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('9ab6b2ba-1c47-483f-a684-d26b7d561323', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('9b2e254a-cffb-435a-9784-da2c81dd4373', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('9bcbc956-5e44-4f47-80a2-0c3265f08038', '6d3fcaba-f822-456e-848c-6102784004f5', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('9c43244f-93a9-4425-9c7d-18642f2c5a52', 'c8accf20-81a4-4831-b493-6262194ca9b0', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('9e5e567b-6357-4ead-bc6c-693a2d1a8340', '6edfe10c-9842-4883-af7f-92043a225fdf', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('9e799228-a782-4694-a5f5-7f9872cbb134', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('9f741c03-238f-4fca-8536-b7ce7a296776', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('9ff30194-ca05-4044-8d5d-10bfc914596e', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('a07ee872-76ab-4f56-9df6-17ab53e4beb2', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('a10952bf-9487-441f-89d1-d33579df2fb9', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('a1200dfe-012e-463e-9c15-26b4b7e44d62', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('a27e862d-f817-4e64-94eb-00067065ea29', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '9414cf03-338c-4994-890e-a91463e4d792', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('a31e0759-866f-4856-b3d4-3d93c088d50c', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('a39a8cd5-a998-4705-985e-227c627636d4', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('a40ebebd-d2a1-45c5-b93a-805e7c7b8781', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('a4620b60-ae93-4034-b1f5-fd3e8cae46a7', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('a601efce-94aa-4def-ad4c-516865d2249a', '2e2b17f1-35566-4665-88ea-123456789r', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('a601ff45-b97d-4854-9f30-ce27fc7349dd', 'f09c6461-0bbe-436c-9fea-fe18819459fa', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('a66042ed-1818-4de3-8be7-775e38b68530', 'c41b5efb-e81d-4da1-8516-409015e4932d', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('a6820c79-eddf-4263-9711-7ef5c14f38c2', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('a6b8a32b-f42e-4f40-b281-5b8864365e3f', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('a6faf2a4-a11c-481f-ba96-43c5e5664154', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('a7230a98-fdb9-44c8-a8e4-73b99bc17a86', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('a72d83b1-a4e5-4541-8cc4-1e3d42e4ab6d', '7c56e6d0-ed7e-442c-9702-11c057a67500', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('a734a662-f768-4e00-afb7-99968b35a00e', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('a7f8171c-096a-416f-821d-a7b5b78e5965', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('a8673b13-664c-477b-b9db-d671dd1880e2', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('a8d3a676-4b56-4501-a12a-fd0cb4fd9c62', 'c41b5efb-e81d-4da1-8516-409015e4932d', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('a94a4c0a-4424-4763-8da6-0e49fa51bd68', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('aa310abd-dac9-4836-9cbf-0b29ee2b450c', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '9414cf03-338c-4994-890e-a91463e4d792', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('aaa7192f-3636-4a12-83cf-8cf2126d5a78', '2e2b17f1-35566-4665-88ea-123456789r', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('ab281641-c991-44dc-a772-dfec72e167d6', '2e2b17f1-35566-4665-88ea-123456789r', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('ab39b799-c2aa-4698-a319-0d8e47109b20', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('abe9bacf-7c40-4d9f-8c7e-9a98d9d10c4e', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('ac184dfb-0904-4b86-b187-c14c695e1fc5', 'c8accf20-81a4-4831-b493-6262194ca9b0', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('ad0d6ba6-6a36-4d3d-8e9d-dce518ff4e2b', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('ad254cab-242d-45b1-b5ce-0c6dae795876', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('ad3b7bd9-810e-4433-af9a-5fd5501ce7c3', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('ada77b89-1784-40ac-b5f6-1c8e6ae46565', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('ae05c5a8-c589-4173-956d-7c65f71ed512', '43dfc018-1dc2-4881-8506-39bec6bbfe70', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('ae0a05b4-c4f2-47a3-8f78-e068d41d6c6e', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('ae9fcbab-624e-4c6f-9786-297b698ee4d5', '196c6f3d-6817-4106-b5d5-397f68c95905', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('aebcfb64-e6a8-411f-b03d-b4eba989456e', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('aeeb059a-5682-46e1-a2f4-14348a354bd3', 'c41b5efb-e81d-4da1-8516-409015e4932d', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('aeedcd8e-80e5-4f44-8016-9ce286374e5a', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('af4d1b8a-2aad-44ad-90e0-1459c04d7bb0', '899c6339-85bd-44ba-8f5f-9f3e7290944d', 'ebdaabe4-afb8-48bc-a8ed-6593ab11f74a', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('af5f39c1-a668-4dec-930f-a74e9b3a6b37', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('b054f38a-0669-4646-b52d-d7449d04407d', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('b0c4b584-3b6d-4fe8-8767-cef50537f264', 'c41b5efb-e81d-4da1-8516-409015e4932d', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('b1f390be-57de-4794-8965-4176bee0df0c', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('b1fa13b0-5e77-4da8-acf6-872ead4696aa', '2e2b17f1-35566-4665-88ea-123456789r', 'wewewss', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('b20b0b2c-2d71-4c3f-92e0-ec0135126798', 'c41b5efb-e81d-4da1-8516-409015e4932d', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('b27ef71d-1ac4-4a80-a131-e292cf3ac21a', '20a26beb-a7ff-41ef-8c22-6bf4084d951f', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-09-08', '2016-09-08');
INSERT INTO "public"."role_permission" VALUES ('b2c0372e-614a-4139-8a22-6e20c7c88981', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('b2d84a6b-bf67-439b-8b70-681f44dbcb09', '6edfe10c-9842-4883-af7f-92043a225fdf', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('b32db25b-92bd-4c88-9053-ad7a6c5685fa', 'c8accf20-81a4-4831-b493-6262194ca9b0', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('b34769fd-c2c6-4a63-94e0-30b53390276b', '899c6339-85bd-44ba-8f5f-9f3e7290944d', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('b35aee70-2a70-40e5-bccc-8daf3a4a1516', '6d3fcaba-f822-456e-848c-6102784004f5', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('b43184e4-8208-4018-a2b7-30483cc49331', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('b4812b9c-1eb9-4158-ad42-f989bc475536', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('b4f42eeb-6467-4710-9b2b-217476ac073a', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('b5165bac-cd6b-495f-b652-c43b025d3747', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('b55e5c96-50de-4346-a851-387988a1c3eb', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('b5c9142f-5368-4331-b65f-67450c4e2910', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('b5fe78a3-1230-44fb-a4ee-af4b6276372d', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('b639ee8a-9319-4c40-9114-352f1b42cf59', 'c8accf20-81a4-4831-b493-6262194ca9b0', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('b6b4f202-54ac-48a3-b415-7de7797eed90', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('b6bd7716-dfa0-4d66-b2df-2707d3d90f5e', '2e2b17f1-35566-4665-88ea-123456789r', '65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('b7b6c0eb-3a27-48a1-862e-e0f30496db29', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('b7b908b4-d27d-4290-9962-4d0416275ab8', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('b823ba55-bbc3-4c13-b699-ef87c441db83', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('b84174f0-49e7-4709-a2a8-f40ad94ada23', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('b8c7fa60-73fe-4256-9d08-a871df7942cc', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('b93cde38-e9e2-43e0-b727-1dca78e40a60', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('ba18d709-4027-4445-b9bb-dd61011ffa5f', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('ba9a1946-145a-4427-9fd2-c59b8c87fee2', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('ba9f8395-c4bd-4e5f-8b09-f275a1478d81', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('bb490421-174d-4e6c-86e2-57737123b6f4', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('bc35328a-0e66-427f-b6d5-2b4a525350ea', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('bd5942c4-7ce4-4c21-bce8-4b3aee359c5f', '7c56e6d0-ed7e-442c-9702-11c057a67500', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('be18a92d-48d6-4428-bd1a-3a769d729df4', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('be399e9e-0dc4-4a83-bdf8-e97c3aeb1d7d', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('be8d7242-80ad-4a97-b440-18c0f459ef18', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('be8e4da8-5e74-4105-b81d-47516fde68a1', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('bfe00d79-d782-430e-b48c-c692321cee1c', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('c0d6a5bb-a95a-42a8-af35-39bf71a7d7b3', '2e2b17f1-35566-4665-88ea-123456789r', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('c1c4190d-cba7-48fa-93e0-5157e5afbe22', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('c1d23b85-6764-4e18-b19c-a37ac679d9b9', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('c315da77-3bd4-4a06-89b6-bea0654d9818', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '9414cf03-338c-4994-890e-a91463e4d792', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('c357818e-dc45-4a8f-8228-add2e649c00e', 'e00faa40-71e8-4740-856b-a4137af0418c', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('c3ce2a4f-0a14-4752-88a0-589cb922e6e7', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('c44bbc1b-4433-4a3d-a14d-7e3b45bb0767', 'c8accf20-81a4-4831-b493-6262194ca9b0', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('c464e6a4-0eee-4725-9a5e-df362f2f6423', 'ac1a9395-be3b-4754-966d-19b8c75278c6', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('c47c4535-8fe3-4316-8e21-3ab1be6abee1', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('c4bbd10b-9cd0-4116-bf40-e0b22e3bf0a3', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('c584f714-7b36-4399-86e9-853d1258c5f0', '6d3fcaba-f822-456e-848c-6102784004f5', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('c6bc1419-9d78-4a94-9aba-3766a6370f81', '899c6339-85bd-44ba-8f5f-9f3e7290944d', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('c6ef0e9f-9378-455b-aabe-9b5b79fe6b3a', '196c6f3d-6817-4106-b5d5-397f68c95905', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('c7884729-ae13-479f-969d-4089084a2a0a', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('c80ede3f-b26d-4efc-b2bd-005cf6c304e1', '95E29E42-6ECC-BB6B-16D0-8911F42D6DA4', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-07-01', '2015-07-01');
INSERT INTO "public"."role_permission" VALUES ('c89140ba-1869-4c43-9dce-43cc66154139', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('c8ef163a-9763-46d6-b3dc-ac855cf7281c', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('c9491203-6007-418a-9183-d7fee00c19f0', 'c41b5efb-e81d-4da1-8516-409015e4932d', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('c965adb7-d214-41d5-adf7-fa521991c017', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('c9b72095-6469-4c7b-9a91-b61d8c3e3cbe', '2e2b17f1-35566-4665-88ea-123456789r', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('cb3955c0-650e-4751-a81c-f3a618e680e1', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('cb911344-0fc9-43f6-8a3c-f6a1461e0581', '20a26beb-a7ff-41ef-8c22-6bf4084d951f', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-09-08', '2016-09-08');
INSERT INTO "public"."role_permission" VALUES ('cc28b9eb-b76e-425d-b1e6-d3a979ee386e', '6d3fcaba-f822-456e-848c-6102784004f5', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('cce94ec4-ea0a-4dee-895f-2078573cbac1', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('cd1cc377-5614-44e3-9f6d-3cef887d467f', '20a26beb-a7ff-41ef-8c22-6bf4084d951f', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-09-08', '2016-09-08');
INSERT INTO "public"."role_permission" VALUES ('cd86c987-6277-424b-b882-a62a73457ade', 'c8accf20-81a4-4831-b493-6262194ca9b0', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('cde9f691-a1ab-4d14-a54d-8ea6d561de41', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('ce570634-22c6-4b2f-bca9-b7cdcc6864ba', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '65ab0fdf-d93e-4185-b1e9-5c66f39edb5f', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('ced2ae51-e4e4-4a63-aece-e3c3d7800a54', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('cf6d5d35-3d00-4cb4-9fbb-9b657bfe5360', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('cfde5200-2797-47e1-9339-3d777ede9acd', '196c6f3d-6817-4106-b5d5-397f68c95905', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('d05b69db-ef6d-4d86-8308-b09cada969bb', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('d05be896-742d-447f-939f-d27a55b2c958', 'c41b5efb-e81d-4da1-8516-409015e4932d', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('d0d1d982-14cc-4d73-b773-e5d4b38a6ced', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('d0e21632-9c1c-4e6c-9209-eb015d5f22d1', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('d22dfe4a-d087-42bd-b199-88b0476f29e3', 'c41b5efb-e81d-4da1-8516-409015e4932d', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('d29b9b19-9691-4993-8332-d3e5fb107e23', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('d33668bb-e034-4e0c-8abd-6dc695ab00a3', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('d3f2591b-3cee-4309-bc74-1d3d9b3b59f6', '6d3fcaba-f822-456e-848c-6102784004f5', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('d4b17d79-3110-49be-af23-ef6e481ba746', 'c41b5efb-e81d-4da1-8516-409015e4932d', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('d4c87b13-1bd8-4264-ae49-2f9bdf2366b9', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-08-17', '2016-08-17');
INSERT INTO "public"."role_permission" VALUES ('d50a9708-2cc8-45d4-970a-a16c58f3ea94', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('d5170294-8fff-4fda-8972-8a6d21f95ba4', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('d528eaa2-dd61-4296-89b9-105d07065b89', '6d3fcaba-f822-456e-848c-6102784004f5', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('d52d7c5c-4962-4243-a3c0-7c568a0b0991', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('d539c906-c905-41ff-822c-54c2fef0df44', 'c41b5efb-e81d-4da1-8516-409015e4932d', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('d5ede8f1-e123-4521-a410-26830ce211c1', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('d61018cf-5754-4c8a-9b7a-7df093bab2e1', '7c56e6d0-ed7e-442c-9702-11c057a67500', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('d6e71750-f855-4655-9f34-67033af6c8f9', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('d7b036e5-f599-40c2-9cd6-db8754b71cdc', 'cd5bef57-3376-450f-a13d-4a829dec071b', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('d7c9aa1a-d2f3-40af-af86-a31e32166574', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('d7fb6320-3bd9-4f43-971b-d9968f60475e', 'e00faa40-71e8-4740-856b-a4137af0418c', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('d8a7bdd7-f20a-4d75-93b6-742ffd75e711', '2e2b17f1-35566-4665-88ea-123456789r', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('d8b7b7d2-b414-48cd-897d-bbdd5f14e8eb', '6edfe10c-9842-4883-af7f-92043a225fdf', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('d91077c7-2cb2-476e-9fd7-bb78c8cf18ca', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('d940fc9c-28ba-4670-bf12-bccf1848d9b1', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('da31ab2c-47ce-46a6-b5fc-9bb6f24ea073', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('da3d6ba3-a76d-4250-8575-6cd3bfa42dfb', '196c6f3d-6817-4106-b5d5-397f68c95905', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('daa8a827-6140-47c0-9e82-0ce65f151c60', 'c41b5efb-e81d-4da1-8516-409015e4932d', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('db9a9a81-8129-4212-8419-083b9e30a29b', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('dbe10700-9535-4f3e-9fb1-0b6c42ecdf47', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('dc48eac3-e2ee-446a-a289-805552a9f323', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('dcc0d71e-f546-48d0-a599-145285e2f58e', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('dd13fb2f-a6f2-46f0-a5f8-0ffe2eb3e76e', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('dd2b0594-f9fa-4b37-b35b-0e86028bc016', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('ddae1851-0d63-411c-85ce-7167ea54a6f7', '7c56e6d0-ed7e-442c-9702-11c057a67500', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('ddd258d5-e742-4226-bc33-58d29a2db5ee', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('de10a216-b1e7-46c7-96a8-c5837645de2f', '95E29E42-6ECC-BB6B-16D0-8911F42D6DA4', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-07-01', '2015-07-01');
INSERT INTO "public"."role_permission" VALUES ('dec2b4fc-95df-4c2c-9df6-ecebe586de0f', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('df7975c2-4c3e-496e-8229-5a1aec0f2a0a', '7c56e6d0-ed7e-442c-9702-11c057a67500', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('dfdbb1d0-0b31-4f42-b488-2fa43c5ebdcd', '2e2b17f1-35566-4665-88ea-123456789r', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-07-12', '2016-07-12');
INSERT INTO "public"."role_permission" VALUES ('e018ffe4-910f-4b9a-b19d-cfae1ec4d65a', 'c8accf20-81a4-4831-b493-6262194ca9b0', '9414cf03-338c-4994-890e-a91463e4d792', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('e0194daf-55f1-439a-a6b7-af79253be9d8', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('e0a271ef-18ef-4366-9cca-1ee262ebe18c', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('e1039fd4-5fa3-4135-b181-08d0a50d06ba', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('e1b8e2de-4f70-4d51-b90d-a48c0822be01', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('e3224064-9c0f-4f17-8b1e-7a5b42c71792', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('e42ea683-daa0-43ce-b4c9-fc294538deeb', '6edfe10c-9842-4883-af7f-92043a225fdf', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('e464a23c-7b45-4b37-95ce-5e1a44b20c6a', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('e46927ae-f4d5-44e1-95ef-460251cd2b82', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('e472e047-0339-4ba1-b501-7fe86464085e', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('e570f370-a540-4319-92cb-6a50556c526a', 'c41b5efb-e81d-4da1-8516-409015e4932d', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-04-15', '2016-04-15');
INSERT INTO "public"."role_permission" VALUES ('e5e30ea4-356c-4abd-8a4e-60bb68f6554f', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('e65081a5-8c07-429a-a3e6-dc32c2d99bde', 'c8accf20-81a4-4831-b493-6262194ca9b0', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('e76bebee-dab5-4c32-b9c7-5e8c333e49a5', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-11-30', '2015-11-30');
INSERT INTO "public"."role_permission" VALUES ('e779c577-736a-402e-8972-02220e61b400', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('e78d263b-06fe-44b5-afd1-bf93c9718a10', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('e7cca4b9-7023-47ab-a97f-1813e1bf4ec9', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('e976e914-edf4-448d-8c8e-13291545c6ea', '6edfe10c-9842-4883-af7f-92043a225fdf', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('eb36cae5-e83a-48a1-a72b-1e61a0f4d2f5', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('eb436f45-ebe6-4037-82de-52e7b808913f', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('eb503c8f-7be0-474f-a83a-4802fd2a7f40', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('eb598c11-ac78-4ba0-afb4-bc4de65730c7', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('ec437466-5b91-47ef-9b32-bdca97e55804', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '594867b0-b7e6-4bc9-8993-b8651469a83a', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('ec6ad25e-5d62-4517-a1fd-4ce525b4029e', '43dfc018-1dc2-4881-8506-39bec6bbfe70', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('ecb7f90f-23d0-4065-b7ed-15819683141f', '899c6339-85bd-44ba-8f5f-9f3e7290944d', 'ddce3409-6b90-4f71-a640-58d79c17f259', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('ed241814-83ba-4f53-bb7d-1de055f48d7a', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('ee204a19-1b3f-431e-9d38-60ec3fae4845', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('eeb60cbf-4161-4118-aef6-7783e579af40', '196c6f3d-6817-4106-b5d5-397f68c95905', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('eed80d63-108d-4fba-b163-82ef98247971', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('ef1a7d7d-6689-4d10-8dfe-4beb94c6e772', '6d3fcaba-f822-456e-848c-6102784004f5', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('ef1cf670-1d29-4834-b99b-885819176984', 'e00faa40-71e8-4740-856b-a4137af0418c', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('f071162d-5d58-47a5-be5a-71ea33e78fa4', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('f0ab3f79-9033-4aa0-b558-3ba1d5426e4d', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '31f85247-f3af-49f2-98c0-8872240363a1', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('f1fef777-925b-49e4-94e8-402bc6febbce', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '8ffe2a80-27e7-43da-a19e-b298bdeb3a0f', '2016-06-07', '2016-06-07');
INSERT INTO "public"."role_permission" VALUES ('f24c82a5-18b1-446e-b1d6-77f9792040ea', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('f2a82185-734c-4937-b57a-7c8c148f24c5', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2015-08-26', '2015-08-26');
INSERT INTO "public"."role_permission" VALUES ('f39bc21c-e7f6-4f6e-a107-663b4132ac04', 'c8accf20-81a4-4831-b493-6262194ca9b0', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('f4616f5b-f30e-4c5c-a934-cb6b9ea5e9de', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('f4c23962-b1c6-4671-85bd-4e40f4688a94', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '31f85247-f3af-49f2-98c0-8872240363a1', '2016-05-19', '2016-05-19');
INSERT INTO "public"."role_permission" VALUES ('f56f7ea4-850b-4305-b92a-51feee86cf76', 'bc226803-a195-43dd-a627-a11fb9945ab3', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('f6008e12-c5d4-4c8e-b83a-5f84c24feb13', 'c8accf20-81a4-4831-b493-6262194ca9b0', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2016-04-06', '2016-04-06');
INSERT INTO "public"."role_permission" VALUES ('f65a4ced-c9e7-43c1-9299-1dd81d056768', '196c6f3d-6817-4106-b5d5-397f68c95905', '38a8145c-9cc7-41ae-a3a2-8285879419e8', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('f77a89e3-c3d4-4304-a4ef-d752acd4f3f4', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('f7ea07ec-6064-4aa9-87d0-89299183a760', '7c56e6d0-ed7e-442c-9702-11c057a67500', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('f8062a90-c305-4478-a267-c21747ee2121', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '9414cf03-338c-4994-890e-a91463e4d792', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('f83a789e-f754-439b-bdbe-b743a80860ef', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '7b7393a1-0ba5-45cc-887e-4b998ba5607b', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('f88df12c-fe74-4622-8c8a-b86a0f998d2c', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-09-06', '2016-09-06');
INSERT INTO "public"."role_permission" VALUES ('f9a39b16-2d36-4f93-8890-a8a8e56925d2', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '9414cf03-338c-4994-890e-a91463e4d792', '2016-05-12', '2016-05-12');
INSERT INTO "public"."role_permission" VALUES ('f9dd5409-64ad-44ef-a3d2-ce6402b881f3', '6d3fcaba-f822-456e-848c-6102784004f5', 'a07c453e-77ff-4b2d-883f-7a645086d1e7', '2015-12-04', '2015-12-04');
INSERT INTO "public"."role_permission" VALUES ('fa78f160-51bf-4466-b946-f1e556a18bb8', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('fb1dcf89-785d-460b-a328-88908b2ea02c', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '35c87756-48f7-48c2-a7be-ca4d6ee7fd9f', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('fb309ac3-53b5-449f-bf13-37272b7c3f75', '7c56e6d0-ed7e-442c-9702-11c057a67500', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('fc826ba0-e42e-4bb3-8334-d7b3d47dc671', '7c56e6d0-ed7e-442c-9702-11c057a67500', '9414cf03-338c-4994-890e-a91463e4d792', '2016-04-12', '2016-04-12');
INSERT INTO "public"."role_permission" VALUES ('fcb0bed8-0034-4e48-90e6-ef2df12f8b2a', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('fd4c959c-4684-453b-9fed-f50197ec2bb3', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-07-24', '2015-07-24');
INSERT INTO "public"."role_permission" VALUES ('fd52fa66-b857-4198-b653-893a87cf2a7b', 'e00faa40-71e8-4740-856b-a4137af0418c', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-01-06', '2016-01-06');
INSERT INTO "public"."role_permission" VALUES ('fdb31ed2-d5d0-42e6-80f2-168e14022287', '20a26beb-a7ff-41ef-8c22-6bf4084d951f', '2e2b17f1-202f-4665-88ea-26806bc45660', '2016-09-08', '2016-09-08');
INSERT INTO "public"."role_permission" VALUES ('fe2b2c03-6a58-4040-89b0-703bece84102', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('fe3de33e-2076-4e0c-8533-7276a140b676', '43dfc018-1dc2-4881-8506-39bec6bbfe70', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2016-06-28', '2016-06-28');
INSERT INTO "public"."role_permission" VALUES ('fe4b63f2-5a50-490c-b0a9-eab800b4f447', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '09df35b9-2076-4010-adda-4a72c1c57caf', '2016-07-07', '2016-07-07');
INSERT INTO "public"."role_permission" VALUES ('fef1e3cf-ad65-4417-82bc-4db926111b41', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', 'd105e433-477e-48c0-8405-a5ea8d0d8de9', '2016-08-11', '2016-08-11');
INSERT INTO "public"."role_permission" VALUES ('ffbdf32c-a4dc-4cae-b6aa-958d2019e03f', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2016-07-13', '2016-07-13');
INSERT INTO "public"."role_permission" VALUES ('fff65402-330f-4430-96a6-87c14ae63858', 'cda2f464-2835-4e22-ad2e-e93967d35db2', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-11-19', '2015-11-19');
INSERT INTO "public"."role_permission" VALUES ('fffe575d-4cda-47c8-9cd3-a7d2182c143a', 'e00faa40-71e8-4740-856b-a4137af0418c', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2016-01-06', '2016-01-06');

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
-- Records of user_info
-- ----------------------------
INSERT INTO "public"."user_info" VALUES ('06d2db28-d751-4f2c-9bda-bc879a5b49c8', null, '2016-04-12', '2016-04-12', null, '0', 'ADMIN', '62739164', null, '车上1', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', '7c56e6d0-ed7e-442c-9702-11c057a67500', '0', 'CREDIT');
INSERT INTO "public"."user_info" VALUES ('09450320-d66c-490a-91d1-62f9e653bcee', null, '2016-05-19', '2016-05-19', null, '1', 'PUBLIC', '左左（北服务区）', '133333333333', '左左（北服务区）', 'e53a0a2978c28872a4505bdb51db06dc', null, '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '0ccfce6e-0df0-4fef-ad74-dfc90ddad343', '0', 'CREDIT');
INSERT INTO "public"."user_info" VALUES ('0a132bf9-6c51-4017-88c7-84fd389a619c', null, '2016-06-07', '2016-06-07', null, '0', 'PUBLIC', 'yjj', '18780284617', 'yy', 'e10adc3949ba59abbe56e057f20f883e', '0                               ', 'd0392bc8-131c-8989-846e-c81c66011111', 'ba10ea08-8ba1-4770-9be6-9225fe52308f', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('109dad15-d83b-460e-a999-ef08865bdda6', null, '2015-09-11', '2015-09-11', null, '0', 'ADMIN', 'fanqie_yun', null, '番茄运营', '96e79218965eb72c92a549dd5a330112', '1                               ', '60978e73-851b-429d-9cf4-415300a64739', '0dc3d19e-0fd4-4e9d-83f7-166a1dbfc824', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('11111', null, '2015-07-01', '2015-07-01', null, '21', 'ADMIN', 'xzadmin', '13800138000', '管理员', '96e79218965eb72c92a549dd5a330112', '1                               ', 'CEA3CBBC-D739-DBC7-FAC9-49D1351D86F3', '95E29E42-6ECC-BB6B-16D0-8911F42D6DA4', '0', null);
INSERT INTO "public"."user_info" VALUES ('11628e44-6f30-42e8-9a3e-ee4c283eac6c', null, '2016-06-28', '2016-06-28', null, '0', 'PUBLIC', '张林', '13681017068', '成都', '96e79218965eb72c92a549dd5a330112', '1                               ', '60978e73-851b-429d-9cf4-415300a64739', '43dfc018-1dc2-4881-8506-39bec6bbfe70', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('12121', null, '2015-06-30', '2015-06-30', null, '21', 'ADMIN', 'qkzadmin', '13800138000', '超级管理员', '96e79218965eb72c92a549dd5a330112', '1                               ', 'fanqie-shoudong-init', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '0', null);
INSERT INTO "public"."user_info" VALUES ('12f1edc8-6e3a-4023-a485-d5da89ddde4c', null, '2016-06-28', '2016-06-28', null, '0', 'PUBLIC', 'zhanglin', '13681017068', '成都', '96e79218965eb72c92a549dd5a330112', '1                               ', '60978e73-851b-429d-9cf4-415300a64739', '196c6f3d-6817-4106-b5d5-397f68c95905', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('13af17d4-2125-4811-88ba-fbb6774f8ee5', null, '2015-10-15', '2015-10-15', null, '0', 'PUBLIC', 'wso2701435', '13036415132', '宋双', '96e79218965eb72c92a549dd5a330112', '0                               ', 'd0392bc8-131c-8989-846e-c81c66011111', 'bc226803-a195-43dd-a627-a11fb9945ab3', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('15a66980-bb95-49fd-9bab-6d5e8a913088', null, '2015-07-24', '2015-07-24', null, '0', 'ADMIN', '85715336', null, '深圳笃行客', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd6d5316c-4756-426e-a019-6909a84fa8aa', 'ac1a9395-be3b-4754-966d-19b8c75278c6', '0', null);
INSERT INTO "public"."user_info" VALUES ('2df7667a-6cf4-4320-8449-6483c643ea62', null, '2015-06-15', '2015-06-12', null, '22', 'ADMIN', 'mtadmin', '13800138000', '超级管理员', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd0392bc8-131c-48a4-846e-c81c66097781', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '0', 'OPEN');
INSERT INTO "public"."user_info" VALUES ('2e2b17f1-35566-4665-88ea-26806bc47777', null, '2015-06-12', '2015-10-15', null, '2', 'ADMIN', 'feiniao', '13800138001', '飞鸟', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd0392bc8-131c-8989-846e-c81c66011111', '2e2b17f1-35566-4665-88ea-123456789r', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('3b2f2276-1cc4-48f2-8171-dab9c6bffa32', null, '2016-03-16', '2016-03-16', null, '0', 'ADMIN', '105752558', null, '番茄信用住', '96e79218965eb72c92a549dd5a330112', '1                               ', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'c8accf20-81a4-4831-b493-6262194ca9b0', '0', 'CREDIT');
INSERT INTO "public"."user_info" VALUES ('4778356f-8a67-4e3a-837b-560d362daeb1', null, '2016-04-05', '2016-06-17', null, '1', 'PUBLIC', '52414687', '13025145241', '23456', 'b3275960d68fda9d831facc0426c3bbc', '1                               ', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '4f81e45c-51b2-4fd9-a44c-2ca623b19841', '0', 'CREDIT');
INSERT INTO "public"."user_info" VALUES ('49b3d586-ba1b-45d6-bd69-0ce1b04b5cf9', null, '2016-09-08', '2016-09-08', null, '0', 'ADMIN', '45470283', null, '携程民宿', '96e79218965eb72c92a549dd5a330112', '1                               ', '33592699-8f9a-4e39-8082-110d33afb6c0', '20a26beb-a7ff-41ef-8c22-6bf4084d951f', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('4f2bac5a-c074-4db6-bb00-a801a27f1d7a', null, '2015-12-04', '2015-12-04', null, '0', 'ADMIN', '68051118', null, 'egg', '96e79218965eb72c92a549dd5a330112', '1                               ', '17ef83c0-89bc-4947-afcc-ea55f762cb1b', '6d3fcaba-f822-456e-848c-6102784004f5', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('523d4134-8594-4e95-ad28-e87440997e91', null, '2015-11-19', '2015-11-19', null, '0', 'ADMIN', '24796464', null, 'dsds', '96e79218965eb72c92a549dd5a330112', '1                               ', '97356a44-1dd5-47b7-a013-aec959427629', '07dceee1-200c-4524-b1c7-b09a1cffa0ea', '0', null);
INSERT INTO "public"."user_info" VALUES ('5583ff16-f4e7-43d5-8672-752034754e5f', null, '2015-11-19', '2015-11-19', null, '0', 'ADMIN', '104509771', null, 'sdsd', '96e79218965eb72c92a549dd5a330112', '1                               ', '6c628db6-cbd2-40a5-aaf5-77cf71ba3dd0', 'cda2f464-2835-4e22-ad2e-e93967d35db2', '0', null);
INSERT INTO "public"."user_info" VALUES ('5b9d3af8-f5e0-4111-a173-0b1d2728761b', null, '2015-11-19', '2015-11-19', null, '0', 'ADMIN', '92793998', null, 'sds1121', '96e79218965eb72c92a549dd5a330112', '1                               ', '0c0daf00-ab23-4d43-8dc4-0e7042ff71ba', '8fe49390-856d-4690-b338-2eb0fd5bbc12', '0', 'OPEN');
INSERT INTO "public"."user_info" VALUES ('613a4668-1f99-4682-9669-5b82a609b93f', null, '2016-08-17', '2016-08-17', null, '0', 'ADMIN', '95293351', null, '番茄精品活动', '96e79218965eb72c92a549dd5a330112', '1                               ', '5a779fe6-4fb6-46cc-9d35-d0683719f78a', 'b10a754c-a12e-4d66-8ab9-edd07a65747e', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('65ae4527-a582-4601-972a-336df9cdda18', null, '2015-12-04', '2015-12-04', null, '0', 'ADMIN', '94804456', null, 'egg', '96e79218965eb72c92a549dd5a330112', '1                               ', '7c872002-cbee-4aab-8161-2a0b64609304', '3bafe101-e6d7-420b-8e8e-86c440f17e1e', '0', 'OPEN');
INSERT INTO "public"."user_info" VALUES ('6978c16e-528a-471f-be95-262f48a13802', null, '2016-03-09', '2016-03-09', null, '0', 'ADMIN', '68931469', null, '测试用', '96e79218965eb72c92a549dd5a330112', '1                               ', '43d60bf4-a467-4f88-b451-cb45d346a0b7', '27c4e2d9-0890-4c31-8fed-2c02eabee9b0', '0', 'OPEN');
INSERT INTO "public"."user_info" VALUES ('6fa8f0be-ed0b-4668-a708-2b2612e22612', null, '2016-06-07', '2016-06-07', null, '0', 'ADMIN', '69779149', null, '胡夫人', '96e79218965eb72c92a549dd5a330112', '1                               ', '8356b7d9-9d6e-4bb1-a99d-e4b156a09280', 'f09c6461-0bbe-436c-9fea-fe18819459fa', '1', 'OPEN');
INSERT INTO "public"."user_info" VALUES ('785c393f-0aad-4091-b637-7f0a353e001e', null, '2015-07-24', '2015-07-24', null, '0', 'ADMIN', '75010522', null, '深圳笃行客', '96e79218965eb72c92a549dd5a330112', '1                               ', '60d53c4d-4bbc-4a17-8ba1-88028d1f9c3e', 'db9bff73-e7b3-4d7d-9e3a-18882b860f82', '1', null);
INSERT INTO "public"."user_info" VALUES ('88a5a977-6453-4870-a5f4-e497a046522f', null, '2016-03-16', '2016-03-16', null, '0', 'PUBLIC', 'credit', '15281017068', '咪咪', '96e79218965eb72c92a549dd5a330112', '1                               ', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', 'f3cf64f4-c2ca-4fe5-bd76-30e0a54c3f84', '0', 'CREDIT');
INSERT INTO "public"."user_info" VALUES ('c0a2aad1-5dd4-487a-b639-dacebcc4bf97', null, '2015-07-03', '2015-07-03', null, '0', 'PUBLIC', 'admin', '13800138000', '番茄管理员', '96e79218965eb72c92a549dd5a330112', '1                               ', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'cd5bef57-3376-450f-a13d-4a829dec071b', '0', null);
INSERT INTO "public"."user_info" VALUES ('c399b519-959f-4259-bce7-b27ecb7bb061', null, '2015-08-26', '2015-09-14', null, '3', 'ADMIN', '34203727', '13688889999', '第四城', '96e79218965eb72c92a549dd5a330112', '1                               ', '1e72f3a4-6e0e-4f1e-8562-5732906dc728', 'cdc42d0b-0dd6-4d3a-a433-5ea9c1cd6976', '0', null);
INSERT INTO "public"."user_info" VALUES ('c617333a-19fc-4337-8eab-4129703eaca3', null, '2015-08-26', '2015-08-26', null, '0', 'PUBLIC', 'fanqie', '13512345678', '康总', '96e79218965eb72c92a549dd5a330112', '0                               ', 'd0392bc8-131c-8989-846e-c81c66011111', '6edfe10c-9842-4883-af7f-92043a225fdf', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('cde491db-afe1-4085-b33e-104a4207ece8', null, '2015-07-24', '2015-07-24', null, '0', 'PUBLIC', 'duxingke', '13480993337', '谭烽', 'f64ac3012393bf1e52ac8ae1991be3ef', '1                               ', 'd6d5316c-4756-426e-a019-6909a84fa8aa', 'f6c00bc5-b041-4c6f-806c-7643dc95a5f1', '0', null);
INSERT INTO "public"."user_info" VALUES ('e42b5498-21c2-46d7-b08a-5fba4e916963', null, '2016-05-19', '2016-05-19', null, '1', 'PUBLIC', '饭袋（西门服务区）', '13333333333', '饭袋（西门服务区）', '202cb962ac59075b964b07152d234b70', '1                               ', '70c0a1ad-63a4-4d8f-b9f1-774366e0ee10', '899c6339-85bd-44ba-8f5f-9f3e7290944d', '0', 'CREDIT');
INSERT INTO "public"."user_info" VALUES ('f17dffd0-6e90-4eaf-9a11-53228c2ca914', null, '2016-04-15', '2016-04-15', null, '0', 'ADMIN', 'qunar', null, '去哪儿', '96e79218965eb72c92a549dd5a330112', '1                               ', 'e9781645-ccee-4eaf-8808-d09cc76a61e2', 'c41b5efb-e81d-4da1-8516-409015e4932d', '0', 'SALE');
INSERT INTO "public"."user_info" VALUES ('f59528c2-b833-4b4f-be0e-a36b0415ee87', null, '2016-04-12', '2016-04-12', null, '3', 'PUBLIC', '的是滴是滴', '13666666622', '的11', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd82f3bce-b1de-4d31-a582-f2695397bbbd', 'f869c1a7-2b81-4932-8a3b-cc015fe6ba9d', '0', 'CREDIT');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

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
-- Indexes structure for table otainfo_company_ref
-- ----------------------------
CREATE INDEX "otainfo_company_ref_ota_info_id_company_id_idx" ON "public"."otainfo_company_ref" USING btree (ota_info_id, company_id);

-- ----------------------------
-- Primary Key structure for table otainfo_company_ref
-- ----------------------------
ALTER TABLE "public"."otainfo_company_ref" ADD PRIMARY KEY ("id");

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
