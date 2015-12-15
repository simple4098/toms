ALTER TABLE "public"."timer_rate_price"
ADD COLUMN "rate_type" varchar(16);

COMMENT ON COLUMN "public"."timer_rate_price"."rate_type" IS '错误类型';

UPDATE  otainfo_company_ref set tb_type='DEFAULT' where id='572c1b47-9343-4567-981c-895ki8j18';