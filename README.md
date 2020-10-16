# internet-shop
Internet-shop with basic operations. 

Project uses N-tier architecture with DB layer, DAO layer, Service layer, Controllers layer and View layer.<br>
This project has been developed according to SOLID principles with authorization and authentication by RBAC filter strategy.

One user can have multiple roles.<br>
##### No role:<br>
  - Registration
  - Authorization
##### User:<br>
  - View all products
  - Add / remove products to shopping cart
  - Place an order
  - Logout
##### Admin:<br>
  - View / delete users
  - View / add / remove products
  - View / delete user orders.
  - Logout

# Technologies used<br>
**backend:** Java, Servlets, Tomcat, JDBC<br>
**frontend:** HTML, CSS, Bootstrap, JSP, JSTL<br>
**database:** MySQL<br>

# To run this project you need:<br>
1) *Download and install* the [JDK](https://www.oracle.com/java/technologies/javase-downloads.html, "Download JDK") <br>
2) *Download and install* servlet container (for example Apache [Tomcat](https://tomcat.apache.org/download-90.cgi, "Download Tomcat"))<br>
3) *Download and install* [MySQL Server](https://dev.mysql.com/downloads/)<br>
+ Setup connection with MySQL using default parameters <br>
  + user: *"root"*<br>
  + password: *"root"*<br>
  + url: jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC<br>
+ Create schema internet_shop<br>
+ Create tables using commands from init_db.sql in src/main/resources/ folder<br>

# Author
[Denys Tarkaiev](https://github.com/tarkaiev "Author")
