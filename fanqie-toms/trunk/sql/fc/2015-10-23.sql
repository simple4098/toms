ALTER TABLE "public"."timer_rate_price"
ADD COLUMN "error_content" varchar(255),
ADD COLUMN "created_date" timestamp,
ADD COLUMN "updated_date" timestamp,
ADD COLUMN "deleted" int2;

COMMENT ON COLUMN "public"."timer_rate_price"."error_content" IS '错误信息';

COMMENT ON COLUMN "public"."timer_rate_price"."created_date" IS '创建时间';

COMMENT ON COLUMN "public"."timer_rate_price"."deleted" IS '是否删除';

DROP INDEX "public"."timer_rate_price_company_id_ota_info_id_account_id_idx";
CREATE  INDEX "timer_rate_price_company_id_ota_info_id_account_id_idx" ON "public"."timer_rate_price" USING btree (company_id, ota_info_id, room_type_id, deleted);