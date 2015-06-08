--初始化公司
INSERT INTO company VALUES ('d0392bc8-131c-48a4-846e-c81c66097781', null, '2015-05-15', '2015-05-15', null, '0', '丽江漫途信息技术有限公司', '0', '56458653', '1', '901', 'MTB', 'mt911');

--权限初始化数据
INSERT INTO permission VALUES ('09df35b9-2076-4010-adda-4a72c1c57caf', null, null, null, null, null, '房态房量', '/oms/obtRoomType', null, '0', '0');
INSERT INTO permission VALUES ('26c5ceb0-a1ca-4600-bf54-eff51066fd7c', null, null, null, null, null, '客栈列表', '/inn_manage/find_inns', null, '0', '0');
INSERT INTO permission VALUES ('37b68f35-62ec-49cf-98c6-1e3755bb54c1', null, null, null, null, null, '客栈活跃', '/inn_manage/activeInn', null, '0', '0');
INSERT INTO permission VALUES ('499cbd11-a80e-4fc5-a38e-3d503fc3eb40', null, null, null, null, null, '账号设置', '/user/find_users', null, '0', '0');
INSERT INTO permission VALUES ('55ac4fe9-6bec-400a-b0c0-89f2c8993c97', null, null, null, null, null, '订单来源', '/operate/order', null, '0', '0');
INSERT INTO permission VALUES ('6e3a7472-e62e-4cfa-bff6-5a30218a4c95', null, null, null, null, null, '客栈标签', '/system/find_labels', null, '0', '0');
INSERT INTO permission VALUES ('7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c', null, null, null, null, null, '客户分布', '/operate/kf', null, '0', '0');
INSERT INTO permission VALUES ('b4d62ee9-4e05-4367-b526-ec1e40c26eb6', null, null, null, null, null, '运营趋势', '/operate/qs', null, '0', '0');
INSERT INTO permission VALUES ('d8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', null, null, null, null, null, '通知模板', '/system/find_notices', null, '0', '0');
INSERT INTO permission VALUES ('e37849ca-c47c-4558-a48d-2b2c6b53b0f4', null, null, null, null, null, '消息通知', '/notice/find_notices', null, '0', '0');

--角色初始化数据
INSERT INTO role VALUES ('4a26fe8d-db59-4a2e-82f4-90f668e74188', null, '2015-06-04', null, 'ROLE_1', 'ROLE_1', 'ROLE_1', '1');
INSERT INTO role VALUES ('6f659012-b5e8-4b8b-804b-5560089ae4cd', null, '2015-05-14', null, null, 'admin', 'ROLE_ADMIN', '0');
INSERT INTO role VALUES ('6f9e7ba6-eb13-4933-b81e-74d6472c500e', null, '2015-05-14', null, null, 'admin', 'ROLE_ADMIN', '0');
INSERT INTO role VALUES ('804d0e27-c63b-4b25-9972-653bbfc0877a', null, '2015-05-14', null, null, 'admin', 'ROLE_ADMIN', '0');
INSERT INTO role VALUES ('8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', null, '2015-05-14', null, null, 'admin', 'ROLE_ADMIN', '0');

--角色与权限关联数据

INSERT INTO  role_permission VALUES ('20146416-d906-4c79-986c-a51eb4479668', '4a26fe8d-db59-4a2e-82f4-90f668e74188', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-06-04', '2015-06-04');
INSERT INTO role_permission VALUES ('5782550b-bc1b-4df1-8927-93aac58f77a1', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('661da333-7c0a-44ad-8916-4d61e2764829', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('99fa9e99-b17c-4ba8-8ce9-d37aa0b1e2b8', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('b6604352-1f4e-4266-816a-fd5e40dde5a2', '4a26fe8d-db59-4a2e-82f4-90f668e74188', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-06-04', '2015-06-04');
INSERT INTO role_permission VALUES ('bdee23f4-2d31-4648-87b7-cefc3deedd92', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('c63afe86-d231-4011-9658-9e3d442e0571', '4a26fe8d-db59-4a2e-82f4-90f668e74188', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-06-04', '2015-06-04');

--公司权限初始化
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('1','d0392bc8-131c-48a4-846e-c81c66097781','499cbd11-a80e-4fc5-a38e-3d503fc3eb40',now(),now());

insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('3','d0392bc8-131c-48a4-846e-c81c66097781','d8c49677-e86a-4a10-abf4-d5a6a2d3e8f9',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('4','d0392bc8-131c-48a4-846e-c81c66097781','6e3a7472-e62e-4cfa-bff6-5a30218a4c95',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('5','d0392bc8-131c-48a4-846e-c81c66097781','e37849ca-c47c-4558-a48d-2b2c6b53b0f4',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('6','d0392bc8-131c-48a4-846e-c81c66097781','09df35b9-2076-4010-adda-4a72c1c57caf',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('7','d0392bc8-131c-48a4-846e-c81c66097781','b4d62ee9-4e05-4367-b526-ec1e40c26eb6',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('8','d0392bc8-131c-48a4-846e-c81c66097781','7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('9','d0392bc8-131c-48a4-846e-c81c66097781','55ac4fe9-6bec-400a-b0c0-89f2c8993c97',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('10','d0392bc8-131c-48a4-846e-c81c66097781','37b68f35-62ec-49cf-98c6-1e3755bb54c1',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('11','d0392bc8-131c-48a4-846e-c81c66097781','26c5ceb0-a1ca-4600-bf54-eff51066fd7c',now(),now());

--用户数据
INSERT INTO user_info VALUES ('2df7667a-6cf4-4320-8449-6483c643ea62', null, '2015-05-14', '2015-05-28', null, '21', 'ADMIN', 'test', '13800138000', 'superMan', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd0392bc8-131c-48a4-846e-c81c66097781', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '0');
INSERT INTO user_info VALUES ('839cca94-32d9-4df3-95d6-87fcfb8fee29', null, '2015-05-22', '2015-05-27', null, '1', 'ADMIN', 'test1', '138001380000', 'test', '96e79218965eb72c92a549dd5a330112', '1                               ', 'd0392bc8-131c-48a4-846e-c81c66097781', '8a0219eb-88e8-4cd9-9b25-a6ebd6ada402', '0');