ALTER TABLE "public"."dictionary"
ADD COLUMN "asynchronous_url" varchar(200),
ADD COLUMN "weather_asynchronous" int2;

COMMENT ON COLUMN "public"."dictionary"."asynchronous_url" IS '异步请求url';

COMMENT ON COLUMN "public"."dictionary"."weather_asynchronous" IS '是否为异步请求';

ALTER TABLE "public"."dictionary"
ALTER COLUMN "weather_asynchronous" SET DEFAULT 0;

ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "basic_total_price" decimal;

COMMENT ON COLUMN "public"."ota_toms_order"."basic_total_price" IS '订单下单总价';


ALTER TABLE "public"."ota_daily_infos"
ADD COLUMN "basic_price" decimal;

COMMENT ON COLUMN "public"."ota_daily_infos"."basic_price" IS '下单每日价格';
