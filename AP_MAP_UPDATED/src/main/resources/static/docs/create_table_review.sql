create table review(
    mb_id varchar(50) CONSTRAINT review_mb_id_fk references member(mb_id),
    SIGUN_NM varchar(50),
    BIZPLC_NM varchar(100),
    rv_score int(5),
    rv_comment varchar(200)
);

commit;
    