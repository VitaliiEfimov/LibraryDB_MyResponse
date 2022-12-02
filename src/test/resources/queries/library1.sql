select *
from book_borrow;

# 01
select *
from users;
# 02
select count(*) as borrowedBooks
from users u
         inner join book_borrow b on u.id = b.user_id
where is_returned = 0;
# 03
select name from book_categories;
# 04
select name, author,year from books where name='Chordeiles minor';
# 05
select bc.name, count(*)
from book_borrow bb
         inner join books b on bb.book_id = b.id
         inner join book_categories bc on b.book_category_id = bc.id
group by name
order by 2 desc;
# 06
select id,name,author from books
where name = 'Clean Code' and author='Robert C.Martin'
order by id desc;
select id,name,author from books
where name = 'Clean Code'
order by id desc;

# 07
select full_name,b.name,bb.borrowed_date from users u
                                                  inner join book_borrow bb on u.id = bb.user_id
                                                  inner join books b on bb.book_id = b.id
where full_name='Test Student 1'
order by 3 desc;

