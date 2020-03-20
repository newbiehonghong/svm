DROP TABLE IF EXISTS "dashboard_config";
CREATE TABLE "dashboard_config" (
	"conf_id" INT4 NOT NULL,
	"conf_type" INT4 NOT NULL,
	"width" INT4 NOT NULL,
	"height" INT4 NOT NULL,
	"conf_index" INT4 NOT NULL,
	"count" INT4 NULL,
	"conf_mode" VARCHAR(12) NULL,
	"services" VARCHAR(1024) NULL,
	"apis" VARCHAR(1024) NULL,
	CONSTRAINT "pk_dashboard_config" PRIMARY KEY ("conf_id")
);