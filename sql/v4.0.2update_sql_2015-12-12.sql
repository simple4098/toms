-- 新增toms订单字段，客栈名称，房型名称
ALTER TABLE "public"."ota_toms_order"
ADD COLUMN "order_inn_name" varchar(100),
ADD COLUMN "order_room_type_name" varchar(100);

COMMENT ON COLUMN "public"."ota_toms_order"."order_inn_name" IS '客栈名称';

COMMENT ON COLUMN "public"."ota_toms_order"."order_room_type_name" IS '房型名称';

-- 更新toms订单表中客栈名称字段
update ota_toms_order  set order_inn_name = bi.inn_name FROM bang_inn bi where  ota_toms_order.company_id = bi.company_id and ota_toms_order.inn_id = bi.inn_id
-- 更新toms订单表中客栈房型名称字段
update ota_toms_order  set order_room_type_name = obi.room_type_name  FROM ota_bang_inn_room obi where  ota_toms_order.ota_room_type_id = obi.rid