ALTER TABLE "public"."other_consumer_function"
ADD COLUMN "myself_channel_status" bool;

COMMENT ON COLUMN "public"."other_consumer_function"."myself_channel_status" IS '是否开启自定义渠道';

ALTER TABLE "public"."other_consumer_function"
ADD COLUMN "pms_channel_name_status" bool;

COMMENT ON COLUMN "public"."other_consumer_function"."pms_channel_name_status" IS 'pms客栈名称设置';

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "json_data" varchar(255);
COMMENT ON COLUMN "public"."ota_toms_order"."json_data" IS 'json数据';
