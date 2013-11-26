-----------------------
-- TEAM 2	     --
-- DANIEL CARRIER    --
-- DANIEL HASKIN     --
-- NATHAN STANDIFORD --
-- 2013-11-26	     --
-----------------------

-------------------------
-- SQL DROP STATEMENTS --
-------------------------

DROP TABLE "main"."Item"

DROP TABLE "main"."ProductContainerProductRelation"

DROP TABLE "main"."Product"

DROP TABLE "main"."ProductContainer"

drop table "main"."Model"

DROP TABLE "main"."UnitEnum"


---------------------------
-- SQL CREATE STATEMENTS --
---------------------------

CREATE TABLE "Product" (
       "CreationDate" DateTime NOT NULL
       , "Barcode" Text PRIMARY KEY  NOT NULL  UNIQUE
       , "Description" Text NOT NULL
       , "SizeValue" DOUBLE NOT NULL
       , "SizeUnit" INTEGER NOT NULL
       , "ShelfLife" INTEGER NOT NULL
       , "3MonthSupply" Integer NOT NULL 
)

CREATE TABLE "ProductContainer" (
       "Name" TEXT NOT NULL 
       , "ParentContainerID" INTEGER references "ProductContainer"("ID")
       , "3MonthSupplyValue" DOUBLE NOT NULL
       , "3MonthSupplyUnit" INTEGER
       , "ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE
)

CREATE TABLE "Item" (
     "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE 
     , "EntryDate" DATETIME NOT NULL
     , "ExitTime" DATETIME
     , "ProductBarcode" text NOT NULL  references "Product"("Barcode")
     , "ProductContainerID" INTEGER references "ProductContainer"("ID")
)


create table "Model" (
       "name" varchar(50) primary key not null unique
       , "value" varchar(50) null
)


CREATE TABLE "ProductContainerProductRelation" (
     "ProductContainerID" INTEGER NOT NULL references "ProductContainer"("ID") on update cascade
     , "ProductBarcode" Text NOT NULL references "Product"("Barcode") on update cascade
)

CREATE TABLE "UnitEnum" (
       "ID" INTEGER PRIMARY KEY  NOT NULL  UNIQUE
       , "Name" TEXT NOT NULL  UNIQUE
)
