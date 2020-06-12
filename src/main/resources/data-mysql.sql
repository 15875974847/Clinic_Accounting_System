use clinic;

-- creating Users
insert into users (`username`, `password`, `role`) values ('admin', 'admin', 'admin');
insert into users (`username`, `password`, `role`) values ('firstDoc', 'firstDoc', 'doctor');
insert into users (`username`, `password`, `role`) values ('secondDoc', 'secondDoc', 'doctor');
insert into users (`username`, `password`, `role`) values ('thirdDoc', 'thirdDoc', 'doctor');
insert into users (`username`, `password`, `role`) values ('firstUser', 'firstUser', 'patient');
insert into users (`username`, `password`, `role`) values ('secondUser', 'secondUser', 'patient');

-- pushing UserInfo
-- admins
-- I decided to deprive admins of the right to have his own profile and manage it, i.e. my admin will not be the essence of the clinic
-- insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (1, 'Artemqa', 'Arti', 'Tsv', 'artem.tsvirko@mail.ru', '+375 (33) 337-33-33', '2000-07-06', 'Trump st, 65', 'Bad vision');
-- docs
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (2, 'John', 'Egghead', 'Sturgis', 'j.sturgis@gmail.com', '+1-444-555-5309', '1999-11-29', 'Klinton st, 69', 'Time-to-time crazy');
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (3, 'Sheldon', 'Lee', 'Cooper', 'artem.tsvirko@mail.ru', '+1-444-666-5309', '1999-11-29', 'Klinton st, 69', 'Dangerosly funny');
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (4, 'Leonard', '-', 'Hoffstader', 'leo.hoff@gmail.com', '+1-444-777-5309', '1985-11-29', 'Klinton st, 69', 'Lactate smth');
-- patients
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (5, 'Jimmy', 'Sol', 'McGill', 'artem.tsvirko@mail.ru', '+1-259-888-4309', '2019-10-1', 'Obama st, 65', 'Pooping issues');
insert into user_info (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone`, `dob`, `address`, `medical_history`) values (6, 'Arti', 'A.', 'Tsvirko', 'tsvirko.artem@gmail.com', '+1-444-999-4444', '2019-07-06', 'Bush st, 27', 'Bad vision');

-- pushing StaffEntity info
insert into staff_entity (`id`, `salary`) values (2, 100000);
insert into staff_entity (`id`, `salary`) values (3, 120000);
insert into staff_entity (`id`, `salary`) values (4, 90000);

-- pushing Doctors info
insert into doctors (`id`, `degree`, `specialization`) values (2, 'MIT Banchelor in surgery', 'Surgery');
insert into doctors (`id`, `degree`, `specialization`) values (3, 'CalTech', 'Common Doctor');
insert into doctors (`id`, `degree`, `specialization`) values (4, 'CalTech', 'Venerology');

-- pushing Appointments
insert into appointments (`doctor_id`, `patient_id`, `date`, `number_in_queue`, `comment`) values (2, 5, '2019-12-12', '1', 'Appendix');
insert into appointments (`doctor_id`, `patient_id`, `date`, `number_in_queue`, `comment`) values (2, 6, '2019-12-12', '2', 'Leg needs to be fixed');
insert into appointments (`doctor_id`, `patient_id`, `date`, `number_in_queue`, `comment`) values (2, 3, '2019-12-13', '1', 'Consultation');

-- pushing Events 
insert into `events` (`header`, `content`, `start_date`, `end_date`, `only_for_personal`) values ('Donor day', 'Come and give us your blood and take money for that', '2019-12-07', '2019-12-10', 0);
insert into `events` (`header`, `content`, `start_date`, `end_date`, `only_for_personal`) values ('Personal meeting', 'Solving problems with salaries', '2019-12-12', '2019-12-12', 1);
insert into `events` (`header`, `content`, `start_date`, `end_date`, `only_for_personal`) values ('Hygiene lecture', 'Come in class 219 to learn new about importance of hygine in your life. BLA. BLA.', '2019-12-10', '2019-12-12', 0);
