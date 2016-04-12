CREATE TABLE "public"."other_consumer_function" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"deleted" int2,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"status" bool
)
WITH (OIDS=FALSE);
COMMENT ON COLUMN "public"."other_consumer_function"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."other_consumer_function"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."other_consumer_function"."status" IS '状态（f未开启，t开启）';


CREATE TABLE "public"."other_consumer_info" (
"id" varchar(64) COLLATE "default" NOT NULL,
"company_id" varchar(64) COLLATE "default",
"consumer_fun_id" varchar(64) COLLATE "default",
"level" int2,
"consumer_project_name" varchar(50) COLLATE "default",
"price_name" varchar(50) COLLATE "default",
"price" numeric(5,1),
"parent_id" varchar(64) COLLATE "default",
"deleted" int2,
"created_date" timestamp(6),
"updated_date" timestamp(6),
"status" bool,
"modifier_id" varchar(64) COLLATE "default",
"creator_id" varchar(64) COLLATE "default"
)
WITH (OIDS=FALSE);
COMMENT ON COLUMN "public"."other_consumer_info"."consumer_fun_id" IS '配置(other_consumer_function)表id';
COMMENT ON COLUMN "public"."other_consumer_info"."consumer_project_name" IS '消费项目名称';
CREATE INDEX "other_consumer_function_company_id_idx" ON "public"."other_consumer_function" USING btree (company_id);
ALTER TABLE "public"."other_consumer_function" ADD PRIMARY KEY ("id");


CREATE INDEX "other_consumer_info_company_id_parent_id_idx" ON "public"."other_consumer_info" USING btree (company_id, parent_id);
ALTER TABLE "public"."other_consumer_info" ADD PRIMARY KEY ("id");


--改价消息记录表
drop table if exists inn_change_price_msg;
CREATE TABLE "public"."inn_change_price_msg" (
"id" varchar(64) NOT NULL,
"inn_id" int4 NOT NULL,
"inn_name" varchar(64) NOT NULL,
"create_date" timestamp NOT NULL,
"update_date" timestamp,
"read" bool NOT NULL,
"message" varchar(255) NOT NULL,
"company_type" varchar(8),
PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

COMMENT ON COLUMN "public"."inn_change_price_msg"."inn_id" IS '客栈id';

COMMENT ON COLUMN "public"."inn_change_price_msg"."inn_name" IS '客栈名称';

COMMENT ON COLUMN "public"."inn_change_price_msg"."create_date" IS '记录创建时间';

COMMENT ON COLUMN "public"."inn_change_price_msg"."read" IS '是否已读，t为已读';

COMMENT ON COLUMN "public"."inn_change_price_msg"."message" IS '消息内容';

COMMENT ON COLUMN "public"."inn_change_price_msg"."company_type" IS '所属公司类型';

CREATE INDEX "created_at_index_key" ON "public"."inn_change_price_msg" USING btree ("create_date" "pg_catalog"."timestamp_ops" DESC NULLS LAST);

CREATE INDEX "msg_inn_id_index_key" ON "public"."inn_change_price_msg" USING btree ("inn_id");