CREATE TABLE "USER_TOKEN" 
   (	"DELEGATE_ID" NUMBER, 
	"TOKEN" VARCHAR2(50 BYTE), 
	"CREATE_DATE" TIMESTAMP (6), 
	"EXPIRY_DATE" TIMESTAMP (6)
   );
---------------------------------------   
   CREATE TABLE "ERROR_CODE" 
   (	"ID" NUMBER(19,0), 
"ACTIVE" NUMBER(1,0), 
"ERROR_CODE" VARCHAR2(255 CHAR), 
"ERROR_NAME_ARABIC" VARCHAR2(255 CHAR), 
"ERROR_NAME_ENGLISH" VARCHAR2(255 CHAR)
   );
   
ALTER TABLE ERROR_CODE ADD CONSTRAINT ID_UNIQUE UNIQUE (ID);   
ALTER TABLE ERROR_CODE ADD CONSTRAINT ERROR_CODE_UNIQUE UNIQUE (ERROR_CODE);  
------------------------------------
ALTER TABLE TM_DELEGATES ADD (DELEGATE_ID_TEMP NUMBER);
UPDATE TM_DELEGATES SET DELEGATE_ID_TEMP = DELEGATE_ID;
ALTER TABLE TM_DELEGATES DROP COLUMN DELEGATE_ID;
ALTER TABLE TM_DELEGATES RENAME COLUMN DELEGATE_ID_TEMP TO DELEGATE_ID;

-------------------------------------

ALTER TABLE TM_RECEIPTS ADD (CREATED_BY_TEMP NUMBER);
UPDATE TM_RECEIPTS SET CREATED_BY_TEMP = CREATED_BY;
ALTER TABLE TM_RECEIPTS DROP COLUMN CREATED_BY;
ALTER TABLE TM_RECEIPTS RENAME COLUMN CREATED_BY_TEMP TO CREATED_BY;

-------------------------------------

ALTER TABLE TM_RECEIPTS ADD (TM_DELEGATES_CODE_TEMP NUMBER);
UPDATE TM_RECEIPTS SET TM_DELEGATES_CODE_TEMP = TM_DELEGATES_CODE;
ALTER TABLE TM_RECEIPTS DROP COLUMN TM_DELEGATES_CODE;
ALTER TABLE TM_RECEIPTS RENAME COLUMN TM_DELEGATES_CODE_TEMP TO TM_DELEGATES_CODE;

------------------------------------------------------------

ALTER TABLE TM_RECEIPT_DETAILS ADD (CREATED_BY_TEMP NUMBER);
UPDATE TM_RECEIPT_DETAILS SET CREATED_BY_TEMP = CREATED_BY;
ALTER TABLE TM_RECEIPT_DETAILS DROP COLUMN CREATED_BY;
ALTER TABLE TM_RECEIPT_DETAILS RENAME COLUMN CREATED_BY_TEMP TO CREATED_BY;

---------------------------------------------------

ALTER TABLE TM_RECEIPT_PAYMENTS ADD (CREATEDBY_TEMP NUMBER);
UPDATE TM_RECEIPT_PAYMENTS SET CREATEDBY_TEMP = CREATEDBY;
ALTER TABLE TM_RECEIPT_PAYMENTS DROP COLUMN CREATEDBY;
ALTER TABLE TM_RECEIPT_PAYMENTS RENAME COLUMN CREATEDBY_TEMP TO CREATEDBY;

---------------------------------------------------

ALTER TABLE TM_CHARITY_BOX_ATTACHMENT ADD (CREATED_BY_TEMP NUMBER);
UPDATE TM_CHARITY_BOX_ATTACHMENT SET CREATED_BY_TEMP = CREATED_BY;
ALTER TABLE TM_CHARITY_BOX_ATTACHMENT DROP COLUMN CREATED_BY;
ALTER TABLE TM_CHARITY_BOX_ATTACHMENT RENAME COLUMN CREATED_BY_TEMP TO CREATED_BY;

-------------------------------------------------------

ALTER TABLE TM_CHARITY_BOXES ADD (CHARITY_BOX_TYPE_TEMP NUMBER);
UPDATE TM_CHARITY_BOXES SET CHARITY_BOX_TYPE_TEMP = CHARITY_BOX_TYPE;
ALTER TABLE TM_CHARITY_BOXES DROP COLUMN CHARITY_BOX_TYPE;
ALTER TABLE TM_CHARITY_BOXES RENAME COLUMN CHARITY_BOX_TYPE_TEMP TO CHARITY_BOX_TYPE;


ALTER TABLE TM_CHARITY_BOXES ADD (CATEGORY_OF_BOXES_TEMP VARCHAR2(10));
UPDATE TM_CHARITY_BOXES SET CATEGORY_OF_BOXES_TEMP = CATEGORY_OF_BOXES;
ALTER TABLE TM_CHARITY_BOXES DROP COLUMN CATEGORY_OF_BOXES;
ALTER TABLE TM_CHARITY_BOXES RENAME COLUMN CATEGORY_OF_BOXES_TEMP TO CATEGORY_OF_BOXES;
-----------------------------------
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL ADD (ACTION_TYPE_TEMP VARCHAR2(10));
UPDATE TM_CHARITY_BOX_TRANSFERS_DTL SET ACTION_TYPE_TEMP = ACTION_TYPE;
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL DROP COLUMN ACTION_TYPE;
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL RENAME COLUMN ACTION_TYPE_TEMP TO ACTION_TYPE;
-----------------------------------

ALTER TABLE TM_DELEGATES ADD (IS_CHARITY_BOX NUMBER(1) DEFAULT 0 NOT NULL);
ALTER TABLE TM_DELEGATES ADD (IS_COUPON NUMBER(1) DEFAULT 0 NOT NULL);

-------------------------
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL ADD (STATUS VARCHAR2(5));
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL ADD (SUPERVISOR_ID NUMBER);
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL ADD (TRANSACTION_NUMBER NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL);
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL ADD CONSTRAINT TRANSACTION_NUMBER_UNIQUE UNIQUE (TRANSACTION_NUMBER);

----------------------------
  CREATE TABLE "TM_DELEGATE_ROUTE" 
   (	"ROUTE_ID" NUMBER, 
	"ROUTE_DATE" DATE, 
	"ROUTE_NUMBER" VARCHAR2(50 BYTE), 
	"DELEGATE_ID" NUMBER, 
	"STATUS" NUMBER, 
	"CREATED_BY" NUMBER, 
	"CREATION_DATE" DATE, 
	"LAST_UPDATE_BY" NUMBER, 
	"LAST_UPDATED_DATE" DATE, 
	"ORG_ID" NUMBER
   );
   
  CREATE TABLE "TM_DELEGATE_ROUTE_DTL" 
   (	"ROUTE_LINE_ID" NUMBER, 
	"ROUTE_ID" NUMBER, 
	"SUB_LOCATION_ID" NUMBER, 
	"STATUS" NUMBER, 
	"CREATED_BY" NUMBER, 
	"CREATION_DATE" DATE, 
	"LAST_UPDATE_BY" NUMBER, 
	"LAST_UPDATED_DATE" DATE, 
	"ORG_ID" NUMBER, 
	"CHECKED" NUMBER
   ); 
----------------------------

CREATE SEQUENCE CH_BOX_TRANS_SEQ INCREMENT BY 1 START WITH 4624 MAXVALUE 99999999999999999999999999999999999 MINVALUE 1 NOCACHE;
CREATE SEQUENCE CH_BOX_TRANS_DTL_SEQ INCREMENT BY 1 START WITH 7141 MAXVALUE 99999999999999999999999999999999999 MINVALUE 1 NOCACHE;
CREATE SEQUENCE SUB_LOCATION_SEQ INCREMENT BY 1 START WITH 7023 MAXVALUE 99999999999999999999999999999999999 MINVALUE 1 NOCACHE;
ALTER TABLE TM_SUB_LOCATIONS ADD CONSTRAINT SUB_LOCATION_ID_UNIQUE UNIQUE (SUB_LOCATION_ID);

CREATE SEQUENCE LOCATION_SEQ INCREMENT BY 1 START WITH 4501 MAXVALUE 99999999999999999999999999999999999 MINVALUE 1 NOCACHE;
ALTER TABLE TM_LOCATIONS ADD CONSTRAINT LOCATION_ID_UNIQUE UNIQUE (LOCATION_ID);

----------------------------
ALTER TABLE SP_CASES ADD (CHECK_FLAG NUMBER(1,0));
----------------------------
CREATE TABLE "TM_DELEGATE_COUPONS" 
   (       
    "ID"  NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL,
	"DELEGATE_ID" NUMBER,
    "COUPON_ID" NUMBER,
	"CREATION_DATE" DATE	
   );
----------------------------
ALTER TABLE TM_RECEIPT_DETAILS ADD (TRANSACTION_TYPE VARCHAR2(50));
ALTER TABLE TM_RECEIPT_DETAILS ADD (RECEIPT_DETAIL_NAME VARCHAR2(500));
ALTER TABLE TM_RECEIPT_DETAILS ADD (PAYMENT_TYPE VARCHAR2(50));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_NAME VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_NAME VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_MOBILE VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_PHONE VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_POBOX VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_EMAIL VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_COUNTRY_ID VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_ID_SPONSORSHIP NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (CASE_ID NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (CASE_AMOUNT NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (GIFT_AMOUNT NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (GIFT_TYPE_CODE VARCHAR2(100));
ALTER TABLE TM_RECEIPT_DETAILS ADD (CASE_PAYMENT_TYPE VARCHAR2(100));
ALTER TABLE TM_RECEIPT_DETAILS ADD (SPONSOR_FOR VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (FIRST_TITLE VARCHAR2(255));
ALTER TABLE TM_RECEIPT_DETAILS ADD (SPONSORSHIP_START_DATE DATE);
ALTER TABLE TM_RECEIPT_DETAILS ADD (CASE_AMOUNT_PER_MONTH NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (CASE_PAYMENT_MONTHS NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (RECEIPT_TYPE_ID NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (PROJECT_COMMITMENT NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (DONATOR_ID_PROJECT NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (PROJECT_STUDY_ID NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD (PROJECT_COUNTRY_ID varchar2(100));
ALTER TABLE TM_RECEIPT_DETAILS ADD (PROJECT_NAME varchar2(250));
ALTER TABLE TM_RECEIPT_DETAILS ADD (SC_PROJECT_ID NUMBER);
----------------------
ALTER TABLE TM_LOCATIONS ADD (LOCATION_LATITUDE FLOAT);
ALTER TABLE TM_LOCATIONS ADD (LOCATION_LONGITUDE FLOAT);
ALTER TABLE TM_SUB_LOCATIONS ADD (LOCATION_LATITUDE FLOAT);
ALTER TABLE TM_SUB_LOCATIONS ADD (LOCATION_LONGITUDE FLOAT);
----------------------
ALTER TABLE TM_RECEIPTS ADD CONSTRAINT RECEIPT_NUMBER_UNIQUE UNIQUE (RECEIPT_NUMBER);
ALTER TABLE TM_RECEIPT_DETAILS ADD CONSTRAINT RECEIPT_DETAIL_CODE_UNIQUE UNIQUE (RECEIPT_DETAIL_CODE);
----------------------
CREATE SEQUENCE ATTACHMENT_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 99999999999999999999999999999999999 MINVALUE 1 NOCACHE;
----------------------
ALTER TABLE TM_PROJECTS ADD (QR_CODE VARCHAR2(100));
ALTER TABLE TM_RECEIPT_DETAILS ADD (IS_TAWASUL_APP NUMBER(1,0) DEFAULT 0);
ALTER TABLE TM_RECEIPTS ADD (IS_TAWASUL_APP NUMBER(1,0) DEFAULT 0);

ALTER TABLE TM_CHARITY_BOX_TRANSFERS ADD (IS_TAWASUL_APP NUMBER(1,0) DEFAULT 0);
ALTER TABLE TM_CHARITY_BOX_TRANSFERS_DTL ADD (IS_TAWASUL_APP NUMBER(1,0) DEFAULT 0);