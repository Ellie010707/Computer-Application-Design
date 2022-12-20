create table member (
                        mb_id varchar(50) primary key,
                        mb_pw varchar(100),
                        mb_name varchar(50),
                        mb_email varchar(100)
) charset = utf8;

create table review (
                        num int primary key AUTO_INCREMENT,
                        mb_id varchar(50) references member,
                        bizplc_nm varchar(50),
                        REFINE_ROADNM_ADDR varchar(500),
                        rv_score int(38),
                        rv_comment varchar(1000)
) charset = utf8;

create table csv (
    bizplc_nm varchar(100),
    refine_roadnm_addr varchar(500)
) charset = utf8;

LOAD DATA LOCAL INFILE 'docker-entrypoint-initdb.d/Animal_Pharmacy.csv'
INTO TABLE csv
CHARACTER SET utf8
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(@a, @bizplc, @b, @c, @d, @refineroad, @e,@f,@g)
SET
bizplc_nm = @bizplc,
REFINE_ROADNM_ADDR = @refineroad;
