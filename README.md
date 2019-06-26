https://shardingsphere.apache.org/document/current/en/manual/sharding-jdbc/configuration/config-spring-boot/
https://habr.com/ru/company/oleg-bunin/blog/309330/

Насколько я понял, нужно было не использовать готовые инструменты, а проявить навыки велосипедостроение. Исходя из этого и действовал)

Test
docker run --detach --name=mysql1 -p 3306:3306  --env="MYSQL_ROOT_PASSWORD=12345" mysql
docker run --detach --name=mysql2 -p 3307:3306  --env="MYSQL_ROOT_PASSWORD=12345" mysql
docker run --detach --name=mysql3 -p 3308:3306  --env="MYSQL_ROOT_PASSWORD=12345" mysql


TODO:
шардить на datasource, а готовые сервисы - позволит прикруить транзакционность без проблем,
security,
swagger
