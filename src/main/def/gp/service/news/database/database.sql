CREATE TABLE gp_NewsCategory
(	
	id CHAR(32) NOT NULL,
	title VARCHAR(255) NOT NULL,
	CONSTRAINT GP_PK_NEWS_CATEGORY PRIMARY KEY (id)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gp_News
(	
	id CHAR(32) NOT NULL,
	title VARCHAR(255) NOT NULL,
	categoryId CHAR(32) NOT NULL,
	introductionImage CHAR(32) NULL,
	introduction LONGTEXT NULL,
	content LONGTEXT NOT NULL,
	CONSTRAINT GP_PK_NEWS PRIMARY KEY (id)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;