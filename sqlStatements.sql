DROP TABLE IF EXISTS "main"."Item";

DROP TABLE IF EXISTS "main"."Product";

DROP TABLE IF EXISTS "main"."Model";

drop table "main"."Model"


DROP TABLE IF EXISTS "main"."ProductContainer"


DROP TABLE IF EXISTS "main"."ProductContainerProductRelation"


DROP TABLE IF EXISTS "main"."UnitEnum"


---------------------------
-- SQL CREATE STATEMENTS --
---------------------------

CREATE TABLE "Item" (
     "ProductBarcode" TEXT NOT NULL
     , "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE
     , "EntryDate" DATETIME NOT NULL
     , "ExitTime" DATETIME
     , "ProductContainerName" TEXT NOT NULL
     , "ProductContainerStorageUnit" TEXT NOT NULL
);

CREATE TABLE "Product" (
       "CreationDate" DateTime NOT NULL
       , "Barcode" Text PRIMARY KEY  NOT NULL  UNIQUE
       , "Description" Text NOT NULL
       , "SizeValue" DOUBLE NOT NULL
       , "SizeUnit" TEXT NOT NULL
       , "ShelfLife" INTEGER NOT NULL
       , "3MonthSupply" Integer NOT NULL 
);

CREATE TABLE "Model" (
       "id" INTEGER PRIMARY KEY NOT NULL
    , "lastRIR" INTEGER NOT NULL
);

CREATE TABLE "ProductContainer" (
       "Name" TEXT NOT NULL 
       , "StorageUnit" TEXT NOT NULL 
       , "ThreeMonthSupplyValue" DOUBLE
       , "ThreeMonthSupplyUnit" INTEGER
)

CREATE TABLE "ProductContainerProductRelation" (
       "ProductContainerName" TEXT NOT NULL
      http://pip.verisignlabs.com/login.do
       , "ProductBarcode" TEXT NOT NULL
);

