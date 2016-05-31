ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "order_source" varchar(50);
COMMENT ON COLUMN "public"."ota_toms_order"."order_source" IS '订单来源';

update ota_toms_order set order_source = 'HAND' where channel_source = 'HAND_ORDER';
UPDATE ota_toms_order set order_source = 'SYSTEM' WHERE channel_source != 'HAND_ORDER';