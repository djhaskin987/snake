DROP TABLE IF EXISTS "main"."Item";


DROP TABLE IF EXISTS "main"."Product";


DROP TABLE IF EXISTS "main"."Model";


DROP TABLE IF EXISTS "main"."ProductContainer";


DROP TABLE IF EXISTS "main"."ProductContainerProductRelation";

CREATE TABLE "Item" (
     "ProductBarcode" TEXT NOT NULL
     , "Barcode" TEXT PRIMARY KEY  NOT NULL  UNIQUE
     , "EntryDate" DATETIME NOT NULL
     , "ExitTime" DATETIME
     , "ProductContainerName" TEXT
     , "ProductContainerStorageUnit" TEXT
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
       "id" INTEGER PRIMARY KEY NOT NULL
    , "lastRIR" INTEGER NOT NULL
);

CREATE TABLE "ProductContainer" (
       "Name" TEXT NOT NULL
       , "StorageUnit" TEXT
       , "ParentContainer" TEXT
       , "IsParentStorageUnit" BIT
       , "ThreeMonthSupplyValue" DOUBLE
       , "ThreeMonthSupplyUnit" TEXT
);

CREATE TABLE "ProductContainerProductRelation" (
       "ProductContainerName" TEXT NOT NULL
       , "ProductContainerStorageUnit" TEXT NOT NULL
       , "ProductBarcode" TEXT NOT NULL
);
