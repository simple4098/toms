-- 创建用户
INSERT INTO  user_info("id", "creator_id", "created_date", "updated_date", "modifier_id", "version", "user_type", "login_name", "telephone", "user_name", "password", "data_permission", "company_id", "role_id", "deleted")
VALUES ('c0a2aad1-5dd4-487a-b639-dacebcc4bf97', NULL, '2015-7-3', '2015-7-3', NULL, 0, 'PUBLIC', 'admin', '13800138000', '番茄管理员', '96e79218965eb72c92a549dd5a330112', '1', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'cd5bef57-3376-450f-a13d-4a829dec071b', 0);
-- 创建公司
INSERT INTO  company("id", "creator_id", "created_date", "updated_date", "modifier_id", "version", "company_name", "deleted", "company_code", "type", "ota_id", "user_account", "user_password") VALUES ('19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', NULL, '2015-7-3', '2015-7-15', NULL, 3, '成都番茄来了技术有限公司', 0, '54041688', 1, 101, 'CS', 'cs123');
-- 权限
INSERT INTO "permission" ("id", "creator_id", "created_date", "updated_date", "modifier_id", "version", "permission_name", "url", "parent_id", "indexed", "deleted")
VALUES ('9414cf03-338c-4994-890e-a91463e4d792', NULL, NULL, NULL, NULL, NULL, '图片管理', '/system/images', NULL, 0, 0);
INSERT INTO "permission" ("id", "creator_id", "created_date", "updated_date", "modifier_id", "version", "permission_name", "url", "parent_id", "indexed", "deleted")
VALUES ('7a6f7b47-d779-41a9-a09f-5289479cab0e', NULL, NULL, NULL, NULL, NULL, '公司管理', '/system/find_companys', NULL, 0, 0);
INSERT INTO "permission" ("id", "creator_id", "created_date", "updated_date", "modifier_id", "version", "permission_name", "url", "parent_id", "indexed", "deleted")
VALUES ('2e2b17f1-202f-4665-88ea-26806bc45660', NULL, NULL, NULL, NULL, NULL, '订单管理', '/order/find_orders', NULL, 0, 0);
-- 创建公司具有的权限
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('5ddd47f6-d996-472a-b131-0b36e23567e7', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('b23999be-f83a-4930-9305-500a42223b1e', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '37b68f35-62ec-49cf-98c6-1e3755bb54c1', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('cf5e7a2e-71d3-43c7-a688-ee7a4a91bd1c', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('e6425d33-585a-425c-a129-0651b8049f8e', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '09df35b9-2076-4010-adda-4a72c1c57caf', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('df2a01fb-ef22-4526-98f3-1aa5d2956947', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '26c5ceb0-a1ca-4600-bf54-eff51066fd7c', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('66f9f57d-7ff4-49c5-8dd0-55bfd7c396b5', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('962b6860-d311-4016-b664-b962321f3016', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'b4d62ee9-4e05-4367-b526-ec1e40c26eb6', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('8940069b-dc74-48bc-b7bb-a8e208f8121b', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('36db59a3-1eaa-4662-8064-f90b82d4cb54', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '7a6f7b47-d779-41a9-a09f-5289479cab0e', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('111615f1-26ff-4c68-8ddb-42ca59efd223', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '9414cf03-338c-4994-890e-a91463e4d792', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('ce9c119a-3d16-4a06-8098-dcf794c002d6', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('dd76abde-2336-4f57-ac90-50bdc1ca180e', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-7-22', '2015-7-22');
INSERT INTO company_permission("id", "company_id", "permission_id", "created_date", "updated_date") VALUES ('7f593e66-4fd9-4f9b-a037-b282a581bc87', '19f7ed10-6a1c-4b56-bb98-ccbde9b4cbd4', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-7-22', '2015-7-22');
-- 创建角色
INSERT INTO "role" ("id", "creator_id", "created_date", "updated_date", "role_desc", "role_name", "role_key", "indexed") VALUES ('cd5bef57-3376-450f-a13d-4a829dec071b', NULL, '2015-7-3', NULL, 'ROLE_SUPER', 'ROLE_SUPER', 'ROLE_SUPER', 13);
-- 角色权限
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date") VALUES ('afc26a52-1fb5-44ca-b21c-7a82c198fea9', 'cd5bef57-3376-450f-a13d-4a829dec071b', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-7-22', '2015-7-22');


-- 创建飞鸟用户
INSERT INTO  user_info("id", "creator_id", "created_date", "updated_date", "modifier_id", "version", "user_type", "login_name", "telephone", "user_name", "password", "data_permission", "company_id", "role_id", "deleted")
VALUES ('88888888user', NULL, '2015-6-12', '2015-7-15', NULL, 1, 'ADMIN', 'feiniao', '13800138001', '飞鸟', '96e79218965eb72c92a549dd5a330112', '1', 'd0392bc8-131c-8989-846e-c81c66011111', '88888888role', 0);
-- 创建飞鸟用户角色
INSERT INTO  role("id", "creator_id", "created_date", "updated_date", "role_desc", "role_name", "role_key", "indexed")
VALUES ('88888888role', NULL, '2015-6-12', NULL, 'ROLE_FEI', 'ROLE_FEI', 'ROLE_10', 10);
-- 创建角色与权限关联关系
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date")
VALUES ('f130a2ab-4ea3-4a85-a86f-e90936cece36', '88888888role', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-7-22', '2015-7-22');
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date")
VALUES ('feaec74f-a6b8-4cbc-aaec-940f742f2da3', '88888888role', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-7-22', '2015-7-22');
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date")
VALUES ('4a4c81d7-78a4-47f3-bf51-1e994d9b822a', '88888888role', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-7-22', '2015-7-22');
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date")
VALUES ('8dce63ab-459f-4e85-ad64-b82e6c6bd373', '88888888role', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-7-22', '2015-7-22');
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date")
VALUES ('885ef003-b214-4469-bd08-0e61fb44ff6b', '88888888role', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-7-22', '2015-7-22');
INSERT INTO  role_permission("id", "role_id", "permission_id", "created_date", "updated_date")
VALUES ('cd1b8a6e-16ed-480f-bc15-cd77b9e3d464', '88888888role', '9414cf03-338c-4994-890e-a91463e4d792', '2015-7-22', '2015-7-22');
-- 公司具有的权限
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('0973cf5f-b128-46c1-921d-b5edc22c423e', 'd0392bc8-131c-8989-846e-c81c66011111', '2e2b17f1-202f-4665-88ea-26806bc45660', '2015-7-6', '2015-7-6');
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('c8e1a8eb-79a7-4e07-b988-21bd02e3dbda', 'd0392bc8-131c-8989-846e-c81c66011111', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-7-6', '2015-7-6');
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('92677dae-bd41-438a-a5b9-c7e33370eb39', 'd0392bc8-131c-8989-846e-c81c66011111', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-7-6', '2015-7-6');
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('de480d20-b215-4a4a-a7ea-ee039a04ce50', 'd0392bc8-131c-8989-846e-c81c66011111', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-7-6', '2015-7-6');
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('f14d8cb0-733c-4888-84b2-6d2b7180ca85', 'd0392bc8-131c-8989-846e-c81c66011111', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-7-6', '2015-7-6');
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('20104676-d9fa-4496-89cf-afbbb5b4cd09', 'd0392bc8-131c-8989-846e-c81c66011111', '55ac4fe9-6bec-400a-b0c0-89f2c8993c97', '2015-7-6', '2015-7-6');
INSERT INTO  company_permission("id", "company_id", "permission_id", "created_date", "updated_date")
VALUES ('9414cf03-338c-4994-890e-a91463e4d792', 'd0392bc8-131c-8989-846e-c81c66011111', '9414cf03-338c-4994-890e-a91463e4d792', '2015-7-22', '2015-7-22');
