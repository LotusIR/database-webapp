CREATE TABLE t_student_course(
	student_id INT,
	course_id INT,
	grade INT,
	CONSTRAINT t_student_course_primary PRIMARY KEY (student_id,course_id),
	CONSTRAINT ref_t_student FOREIGN KEY(student_id) REFERENCES t_student(student_id),
	CONSTRAINT ref_t_course FOREIGN KEY(course_id) REFERENCES t_course(course_id)
)