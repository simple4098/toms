-- 新增toms订单中入住人数量，接口确认号
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "person" int2,
ADD COLUMN "interface_send_id" varchar(64);

COMMENT ON COLUMN "public"."ota_toms_order"."person" IS '入住人数量';

COMMENT ON COLUMN "public"."ota_toms_order"."interface_send_id" IS '接口确认号';

--新增入住人信息表中英文名
ALTER TABLE "public"."ota_order_guests"
ADD COLUMN "first_name" varchar(50),
ADD COLUMN "last_name" varchar(50);

COMMENT ON COLUMN "public"."ota_order_guests"."first_name" IS '英文名';

COMMENT ON COLUMN "public"."ota_order_guests"."last_name" IS '英文姓';

ALTER TABLE fc_rate_plan RENAME TO ota_rate_plan;

ALTER TABLE "public"."ota_rate_plan" ADD COLUMN "rate_code_default" bool;
ALTER TABLE "public"."ota_rate_plan" ADD COLUMN "rate_plan_code" varchar(50);


ALTER TABLE "public"."otainfo_company_ref" ADD COLUMN "xc_user_name" varchar(50);
ALTER TABLE "public"."otainfo_company_ref" ADD COLUMN "xc_password" varchar(50);
ALTER TABLE "public"."otainfo_company_ref" ADD COLUMN "user_id" varchar(50);
ALTER TABLE "public"."otainfo_company_ref" ADD COLUMN "updated_date" timestamp(6);

INSERT INTO  ota_info(id,ota_info,ota_type,deleted,sort)  VALUES ('3', '携程', 'XC', 0, 3);

