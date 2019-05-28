# ZoyiWork
조이코퍼레이션 사전과제
## DB 스키마

```sql
DROP TABLE IF EXISTS `zoyikey`;

CREATE TABLE `zoyikey` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table zoyitranslation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `zoyitranslation`;

CREATE TABLE `zoyitranslation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `keyId` int(11) DEFAULT NULL,
  `locale` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



## DAOBase

```java
public Connection getConnection() throws SQLException {
  try {
    Class.forName("com.mysql.jdbc.Driver"); 
  } catch (ClassNotFoundException e) {
    e.printStackTrace();
  }
  try {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoyi?verifyServerCertificate=false&amp; useSSL=false", 
        "db아이디", "db 비번");
    return conn;
  } catch (SQLException e) {

    e.printStackTrace();
  } 
  return null;
}
```

DB연결에서 아이디와 비밀번호 수정해주셔야 합니다.


### Mysql 5.7 <u>DB이름 : zoyi</u>
### JDK 1.8
### servlet 3.1 
