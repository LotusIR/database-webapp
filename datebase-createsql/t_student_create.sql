CREATE table t_student (
	student_id INT PRIMARY KEY,
	student_name VARCHAR(8),
	student_sex VARCHAR(2),
	student_age INT CHECK(student_age > 15 and student_age < 30),
	student_dept VARCHAR(10),
	student_username VARCHAR(20) UNIQUE,
	student_password VARCHAR(20)
)

