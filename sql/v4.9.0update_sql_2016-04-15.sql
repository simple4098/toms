
ALTER TABLE "public"."bang_inn"
ADD COLUMN "address" varchar(200);

COMMENT ON COLUMN "public"."bang_inn"."address" IS '客栈地址';

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "xml_data" varchar(255);

COMMENT ON COLUMN "public"."ota_toms_order"."xml_data" IS '创建订单请求的xml数据';
ALTER TABLE "public"."ota_toms_order"
ALTER COLUMN "xml_data" TYPE text COLLATE "default";

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "user_id" varchar(64);

COMMENT ON COLUMN "public"."ota_toms_order"."user_id" IS '操作人id';


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
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table bang_inn_to_qunar_city
-- ----------------------------
ALTER TABLE "public"."bang_inn_to_qunar_city" ADD PRIMARY KEY ("id");


--ota
ALTER TABLE "public"."ota_inn_ota"
ADD COLUMN "updated_date" timestamp(6),
ADD COLUMN "created_date" timestamp(6);

-- toms 需要将去哪儿的qunar_city_info
INSERT INTO "public"."otainfo_company_ref" ("id", "ota_info_id", "company_id", "appkey", "appsecret", "sessionkey", "status", "expired_time", "used_price_model", "tb_type", "create_date", "xc_user_name", "xc_password", "updated_date", "user_id", "vendor_id") VALUES ('486512154', '6', 'ba90be55-b5a8-4bcd-8f36-56d008eba099', '1111', '11111', 'abcd', '0', '2016-04-18 16:08:27', 'MAI', 'DEFAULT', '2016-04-18 16:08:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."ota_info" ("id", "ota_info", "ota_type", "deleted", "sort") VALUES ('6', '去哪儿', 'QUNAR', '0', '6');

-- 番茄管家
INSERT INTO "public"."tomato_proxysale_channel" ("update_time", "valid", "operator", "id", "company_code", "inn_push_url", "commission_push_url", "channel_name") VALUES ('2016-05-03', NULL, '190', '904', '52132513', NULL, NULL, '去哪儿分销商');
-- oms
INSERT INTO "public"."tomato_oms_ota_info" ("id", "name", "user_code", "user_password", "authority", "api_type_id", "ota_id", "vendor_type", "logo_path", "pid", "ota_group", "ota_http_url", "ota_type", "push_addr", "push_config", "manager", "proxy_type_config", "common_proxy_strategy") VALUES ('904', '去哪儿分销商', 'QUNAR', 'qunar123', NULL, '1', '904', '2', NULL, '102', NULL, NULL, NULL, 'http://toms.fanqiele.com/qunarService', NULL, 'toms', '{"DI":0,"MAI2DI":0}', 'MAI');
