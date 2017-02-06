--create user
create user opname
    identified by opname
    
--grant
GRANT create session TO opname;
GRANT create table TO opname;
GRANT create view TO opname;
GRANT create any trigger TO opname;
GRANT create any procedure TO opname;
GRANT create sequence TO opname;
GRANT create synonym TO opname;

--grant tablespace
ALTER USER opname quota 100M on USERS;

--tables in schema opname
create table MsProduct(
    productid int,
    productname varchar2(100),
    description varchar2(100),
    sellprice int,
    constraint pk_msproduct primary key (productid)
)

insert all
    into MsProduct values(1,'Cheetos','Snack',3000)
    into MsProduct values(2,'Coca Cola','Drink',4000)
    into MsProduct values(3,'Yupi','Snack',2000)
select * from dual;

create table MsStock(
    stockid int,
    productid int,
    buyprice int,
    stock int,
    datepurchased timestamp,
    constraint pk_msstock primary key (stockid),
    constraint fk_msstock foreign key (productid) references MsProduct(productid)    
)

insert all
    into MsStock values(1,1,2500,20,sysdate)
    into MsStock values(2,2,3500,20, sysdate)
    into MsStock values(3,3,1250,5, sysdate-1)
    into MsStock values(4,3,1500,20, sysdate)
select * from dual;


create table AudittrailStock(
    stockid int,
    stockbefore int,
    stockafter int,
    datechanged timestamp,
    constraint fk_auditstock foreign key (stockid) references MsStock(stockid)
)

create table TransactionHeader(
    transactionid int,
    transactiondate timestamp,
    constraint pk_transheader primary key (transactionid)
)
    --contoh transaksi
insert all
    into transactionheader values (1,sysdate-1)
    into transactionheader values (2,sysdate)
select * from dual

create table TransactionDetail(
    transactionid int,
    productid int,
    quantity int,
    sellprice int,
    constraint pk_transdetail primary key (transactionid, productid),
    constraint fk_trans_prod foreign key (productid) references MsProduct(productid),
    constraint fk_trans_id foreign key (transactionid) references TransactionHeader(transactionid)
)

insert all
    into transactiondetail values(1,1,2,2900)
    into transactiondetail values(2,1,2,3000)
    into transactiondetail values(2,2,2,4000)
select * from dual

create table CartHeader(
    cartid int,
    sessionno varchar2(200),
    constraint pk_cart primary key (cartid)
)

create table CartDetail(
    cartid int,
    productid int,
    quantity int,
    constraint fk_cart foreign key (cartid) references CartHeader(cartid)
)
