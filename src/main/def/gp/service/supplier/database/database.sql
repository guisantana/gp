
CREATE TABLE gp_SupplierCategoryGroup
(	
	id CHAR(32) NOT NULL,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT GP_PK_SUPPLIERCATEGORYGROUP PRIMARY KEY (id)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gp_SupplierCategory
(	
	id CHAR(32) NOT NULL,
	name VARCHAR(255) NOT NULL,
	image CHAR(32) NULL,
	description LONGTEXT NULL,
	groupId CHAR(32) NOT NULL,
	CONSTRAINT GP_PK_SUPPLIERCATEGORY PRIMARY KEY (id),
	CONSTRAINT GP_FK_SUPPLIERCATEGORY FOREIGN KEY (groupId) REFERENCES gp_SupplierCategoryGroup(id)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gp_Supplier
(	
	id CHAR(32) NOT NULL,
	title VARCHAR(255) NOT NULL,
	logo CHAR(32) NULL,
	introduction VARCHAR(500) NOT NULL,
	description LONGTEXT NOT NULL,
	email VARCHAR(150) NOT NULL,
	site VARCHAR(255) NULL,
	telephone CHAR(12) NULL,
	mobilePhone CHAR(12) NULL,
	address VARCHAR(500) NULL,
	video VARCHAR(255) NULL,
	CONSTRAINT GP_PK_SUPPLIER PRIMARY KEY (id)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gp_SupplierRelation
(	
	id CHAR(32) NOT NULL,
	supplierId CHAR(32) NOT NULL,
	categoryId CHAR(32) NOT NULL,
	CONSTRAINT GP_PK_SUPPLIERRELATION PRIMARY KEY (id),
	CONSTRAINT GP_FK_SUPPLIERRELATION1 FOREIGN KEY (supplierId) REFERENCES gp_Supplier(id),
	CONSTRAINT GP_FK_SUPPLIERRELATION2 FOREIGN KEY (categoryId) REFERENCES gp_SupplierCategory(id)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE VIEW gp_vw_SupplierPracticalBudget AS 
	select 
		r.idSupplier AS idSupplier, 
		s.name AS name, 
		s.email AS email, 
		s.budgetEmail AS budgetEmail, 
		r.idCategory AS idCategory, 
		c.name AS category 
	from (
			(gp_nv_supplier s 
			join gp_nv_supplierrelation r) 
			join gp_nv_suppliercategory c
		) 
	where (
			(s.id = r.idSupplier) 
			and 
			(c.id = r.idCategory) 
			and 
			(s.published = 1)
		)


