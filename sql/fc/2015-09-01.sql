/*订单表，新增字段*/
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "guest_email" varchar(100),
ADD COLUMN "special_requirement" varchar(255),
ADD COLUMN "confirm_type" varchar(50),
ADD COLUMN "fc_bed_type" varchar(50),
ADD COLUMN "reserved_item" varchar(200);

COMMENT ON COLUMN "public"."ota_toms_order"."guest_email" IS '客人邮件联系方式';

COMMENT ON COLUMN "public"."ota_toms_order"."special_requirement" IS '特殊要求';

COMMENT ON COLUMN "public"."ota_toms_order"."reserved_item" IS '附加设置';

COMMENT ON COLUMN "public"."ota_toms_order"."fc_bed_type" IS '天下房仓房间类型';

COMMENT ON COLUMN "public"."ota_toms_order"."confirm_type" IS '订单确认方式';


-- 每日房间信息
ALTER TABLE "public"."ota_daily_infos"
ADD COLUMN "break_fast_type" varchar(100),
ADD COLUMN "break_fast_num" int4;

COMMENT ON COLUMN "public"."ota_daily_infos"."break_fast_type" IS '早餐类型';

COMMENT ON COLUMN "public"."ota_daily_infos"."break_fast_num" IS '早餐数量';

-- 入住人信息
ALTER TABLE "public"."ota_order_guests"
ADD COLUMN "nationality" varchar(50);

COMMENT ON COLUMN "public"."ota_order_guests"."nationality" IS '国籍';