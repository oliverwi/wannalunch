dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:hsqldb:mem:devDB"
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	luiz {
	  dataSource {
	    dbCreate = "update"
      url = "jdbc:postgresql://localhost/wannalunch2"
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQLDialect
      pooled = true
      username = "luiz"
      password = "psql"
	  }
	}
	timur {
	  dataSource {
        dbCreate = "create-drop"
        url = "jdbc:postgresql://localhost/wannalunch"
        driverClassName = "org.postgresql.Driver"
        dialect = org.hibernate.dialect.PostgreSQLDialect
        pooled = true
        username = "postgres"
        password = "postgres"
//        dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//        url = "jdbc:hsqldb:mem:devDB"
	  }
	}
}