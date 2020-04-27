# SORM
为深入了解ORM框架其底层实现而编写SORM(Simple or Super ORM 框架)Demo。
1. 封装JDBC，实现对数据的增删改查
2. 读取db.properties中对数据库的配置实现自动生成pojo代码以及连接池配置
3. 使用反射机制来实现参数获取和存储结果。
4. Java数据类型 <==> 数据库数据类型  (互转)
5. 每张表只能有一个主键，暂时只能处理一个主键

使用：
1. 本框架依赖于连接数据库的驱动包(版本视自己的数据库)
2. 使用JDK1.8
3. 需要db.properties配置文件，放于src目录下

文件夹结构：
1. src --源码
2. api -- api文档
3. jar -- 打包的jar，可以下载直接用
4. Demo--使用案例
5. doc --注意事项