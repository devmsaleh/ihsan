
  CREATE TABLE "CAPPS"."TM_RECEIPT_DETAILS" 
   (	"RECEIPT_DETAIL_CODE" NUMBER(19,0) NOT NULL ENABLE, 
	"CREATION_DATE" DATE, 
	"CREATED_BY" NUMBER, 
	"LAST_UPDATE_DATE" DATE, 
	"LAST_UPDATE_BY" NUMBER, 
	"RECEIPT_CODE" NUMBER(19,0), 
	"PROJECT_CODE" NUMBER(19,0) NOT NULL ENABLE, 
	"CLASS_CODE" VARCHAR2(10 BYTE), 
	"AMOUNT" NUMBER(*,0) NOT NULL ENABLE, 
	"NOTES" VARCHAR2(1000 BYTE), 
	"SLIP_SADKAT" VARCHAR2(100 BYTE), 
	"SLIP_ZAKAT" VARCHAR2(100 BYTE), 
	"CREATEDBY" NUMBER(19,0), 
	"LASTMODIFIEDBY" NUMBER(19,0), 
	"CREATEDON" DATE, 
	"LASTMODIFIEDON" DATE, 
	"LOCATIONCODE" NUMBER(2,0), 
	"SERIALNO" NUMBER(13,0), 
	"COMPANYCODE" NUMBER(19,0), 
	"TRANSACTION_TYPE" VARCHAR2(50 BYTE), 
	"RECEIPT_DETAIL_NAME" VARCHAR2(500 BYTE), 
	"PAYMENT_TYPE" VARCHAR2(50 BYTE), 
	"DONATOR_NAME" VARCHAR2(255 BYTE), 
	"DONATOR_MOBILE" VARCHAR2(255 BYTE), 
	"DONATOR_PHONE" VARCHAR2(255 BYTE), 
	"DONATOR_POBOX" VARCHAR2(255 BYTE), 
	"DONATOR_EMAIL" VARCHAR2(255 BYTE), 
	"DONATOR_COUNTRY_ID" VARCHAR2(255 BYTE), 
	"DONATOR_ID_SPONSORSHIP" NUMBER, 
	"CASE_ID" NUMBER, 
	"CASE_AMOUNT" NUMBER, 
	"GIFT_AMOUNT" NUMBER, 
	"GIFT_TYPE_CODE" VARCHAR2(100 BYTE), 
	"CASE_PAYMENT_TYPE" VARCHAR2(100 BYTE), 
	"SPONSOR_FOR" VARCHAR2(255 BYTE), 
	"FIRST_TITLE" VARCHAR2(255 BYTE), 
	"SPONSORSHIP_START_DATE" DATE, 
	"CASE_AMOUNT_PER_MONTH" NUMBER, 
	"CASE_PAYMENT_MONTHS" NUMBER, 
	"RECEIPT_TYPE_ID" NUMBER, 
	"PROJECT_COMMITMENT" NUMBER, 
	"DONATOR_ID_PROJECT" NUMBER, 
	"PROJECT_STUDY_ID" NUMBER, 
	"PROJECT_COUNTRY_ID" VARCHAR2(100 BYTE), 
	"PROJECT_NAME" VARCHAR2(250 BYTE), 
	"SC_PROJECT_ID" NUMBER, 
	 CONSTRAINT "TM_RECEIPT_FK" FOREIGN KEY ("RECEIPT_CODE")
	  REFERENCES "CAPPS"."TM_RECEIPTS" ("RECEIPT_CODE") ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

  CREATE INDEX "CAPPS"."TM_RECEP_CODE_INDX" ON "CAPPS"."TM_RECEIPT_DETAILS" ("RECEIPT_CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;