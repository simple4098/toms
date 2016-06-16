ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "order_source" varchar(50);
COMMENT ON COLUMN "public"."ota_toms_order"."order_source" IS '订单来源';

update ota_toms_order set order_source = 'HAND' where channel_source = 'HAND_ORDER';
UPDATE ota_toms_order set order_source = 'SYSTEM' WHERE channel_source != 'HAND_ORDER';

--信用住取消订单
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "refund_status" bool;

COMMENT ON COLUMN "public"."ota_toms_order"."refund_status" IS '取消订单时，选择是否扣款，ture为扣款，false为不扣款';