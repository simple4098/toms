--归属地相关
ALTER TABLE "public"."ota_order_guests"
ADD COLUMN "guest_province" varchar(40),
ADD COLUMN "guest_city" varchar(40);

COMMENT ON COLUMN "public"."ota_order_guests"."guest_province" IS '客人所属省份';

COMMENT ON COLUMN "public"."ota_order_guests"."guest_city" IS '客人所属城市';
