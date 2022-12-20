create user c;##apdb identified by ap1111;

grant connect to c;
grant resource to c;
grant dba to c;

commit;
