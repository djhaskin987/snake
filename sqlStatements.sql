-------------------------
-- SQL DROP STATEMENTS --
-------------------------

DROP TABLE "main"."Item"


DROP TABLE "main"."Product"


drop table "main"."Model"


DROP TABLE "main"."ProductContainer"


DROP TABLE "main"."ProductContainerProductRelation"


DROP TABLE "main"."UnitEnum"


---------------------------
-- SQL CREATE STATEMENTS --
---------------------------

CREATE TABLE "Item" (
     "ProductID" INTEGER NOT NULL
     , "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE
     , "EntryDate" DATETIME NOT NULL
     , "ExitTime" DATETIME
     , "ProductContainerID" INTEGER
)

CREATE TABLE "Product" (
       "CreationDate" DateTime NOT NULL
       , "Barcode" Text PRIMARY KEY  NOT NULL  UNIQUE
       , "Description" Text NOT NULL
       , "SizeValue" DOUBLE NOT NULL
       , "SizeUnit" INTEGER NOT NULL
       , "ShelfLife" INTEGER NOT NULL
       , "3MonthSupply" Integer NOT NULL 
)

create table "Model" (
       "name" varchar(50) primary key not null unique
       , "value" varchar(50) null
}

CREATE TABLE "ProductContainer" (
       "Name" TEXT NOT NULL 
       , "ProductContainerID" INTEGER 
       , "3MonthSupplyValue" DOUBLE NOT NULL
       , "3MonthSupplyUnit" INTEGER
       , "ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE
)

CREATE TABLE "ProductContainerProductRelation" (
     "ProductContainerID" INTEGER NOT NULL
     , "ProductID" INTEGER NOT NULL 
)

CREATE TABLE "UnitEnum" (
       "ID" INTEGER PRIMARY KEY  NOT NULL  UNIQUE
       , "Name" TEXT NOT NULL  UNIQUE
)
