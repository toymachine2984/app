INSERT INTO public.role(id, description,name) values
(1,'System admin','ROLE_ADMIN'),
(2,'System default user','ROLE_USER');

INSERT INTO public.user(id, expired,first_name,last_name, locked,login,password,isfirstlogin) values
(-1, false, 'Sanzhar', 'Shuraev', false, 'admin@test.com', '$2a$10$yyoeTe46mJXTRI52hUrMXOjIv4MOsskE1HjpEt2SXYreZHfh13NNS',false),
(-2, false, 'Sanzhar', 'Shuraev', false, 'user@test.com', '$2a$10$yyoeTe46mJXTRI52hUrMXOjIv4MOsskE1HjpEt2SXYreZHfh13NNS',false);
INSERT INTO user_role(user_id,role_id) values (-1,1),
(-2,2),
(-1,2);

INSERT INTO  data.department(id,description, name) values
(-1,'', 'Отдел снабжения'),
(-2,'', 'Бухгалтерия'),
(-3,'', 'IT отдел'),
(-4,'', 'Юридический отдел'),
(-5,'','Отдел технической поддержки'),
(-6,'','Отдел кадров');

INSERT INTO  data.address(id,building,city,street) values
(-1,'64','Алматы','Центральная'),
(-2,'46','Алматы','Байтурсынова'),
(-3,'46','Алматы','Абая'),
(-4,'87','Алматы','Жандосова'),
(-5,'76','Алматы','Сейфулина'),
(-6,'80','Алматы','Аль-фараби'),
(-7,'91','Алматы','Ленина'),
(-8,'48','Алматы','Шевченко'),
(-9,'17','Алматы','Темирязева'),
(-10,'86','Алматы','ТолеБи'),
(-11,'60','Алматы','Мамушылы'),
(-12,'79','Алматы','Абая'),
(-13,'1','Алматы','ЖибекЖолы'),
(-14,'38','Алматы','Абая');

INSERT INTO data.employee(id,first_name, last_name,middle_name, gender,phone,image_url, address_id,department_id) values
(-1,'Эльвира','Сорокина','Аркадьевна','female','7-495-840-92-36','http://randomuser.ru/images/women/med/70.jpg',-1,-1),
(-2,'Оксана','Муравьёва','Вячеславовна','female','7-495-511-19-59','http://randomuser.ru/images/women/med/21.jpg',-2,-1),
(-3,'Вероника','Жукова','Леонидовна','female','7-495-899-54-98','http://randomuser.ru/images/women/med/54.jpg',-3,-2),
(-4,'Ольга','Кондратьева','Лаврентьевна','female','7-495-667-38-82','http://randomuser.ru/images/women/med/83.jpg',-6,-2),
(-5,'Валерий','Морозов','Эдуардович','male','7-495-447-40-98','http://randomuser.ru/images/women/med/71.jpg',-4,-3),
(-6,'Олег','Борисов','Михайлович','male','7-495-402-24-28','http://randomuser.ru/images/women/med/23.jpg',-5,-3),
(-7,'Василий','Уваров','Игоревич','male','7-495-378-45-14','http://randomuser.ru/images/women/med/23.jpg',-9,-3),
(-8,'Лидия','Ильина','Ярославовна','female','7-495-575-14-21','http://randomuser.ru/images/women/med/61.jpg',-7,-4),
(-9,'Любовь','Трофимова','Эдуардовна','female','7-495-973-10-90','http://randomuser.ru/images/women/med/24.jpg',-8,-4),
(-10,'Кристина','Данилова','Романовна','female','7-495-342-34-83','http://randomuser.ru/images/women/med/30.jpg',-10,-5),
(-14,'Виктория','Владимирова','Дмитриевна','female','7-495-767-75-20','http://randomuser.ru/images/women/med/13.jpg',-11,-5),
(-11,'Светлана','Лазарева','Валентиновна','female','7-495-918-22-8','http://randomuser.ru/images/women/med/88.jpg',-12,-6),
(-12,'Любовь','Носова','Геннадьевна','female','7-495-675-87-50','http://randomuser.ru/images/women/med/78.jpg',-13,-6),
(-13,'Полина','Тарасова','Вячеславовна','female','7-495-329-35-74','http://randomuser.ru/images/women/med/17.jpg',-14,-3);

INSERT INTO public.managementtime(id,currentdate,enddate,startdate,worktime,user_id) values
(-1, '2020-04-24', '09:00:10', '18:10:31', '09:10:31', -2),
(-2, '2020-04-25', '09:10:10', '17:58:20', '08:48:10', -2),
(-3, '2020-04-26', '09:13:13', '18:01:55', '08:48:42', -2),
(-4, '2020-04-27', '08:58:59', '19:00:41', '10:01:42', -2),
(-5, '2020-04-28', '09:15:22', '18:22:00', '09:06:38', -2),
(-6, '2020-04-29', '09:30:01', '18:13:32', '08:43:31', -2),
(-7, '2020-04-30', '09:00:44', '18:09:12', '09:08:47', -2);