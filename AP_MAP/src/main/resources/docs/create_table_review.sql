create table review(
    mb_id varchar2(50)
        constraint review_mb_id_fk references member(mb_id),
    SIGUN_NM varchar2(50),
    BIZPLC_NM varchar2(100),
    rv_score number(5),
    rv_comment varchar(200)
)

commit;
    