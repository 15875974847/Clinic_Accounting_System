# Clinic Accounting System

Clinic Accounting System tech stack:
* Java 8.
* Spring Boot 2, JPA.
* MySQL 8.
* JSP, JSTL, EL.
* HTML, JS, Bootstrap, Datatables, Date picker.
* JavaMail.

## Functionality overview:

* Sign In.
* Registration.
* Password recovery by email.
* Administrator
  * Can view/sort/search/add/delete `common` and `only for personal` events.
  * Can view/sort/search/add/delete doctors.
  * Can view/sort/search/edit/delete patients.
  * Can view/sort/search appointments and delete all old non-closed appointments by date.
* Doctor
  * Can view/sort/search/add `common` and `only for personal` events.
  * Can view/edit his own credentials and personal info.
  * Can view/sort/search doctors and make an appointment to other doctor.
  * Can view/sort/search patients and make edits in patient's medical history.
  * Can view/sort/search `him as a doctor` appointments and close them with a note in medical history.
  * Can view/sort/search `him as a patient` appointments.
* Patient
  * Can view/sort/search `common` events.
  * Can view/edit his own credentials and personal info.
  * Can view/sort/search doctors and make appointments to them.
  * Can view/sort/search his own appointments.

## Screenshots

### Sign In
![](src/test/results/screenshots/common/sign_in.jpg)


### Registration

#### Username and password
![](src/test/results/screenshots/common/registration_u_p.jpg)

#### Personal info application
![](src/test/results/screenshots/common/registration-pers_info_application.jpg)


### Password recovery by email
![](src/test/results/screenshots/common/pass_recovery-enter_username.jpg)


### Patient

#### Managing account info
![](src/test/results/screenshots/patient/patient-edit_personal_info.jpg)
![](src/test/results/screenshots/patient/patient-edit_credentials.jpg)
![](src/test/results/screenshots/patient/patient-edit_credentials_submission_result.jpg)

#### Making appointments
![](src/test/results/screenshots/patient/patient-make_app_form.jpg)
![](src/test/results/screenshots/patient/patient-make_app_result.jpg)


### Doctor

#### Home Page
![](src/test/results/screenshots/doctor/doctor-home_page.jpg)

#### Creating new events
![](src/test/results/screenshots/doctor/doctor-add_new_event_form.jpg)

#### Personal information page

##### As patient
![](src/test/results/screenshots/doctor/doctor-personal_information_as_a_patient.jpg)
##### As doc
![](src/test/results/screenshots/doctor/doctor-personal_information_as_a_doctor.jpg)
##### Changing personal info
![](src/test/results/screenshots/doctor/doctor-personal_information_form.jpg)
![](src/test/results/screenshots/doctor/doctor-personal_information_form_submission_result.jpg)

#### Managing patients
![](src/test/results/screenshots/doctor/doctor-find_patient_medicalHistory_changing_form.jpg)
![](src/test/results/screenshots/doctor/doctor-find_patient_medicalHistory_changing_form_result.jpg)
#### Managing appointments
![](src/test/results/screenshots/doctor/doctor-my_appointments_closing_appointment_form_submission_result.jpg)


### Admin

#### Home
![](src/test/results/screenshots/admin/admin-home.jpg)

#### Managing events
![](src/test/results/screenshots/admin/admin-create_event_form.jpg)
![](src/test/results/screenshots/admin/admin-delete_event.jpg)

#### Managing doctors
![](src/test/results/screenshots/admin/admin-add_doctor_form.jpg)
![](src/test/results/screenshots/admin/admin-add_doctor_form_2.jpg)
![](src/test/results/screenshots/admin/admin-delete_doctor.jpg)

#### Managing patients
![](src/test/results/screenshots/admin/admin-edit_patients_info.jpg)
![](src/test/results/screenshots/admin/admin-delete_patient.jpg)

#### Managing appointments
![](src/test/results/screenshots/admin/admin-apps.jpg)
![](src/test/results/screenshots/admin/admin-delete_apps_before_date.jpg)
