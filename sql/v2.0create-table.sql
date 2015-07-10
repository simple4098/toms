--增加bang_inn表字段
alter table bang_inn add column deleted int2;
alter table bang_inn add column ota_wg_id varchar(64);
alter table bang_inn add column account_id_di int4;
update bang_inn set deleted=0;


CREATE TABLE  ota_inn_ota (
"id" varchar(64) COLLATE "default" NOT NULL,
"wg_hid" varchar(64) COLLATE "default",
"commission_percent" numeric(3,2),
"company_id" varchar(64) COLLATE "default",
"alias_inn_name" varchar(30) COLLATE "default",
"ota_id" varchar(64) COLLATE "default",
"price_model" varchar(50) COLLATE "default",
"sjia_model" varchar COLLATE "default",
"deleted" int2,
CONSTRAINT "pk_ota_inn_ota" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_inn_ota" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_inn_ota"."wg_hid" IS 'tp店酒店id';
COMMENT ON COLUMN "public"."ota_inn_ota"."commission_percent" IS '佣金比例';
COMMENT ON COLUMN "public"."ota_inn_ota"."company_id" IS '企业id';
COMMENT ON COLUMN "public"."ota_inn_ota"."alias_inn_name" IS '客栈别名';
COMMENT ON COLUMN "public"."ota_inn_ota"."ota_id" IS 'OTA id';
COMMENT ON COLUMN "public"."ota_inn_ota"."price_model" IS '价格模式';
COMMENT ON COLUMN "public"."ota_inn_ota"."sjia_model" IS '上架模式';
COMMENT ON COLUMN "public"."ota_inn_ota"."deleted" IS '是否删除';



CREATE TABLE  ota_price_model (
"id" varchar(64) COLLATE "default" NOT NULL,
"price_model" varchar(20) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"price_model_value" numeric(6,2),
"created_date" timestamp(6),
"deleted" int2,
CONSTRAINT "pk_ota_price_model" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_price_model" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_price_model"."price_model" IS '价格模式';
COMMENT ON COLUMN "public"."ota_price_model"."ota_wg_id" IS 'ota_inn_ota 表id';
COMMENT ON COLUMN "public"."ota_price_model"."price_model_value" IS '价格模式值';
COMMENT ON COLUMN "public"."ota_price_model"."deleted" IS '是否删除';
CREATE INDEX "ota_price_model_ota_wg_id_idx" ON "public"."ota_price_model" USING btree (ota_wg_id);


CREATE TABLE ota_bang_inn_room (
"id" varchar(64) COLLATE "default" NOT NULL,
"inn_id" int8,
"room_type_id" int8,
"room_type_name" varchar(25) COLLATE "default",
"company_id" varchar(64) COLLATE "default",
"rid" varchar(30) COLLATE "default",
"price_model_id" varchar(64) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"deleted" int2,
CONSTRAINT "pk_ota_bang_inn_room" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ota_bang_inn_room" OWNER TO "ota";
COMMENT ON COLUMN "public"."ota_bang_inn_room"."inn_id" IS '客栈id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."room_type_id" IS '房型id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."room_type_name" IS '房型名字';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."rid" IS 'TP点房型id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."price_model_id" IS '价格模式id';
COMMENT ON COLUMN "public"."ota_bang_inn_room"."ota_wg_id" IS 'ota_inn_ota  id';
COMMENT ON COLUMN "public"."ota_price_model"."deleted" IS '是否删除';
CREATE INDEX "ota_bang_inn_room_company_id_ota_wg_id_idx" ON "public"."ota_bang_inn_room" USING btree (company_id, ota_wg_id);


CREATE TABLE ota_info (
"id" varchar COLLATE "default" NOT NULL,
"ota_info" varchar(20) COLLATE "default",
"ota_type" varchar(10) COLLATE "default",
"deleted" int2,
"company_id" varchar(64) COLLATE "default",
"appkey" varchar COLLATE "default",
"appsecret" varchar COLLATE "default",
"sessionkey" varchar COLLATE "default",
"expired_time" timestamp(6),
CONSTRAINT "pk_ota_info" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_info" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_info"."ota_info" IS 'OTA 描述';

COMMENT ON COLUMN "public"."ota_info"."ota_type" IS 'OTA 类型';

COMMENT ON COLUMN "public"."ota_info"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."ota_info"."company_id" IS '公司id';

COMMENT ON COLUMN "public"."ota_info"."appkey" IS '第三方 key';

COMMENT ON COLUMN "public"."ota_info"."appsecret" IS '第三方 secret';

COMMENT ON COLUMN "public"."ota_info"."sessionkey" IS '第三方 sessionkey';

COMMENT ON COLUMN "public"."ota_info"."expired_time" IS '失效时间';

CREATE TABLE ota_inn_roomtype_goods (
"id" varchar(60) COLLATE "default" NOT NULL,
"room_type_id" varchar(30) COLLATE "default",
"inn_id" varchar(30) COLLATE "default",
"rpid" varchar(30) COLLATE "default",
"gid" varchar(30) COLLATE "default",
"company_id" varchar(30) COLLATE "default",
"ota_wg_id" varchar(64) COLLATE "default",
"rid" varchar(30) COLLATE "default",
"product_date" timestamp(6),
"deleted" int2 DEFAULT 0,
CONSTRAINT "pk_ota_inn_roomtype_goods" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_inn_roomtype_goods" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_inn_roomtype_goods"."rid" IS 'tp房型id';

COMMENT ON COLUMN "public"."ota_inn_roomtype_goods"."product_date" IS '商品上架更新时间';



CREATE INDEX "ota_inn_roomtype_goods_rid_idx" ON "public"."ota_inn_roomtype_goods" USING btree (rid);


-- 订单

CREATE TABLE ota_toms_order (
"id" varchar(64) COLLATE "default" NOT NULL,
"version" int4,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"deleted" int4,
"creator_id" varchar(64) COLLATE "default",
"modifier_id" varchar(64) COLLATE "default",
"channel_source" varchar(30) COLLATE "default",
"channel_order_code" varchar(32) COLLATE "default",
"order_status" varchar(20) COLLATE "default",
"inn_id" int4,
"guest_name" varchar(50) COLLATE "default",
"room_type_id" varchar(20) COLLATE "default",
"home_amount" int4,
"live_time" timestamp(6),
"leave_time" timestamp(6),
"total_price" numeric,
"prepay_price" numeric,
"cost_price" numeric,
"ota_price" numeric,
"order_time" timestamp(6),
"ota_room_type_id" varchar(64) COLLATE "default",
"ota_hotel_id" varchar(64) COLLATE "default",
"ota_rate_plan_id" varchar(64) COLLATE "default",
"ota_gid" varchar(64) COLLATE "default",
"eariest_arrive_time" timestamp(6),
"lastest_arrive_time" timestamp(6),
"currency" varchar(30) COLLATE "default",
"payment_type" varchar(32) COLLATE "default",
"guest_mobile" varchar(32) COLLATE "default",
"ota_rate_code" varchar(64) COLLATE "default",
"fee_status" varchar(20) COLLATE "default",
"comment" varchar(255) COLLATE "default",
"reason" varchar(255) COLLATE "default",
"alipay_trade_no" varchar(50) COLLATE "default",
"payment" numeric,
CONSTRAINT "pk_ota_toms_order" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_toms_order" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_toms_order"."id" IS 'ID';

COMMENT ON COLUMN "public"."ota_toms_order"."version" IS '版本号';

COMMENT ON COLUMN "public"."ota_toms_order"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."ota_toms_order"."updated_date" IS '更新时间';

COMMENT ON COLUMN "public"."ota_toms_order"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."ota_toms_order"."creator_id" IS '创建者ID';

COMMENT ON COLUMN "public"."ota_toms_order"."modifier_id" IS '更新者ID';

COMMENT ON COLUMN "public"."ota_toms_order"."channel_source" IS '订单来源渠道';

COMMENT ON COLUMN "public"."ota_toms_order"."channel_order_code" IS '渠道订单号';

COMMENT ON COLUMN "public"."ota_toms_order"."order_status" IS '订单状态';

COMMENT ON COLUMN "public"."ota_toms_order"."inn_id" IS '客栈ID';

COMMENT ON COLUMN "public"."ota_toms_order"."guest_name" IS '客人名称';

COMMENT ON COLUMN "public"."ota_toms_order"."room_type_id" IS '房型ID';

COMMENT ON COLUMN "public"."ota_toms_order"."home_amount" IS '房间数量';

COMMENT ON COLUMN "public"."ota_toms_order"."live_time" IS '入住时间';

COMMENT ON COLUMN "public"."ota_toms_order"."leave_time" IS '离店时间';

COMMENT ON COLUMN "public"."ota_toms_order"."total_price" IS '总价';

COMMENT ON COLUMN "public"."ota_toms_order"."prepay_price" IS '预付价格';

COMMENT ON COLUMN "public"."ota_toms_order"."cost_price" IS '成本价';

COMMENT ON COLUMN "public"."ota_toms_order"."ota_price" IS 'OTA佣金';

COMMENT ON COLUMN "public"."ota_toms_order"."order_time" IS '下单时间';

COMMENT ON COLUMN "public"."ota_toms_order"."ota_room_type_id" IS 'OTA房型ID';

COMMENT ON COLUMN "public"."ota_toms_order"."ota_hotel_id" IS 'OTA酒店ID';

COMMENT ON COLUMN "public"."ota_toms_order"."ota_rate_plan_id" IS 'OTA价格策略ID';

COMMENT ON COLUMN "public"."ota_toms_order"."ota_gid" IS 'OTA商品ID';

COMMENT ON COLUMN "public"."ota_toms_order"."eariest_arrive_time" IS '最早到店时间';

COMMENT ON COLUMN "public"."ota_toms_order"."lastest_arrive_time" IS '最晚到店时间';

COMMENT ON COLUMN "public"."ota_toms_order"."currency" IS '货币类型';

COMMENT ON COLUMN "public"."ota_toms_order"."payment_type" IS '付款方式';

COMMENT ON COLUMN "public"."ota_toms_order"."guest_mobile" IS '联系人电话';

COMMENT ON COLUMN "public"."ota_toms_order"."ota_rate_code" IS 'OTA价格策略';

COMMENT ON COLUMN "public"."ota_toms_order"."fee_status" IS '付款状态';

COMMENT ON COLUMN "public"."ota_toms_order"."comment" IS '备注';

COMMENT ON COLUMN "public"."ota_toms_order"."reason" IS '取消订单原因';

COMMENT ON COLUMN "public"."ota_toms_order"."alipay_trade_no" IS '支付宝交易号';

COMMENT ON COLUMN "public"."ota_toms_order"."payment" IS '付款金额';

-- 每日价格
CREATE TABLE ota_daily_infos (
"id" varchar(64) COLLATE "default" NOT NULL,
"day" date,
"created_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
"price" numeric(10,2),
CONSTRAINT "pk_ota_daily_infos" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_daily_infos" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_daily_infos"."id" IS 'ID';

COMMENT ON COLUMN "public"."ota_daily_infos"."day" IS '时间';

COMMENT ON COLUMN "public"."ota_daily_infos"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."ota_daily_infos"."order_id" IS '订单ID';

COMMENT ON COLUMN "public"."ota_daily_infos"."price" IS '价格';

-- 入住人信息
CREATE TABLE ota_order_guests (
"id" varchar(64) COLLATE "default" NOT NULL,
"name" varchar(32) COLLATE "default",
"room_pos" int4,
"created_date" timestamp(6),
"order_id" varchar(64) COLLATE "default",
CONSTRAINT "pk_ota_order_guests" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_order_guests" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_order_guests"."id" IS 'ID';

COMMENT ON COLUMN "public"."ota_order_guests"."name" IS '客人名称';

COMMENT ON COLUMN "public"."ota_order_guests"."room_pos" IS '房间编号';

COMMENT ON COLUMN "public"."ota_order_guests"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."ota_order_guests"."order_id" IS '订单ID';


-- 字典表
CREATE TABLE dictionary (
"id" varchar(64) COLLATE "default" NOT NULL,
"desc" varchar(100) COLLATE "default",
"url" varchar(100) COLLATE "default",
"type" varchar(20) COLLATE "default",
"value" varchar(30) COLLATE "default",
"v_name" varchar(50) COLLATE "default",
"v_pwd" varchar(64) COLLATE "default",
CONSTRAINT "dictionary_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."dictionary" OWNER TO "ota";

COMMENT ON COLUMN "public"."dictionary"."id" IS 'ID';

COMMENT ON COLUMN "public"."dictionary"."desc" IS '注释';

COMMENT ON COLUMN "public"."dictionary"."url" IS '请求的url';

COMMENT ON COLUMN "public"."dictionary"."type" IS '类型';

COMMENT ON COLUMN "public"."dictionary"."value" IS '值';

COMMENT ON COLUMN "public"."dictionary"."v_name" IS '账号名称';

COMMENT ON COLUMN "public"."dictionary"."v_pwd" IS '账号密码';

INSERT INTO "public"."dictionary" VALUES ('1', '取消订单同步OMS', 'http://192.168.1.158:8888/api/cancelOrder', 'CANCEL_ORDER', '903', 'TB', 'tb');
INSERT INTO "public"."dictionary" VALUES ('2', '创建订单', 'http://192.168.1.158:8888/api/order', 'CREATE_ORDER', '903', 'TB', 'tb');
INSERT INTO "public"."dictionary" VALUES ('3', '查询订单状态', 'http://192.168.1.158:8888/api/queryOrder', 'ORDER_STATUS', '903', 'TB', 'tb');


-- ota info配置信息
CREATE TABLE "public"."ota_info" (
"id" varchar COLLATE "default" NOT NULL,
"ota_info" varchar(20) COLLATE "default",
"ota_type" varchar(10) COLLATE "default",
"deleted" int2,
"company_id" varchar(64) COLLATE "default",
"appkey" varchar COLLATE "default",
"appsecret" varchar COLLATE "default",
"sessionkey" varchar COLLATE "default",
"expired_time" timestamp(6),
CONSTRAINT "pk_ota_info" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."ota_info" OWNER TO "ota";

COMMENT ON COLUMN "public"."ota_info"."ota_info" IS 'OTA 描述';

COMMENT ON COLUMN "public"."ota_info"."ota_type" IS 'OTA 类型';

COMMENT ON COLUMN "public"."ota_info"."deleted" IS '是否删除';

COMMENT ON COLUMN "public"."ota_info"."company_id" IS '公司id';

COMMENT ON COLUMN "public"."ota_info"."appkey" IS '第三方 key';

COMMENT ON COLUMN "public"."ota_info"."appsecret" IS '第三方 secret';

COMMENT ON COLUMN "public"."ota_info"."sessionkey" IS '第三方 sessionkey';

COMMENT ON COLUMN "public"."ota_info"."expired_time" IS '失效时间';

INSERT INTO "public"."ota_info" VALUES ('1', '淘宝', 'TB', '0', '88888888', '23192376', 'c2e9acffbdf281c93b167601781cd228', '61014230a6162d458a44a75692d98da13f11ab9d397ac672555889376', null);
INSERT INTO "public"."ota_info" VALUES ('2', '美团', 'MT', '1', '88888888', '1', '122', '233', null);