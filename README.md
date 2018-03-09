# ssm-crud
主要内容：

基于Maven+SpringMVC+Spring+MyBatis+Bootstrap的组合，快速开发一个完整的CRUD功能。

除对框架组合的基本使用外，还涉及到许多的开发细节：Bootstrap搭建页面，MyBatis逆向工程使用，Rest风格的URI，@ResponseBody注解完成AJAX，AJAX发送PUT请求的问题，jQuery前端校验，SpringMVC的JSR303后端校验等。

基础环境搭建：
- 1、创建一个maven工程
- 2、引入项目依赖的jar包
    - spring
    - springmvc
    - mybatis
    - 数据库连接池，驱动包
    - 其他（jstl，servlet-api，junit）
- 3、引入bootstrap前端框架
- 4、编写ssm整合的关键配置文件
    - web.xml，spring,springmvc,mybatis，使用mybatis的逆向工程生成对应的bean以及mapper
- 5、测试mapper

注意点：

	* 1、在新增以及修改时，引入数据校验（前端 ， 后端 ） 
	* 2、删除时，单个&批量删除
	* 3、mybatis generator的使用，自动生成 xxMapper文件
	* 4、使用ajax请求。SpringMVC处理该请求，只需要@ResponseBody注解

