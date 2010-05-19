import groovy.sql.Sql

if (args.length < 2) {
  println "Please, supply username and password as command-line args"
  System.exit(1)
}

def username = args[0]
def password = args[1]

def sql = Sql.newInstance("jdbc:postgresql://localhost/wannalunch", username, password)

sql.withTransaction { conn ->
  println "Creating twitter accounts"
  
  sql.eachRow("select id, username from wl_user") { user ->
    sql.executeInsert("insert into twitter_account (id, version, username) values (nextval('hibernate_sequence'), 0, ?)", user.username)
    
    sql.eachRow("select id from twitter_account where username = ?", [user.username]) { acc ->
      sql.execute("update wl_user set twitter_account_id = ? where username = ?", [acc.id, user.username]) 
    }
  }

  println "Success"

  if (args.find { it == 'commit' }) {
    println "committing"
    conn.commit()
  } else {
    println "rolling back"
    conn.rollback()
  }
}
