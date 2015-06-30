--初始化公司
INSERT INTO company VALUES ('fanqie-shoudong-init', null, '2015-06-30', '2015-06-30', null, '0', '亲的客栈', '0', '20150630', '1', '912', 'QKZ', 'qkz912');

--角色初始化数据
INSERT INTO role VALUES ('8a0219eb-88e8-4cd9-9b25-a6ebd1111111', null, '2015-06-30', null, null, 'admin', 'ROLE_A', '0');
--角色与权限关联数据
INSERT INTO role_permission VALUES ('1', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '6e3a7472-e62e-4cfa-bff6-5a30218a4c95', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('2', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', 'e37849ca-c47c-4558-a48d-2b2c6b53b0f4', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('3', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '499cbd11-a80e-4fc5-a38e-3d503fc3eb40', '2015-06-05', '2015-06-05');
INSERT INTO role_permission VALUES ('4', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', 'd8c49677-e86a-4a10-abf4-d5a6a2d3e8f9', '2015-06-05', '2015-06-05');

--公司权限初始化
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('112','fanqie-shoudong-init','499cbd11-a80e-4fc5-a38e-3d503fc3eb40',now(),now());

insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('13','fanqie-shoudong-init','d8c49677-e86a-4a10-abf4-d5a6a2d3e8f9',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('14','fanqie-shoudong-init','6e3a7472-e62e-4cfa-bff6-5a30218a4c95',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('15','fanqie-shoudong-init','e37849ca-c47c-4558-a48d-2b2c6b53b0f4',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('16','fanqie-shoudong-init','09df35b9-2076-4010-adda-4a72c1c57caf',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('17','fanqie-shoudong-init','b4d62ee9-4e05-4367-b526-ec1e40c26eb6',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('18','fanqie-shoudong-init','7aa33b7e-0b7c-4d82-bc8c-d6fdd09d584c',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('19','fanqie-shoudong-init','55ac4fe9-6bec-400a-b0c0-89f2c8993c97',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('110','fanqie-shoudong-init','37b68f35-62ec-49cf-98c6-1e3755bb54c1',now(),now());
insert into company_permission(id,company_id,permission_id,created_date,updated_date) 
values('111','fanqie-shoudong-init','26c5ceb0-a1ca-4600-bf54-eff51066fd7c',now(),now());

--用户数据
INSERT INTO user_info VALUES ('12121', null, '2015-06-30', '2015-06-30', null, '21', 'ADMIN', 'qkzadmin', '13800138000', '管理员', '96e79218965eb72c92a549dd5a330112', '1                               ', 'fanqie-shoudong-init', '8a0219eb-88e8-4cd9-9b25-a6ebd1111111', '0');
