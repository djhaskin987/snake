-------------------------
-- SQL DROP STATEMENTS --
-------------------------

DROP TABLE IF EXISTS "main"."Item";


DROP TABLE IF EXISTS "main"."Product";


DROP TABLE IF EXISTS "main"."Model";


DROP TABLE IF EXISTS "main"."ProductContainer";


DROP TABLE IF EXISTS "main"."ProductContainerProductRelation";


DROP TABLE IF EXISTS "main"."UnitEnum";


---------------------------
-- SQL CREATE STATEMENTS --
---------------------------

CREATE TABLE "Item" (
     "ProductID" INTEGER NOT NULL
     , "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE
     , "EntryDate" DATETIME NOT NULL
     , "ExitTime" DATETIME
     , "ProductContainerID" INTEGER
);

CREATE TABLE "Product" (
       "CreationDate" DateTime NOT NULL
       , "Barcode" Text PRIMARY KEY  NOT NULL  UNIQUE
       , "Description" Text NOT NULL
       , "SizeValue" DOUBLE NOT NULL
       , "SizeUnit" INTEGER NOT NULL
       , "ShelfLife" INTEGER NOT NULL
       , "3MonthSupply" Integer NOT NULL
);

CREATE TABLE "Model" (
       "name" varchar(50) PRIMARY KEY NOT NULL UNIQUE
       , "value" varchar(50) NULL
);

CREATE TABLE "ProductContainer" (
       "Name" TEXT NOT NULL
       , "ProductContainerID" INTEGER
       , "3MonthSupplyValue" DOUBLE NOT NULL
       , "3MonthSupplyUnit" INTEGER
       , "ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE
);

CREATE TABLE "ProductContainerProductRelation" (
     "ProductContainerID" INTEGER NOT NULL
     , "ProductID" INTEGER NOT NULL
);

CREATE TABLE "UnitEnum" (
       "ID" INTEGER PRIMARY KEY  NOT NULL  UNIQUE
       , "Name" TEXT NOT NULL  UNIQUE
);
