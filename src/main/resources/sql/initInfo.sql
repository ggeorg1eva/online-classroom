USE classroom;
INSERT INTO subjects (id, `name`)
VALUES (1,'Math'),
       (2, 'Bulgarian language'),
       (3, 'English language'),
       (4, 'History language'),
       (5, 'Geography'),
       (6, 'Physics');

INSERT INTO classes (id, `name`)
VALUES (1,'Twelve_A'),
       (2, 'Twelve_B'),
       (3, 'Twelve_C');

INSERT INTO classes_subjects(school_class_id, subjects_id)
VALUES (1,1),
       (1,2),(1,3),(1,4),(1,5),(1,6),
       (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
       (3,1),(3,2),(3,3),(3,4),(3,5),(3,6);

INSERT INTO teachers(id, age, egn, first_name, last_name, subject_id)
VALUES (1, 25, '9712301122', 'Milka', 'Milkova', 1),
       (2, 35, '8712301122', 'Ivan', 'Ivanov', 2),
       (3, 45, '7712301122', 'Pesho', 'Petrov', 3),
       (4, 55, '6712301122', 'Todor', 'Todorov', 4),
       (5, 36, '6612301122', 'Petka', 'Todorova', 5),
       (6, 46, '7612301122', 'Ivanka', 'Ivanova', 6),
       (7, 48, '7412301122', 'Kichka', 'Bodurova', 2);

INSERT INTO teachers_classes (classes_id, teacher_id)
VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
       (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
       (3,1),(3,7),(3,3),(3,4),(3,5),(3,6);

INSERT INTO students (id, age, egn, first_name, last_name, school_class_id)
VALUES (1, 18, '0441123456','Angel', 'Ivanov', 1),
       (9, 18, '0442123456','Ivan', 'Petkanovich', 1),
       (2, 18, '0443123456','Bozhur', 'Bozhurov', 1),
       (3, 18, '0444123456','Penka', 'Penkova', 2),
       (4, 18, '0445123456','Kichka', 'Miteva', 2),
       (5, 18, '0446123456','Gospodin', 'Gospodinov', 2),
       (6, 18, '0447123456','Totka', 'Ivanova', 3),
       (7, 18, '0448123456','Ivanka', 'Gosheva', 3),
       (8, 18, '0449123456','Petar', 'Petrov', 3);