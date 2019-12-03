use clinic;

-- creating Users
insert into users (`username`, `password`, `role`) values ('admin', 'admin', 'admin');
insert into users (`username`, `password`, `role`) values ('firstDoc', 'firstDoc', 'doctor');
insert into users (`username`, `password`, `role`) values ('firstUser', 'firstUser', 'user');
insert into users (`username`, `password`, `role`) values ('secondUser', 'secondUser', 'user');

-- pushing UserInfo
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (1, 'Artemqa', 'Arti', 'Tsv', 'artem.tsvirko@mail.ru', '+375 (33) 337-33-33', '2019-07-06', 'Trump st, 65', 'Bad vision');
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (2, 'John', 'Egghead', 'Sturgis', 'j.sturgis@gmail.com', '+1-444-934-5309', '2019-11-29', 'Klinton st, 69', 'Time-to-time crazy');
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (3, 'Test1', 'Nothing', 'Test1', 'test.test@gmail.com', '+1-259-444-4309', '2019-10-1', 'Obama st, 65', 'Pooping issues');
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (4, 'Art', 'A.', 'Tsvirko', 'tsvirko.artem@gmail.com', '+1-444-934-4444', '2019-07-06', 'Bush st, 27', 'Bad vision');

-- pushing StaffEntity info
insert into staff_entity (`id`, `salary`) values (2, 100000);

-- pushing Doctors info
insert into Doctors (`id`, `degree`, `specialization`) values (2, 'MIT Banchelor in surgery', 'Surgery');

-- pushing Appointments
insert into Appointments (`doctor_id`, `patient_id`, `date`, `time`, `comment`) values (2, 3 , '2019-12-05', '15:00:00', 'Appendix');
insert into Appointments (`doctor_id`, `patient_id`, `date`, `time`, `comment`) values (2, 4 , '2019-12-07', '8:00:00', 'Leg needs to be fixed');

-- pushing Events 
insert into `events` (`header`, `content`, `start_date`, `end_date`, `only_for_personal`) values ('Donor day', 'Come and give us your blood and take money for that', '2019-12-07', '2019-12-10', 0);

