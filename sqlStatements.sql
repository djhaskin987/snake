DROP TABLE IF EXISTS "main"."Item";


DROP TABLE IF EXISTS "main"."Product";


DROP TABLE IF EXISTS "main"."Model";


DROP TABLE IF EXISTS "main"."ProductContainer";


DROP TABLE IF EXISTS "main"."ProductContainerProductRelation";

CREATE TABLE "Item" (
     "ProductBarcode" INTEGER NOT NULL
     , "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE
     , "EntryDate" DATETIME NOT NULL
     , "ExitTime" DATETIME
     , "ProductContainerName" TEXT NOT NULL
     , "ProductContainerStorageUnit" TEXT NOT NULL
);

CREATE TABLE "Product" (
       "CreationDate" DateTime NOT NULL
       , "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE
       , "Description" TEXT NOT NULL
       , "SizeValue" DOUBLE NOT NULL
       , "SizeUnit" TEXT NOT NULL
       , "ShelfLife" INTEGER NOT NULL
       , "ThreeMonthSupply" Integer NOT NULL
);

CREATE TABLE "Model" (
       "name" varchar(50) PRIMARY KEY NOT NULL UNIQUE
       , "value" varchar(50) NULL
);

CREATE TABLE "ProductContainer" (
       "Name" TEXT NOT NULL
       , "SorageUnit" TEXT NOT NULL
       , "ParentContainer" TEXT
       , "ThreeMonthSupplyValue" DOUBLE
       , "ThreeMonthSupplyUnit" TEXT
);

CREATE TABLE "ProductContainerProductRelation" (
       "ProductContainerName" TEXT NOT NULL
       , "ProductContainerStorageUnit" TEXT NOT NULL
       , "ProductBarcode" TEXT NOT NULL
);
