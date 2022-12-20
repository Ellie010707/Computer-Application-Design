create table review (
    num int primary key AUTO_INCREMENT,
	mb_id varchar(50) references member,
    REFINE_ROADNM_ADDR varchar(500),
    rv_score int(38),
    rv_comment varchar(1000)
);

CREATE INDEX emp_ix01 ON review(num);

commit;
    