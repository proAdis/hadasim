
create database person;
use person;
--פתיחת הטבלה החדשה וממלוי הטבלה כדי שתתאים לשאלות בהמשך 
create table person(
    Person_Id BIGINT PRIMARY KEY,
	Personal_Name nvarchar(100),
	Family_Name nvarchar(100),
	Gendere nvarchar(100),
	Father_Id BIGINT ,
	Mother_Id bigInt,
	spouse_Id bigint);

	insert into person( Person_Id,Personal_Name,Family_Name,Gendere,Father_Id,Mother_Id,spouse_Id)
	values(3261637836, 'David','Jhonson','male',331177889,22116688,null),
    (3061657136, 'Cloe','Seri','female',33344389,03232688,3261637836),

	(3261637823, 'Hannah','Jhonson','female',331177889,22116688,0261637836),
	(0261637836, 'Dvir','Choen','male',041177889,02116688,3261637823),

	(0458483923, 'Justin','Wilson','male',01377889,29416688,null),
	(0832423423, 'Meron','Davis','male',03034945,3948582038,null),

    (0489383742, 'Mile','Moore','male',02849484,04485739,null),
	(0248455930, 'Tamar','Mark','female',029398573,093847573,0489383742),

	(0243465434, 'christina','Mark','female',029398573,093847573,null),

	(0848475627, 'Thor','Brown','male',029398573,093847573,0243465434),
		(0248475930, 'Taylor','Mark','female',045398573,093227573,0848475627),

	(331177889, 'Michael', 'Jhonson', 'male', null, null, 22116688),--להורים לא הוספתי הורים כי זה לא יגמר
	(22116688, 'Linda', 'Jhonson', 'female', null, null, 331177889),
	(33344389, 'William', 'Seri', 'male', null, null, 03232688),
	(03232688, 'Karen', 'Seri', 'female', null, null, 33344389),
	(041177889, 'Robert', 'Choen', 'male', null, null, 02116688),
	(02116688, 'Nancy', 'Choen', 'female', null, null, 041177889),
	(01377889, 'Charles', 'Wilson', 'male', null, null, 29416688),
	(29416688, 'Deborah', 'Wilson', 'female', null, null, 01377889),
	(03034945, 'Steven', 'Davis', 'male', null, null, 3948582038),
	(3948582038, 'Barbara', 'Davis', 'female', null, null, 03034945),
	(02849484, 'Thomas', 'Moore', 'male', null, null, 04485739),
	(04485739, 'Patricia', 'Moore', 'female', null, null, 02849484),
	(029398573, 'George', 'Mark', 'male', null, null, 093847573),
	(093847573, 'Jennifer', 'Mark', 'female', null, null, 029398573),
	(045398573, 'James', 'Mark', 'male', null, null, 093227573),
	(093227573, 'Susan', 'Mark', 'female', null, null, 045398573);
	
	--לפי תרגיל 2 בדיקה והוספת בן זוג בעת הצורך כשלא התווסף היה נראה הגיוני לשים פה
 update p1
set p1.spouse_Id=p2.person_ID
from person p1
join person p2
 ON p2.spouse_Id = p1.Person_Id
WHERE p1.spouse_Id IS NULL;



--תרגיל 1
	--יצירת טבלת עץ משפחה
	create table family_tree (
    Person_Id bigint,
    Relative_Id bigint,
    Connection_Type nvarchar(20)
);

--אבא
	insert into family_tree (Person_Id, Relative_Id, Connection_Type)
select Person_Id, Father_Id, N'Father' from person where Father_Id is not null

--אמא
	insert into family_tree (Person_Id, Relative_Id, Connection_Type)
select Person_Id, Mother_Id , N'Mother' from person where Mother_Id is not null

--בן או בת
insert into family_tree (Person_Id, Relative_Id, Connection_Type)
select Father_Id, Person_Id, 
case when (Gendere = 'male') then N'son' else N'daughter' end
from person
where Father_Id is not null

--אח או אחות
insert into family_tree (Person_Id, Relative_Id, Connection_Type)
select p1.person_id,p2.person_id,
    case when p2.Gendere = 'male' then N'brother' else N'sister' end
from person p1
join person p2
on p1.person_id<>p2.person_id  -- שלא יעשה צירוף עם 
and (
     (p1.Father_Id is not null and p2.Father_Id is not null and p1.Father_Id = p2.Father_Id) or
     (p1.Mother_Id is not null and p2.Mother_Id is not null and p1.Mother_Id = p2.Mother_Id)
 );



--הדפסת טבלאות מיון טבלאת עץ משפחה לפי אדם
select * 
from person


SELECT * 
FROM family_tree
ORDER BY Person_Id ;

