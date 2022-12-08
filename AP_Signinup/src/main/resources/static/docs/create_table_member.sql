create table member (
    mb_id varchar2(50)
        constraint mb_mb_id_pk primary key,
    mb_pw varchar2(100)
     constraint mb_mb_pw_nn not null,
    mb_name varchar2(50)
        constraint mb_mb_name_nn not null,
    mb_email varchar2(100)
    constraint mb_mb_emial_nn not null
);

commit;