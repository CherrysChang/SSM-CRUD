<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <!-- web路径：
        不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
        以/开始的相对路径，找资源，以服务器的路径为标准(http://localhost:3306)；所以 需要加上项目名：http://localhost:3306/crud
     -->
    <script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
    <link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 搭建显示页面：使用BootStrap搭建。官方文档：栅格系统--https://v3.bootcss.com/css/#grid-->
<div class="container">
    <!-- 第一行：标题 -->
    <div class="row">
        <%--占据12列--%>
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <!-- 第二行：按钮 -->
    <div class="row">
        <%--占据4列 并设置偏移--%>
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!-- 第三行：显示表格数据 -->
    <div class="row">
        <%--占据12列--%>
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <!-- 第四行：显示分页信息 -->
    <div class="row">
        <!--1、分页文字信息  -->
        <div class="col-md-6" id="page_info_area"></div>
        <!--2、分页条信息-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
<script type="text/javascript">
    //1、页面加载完成以后，直接去发送ajax请求,要到分页数据
    $(function () {
        $.ajax({
            url:"${APP_PATH}/emps",
            data:"pageNo=1",
            type:"GET",
            success:function (result) {
                //在浏览器控制台查看数据信息
                console.log(result);
                //1、解析并显示 员工数据
                build_emps_table(result);
                //2、解析并显示 分页信息
                build_page_info(result);
                //3、解析显示右下方 分页条
                build_page_nav(result)
            }
        });
    })

    //1、解析显示员工列表数据
    function build_emps_table(result){
        var emps = result.extend.pageInfo.list;
        $.each(emps,function(index,item){
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderTd = $("<td></td>").append(item.gender=='M'?"男":"女");
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            /**
             <button class="">
             <span class="" aria-hidden="true"></span>
             编辑
             </button>
             */
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
            var delBtn =  $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);

            //append方法执行完成以后还是返回原来的元素
            $("<tr></tr>").append(empIdTd)
                .append(empNameTd)
                .append(genderTd)
                .append(emailTd)
                .append(deptNameTd)
                .append(btnTd)
                .appendTo("#emps_table tbody");
        });
    }

    //2、解析显示左侧 分页信息
    function build_page_info(result){
        $("#page_info_area").append("当前 "+result.extend.pageInfo.pageNum+" 页,总 "+
            result.extend.pageInfo.pages+" 页,总 "+
            result.extend.pageInfo.total+" 条记录");
    }

    //3、解析显示右下方 分页条
    function build_page_nav(result){
        //page_nav_area
        //1、ul样式
        var ul = $("<ul></ul>").addClass("pagination");
        //构建元素
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));

        //2、添加首页和前一页 的提示
        ul.append(firstPageLi).append(prePageLi);
        //1，2，3……。3、遍历给ul中 添加页码提示
        $.each(result.extend.pageInfo.navigatepageNums,function(index,item){

            var numLi = $("<li></li>").append($("<a></a>").append(item));
            ul.append(numLi);
        });
        //4、添加下一页和末页 的提示
        ul.append(nextPageLi).append(lastPageLi);

        //5、把构建好的ul加入到nav
        var navEle = $("<nav></nav>").append(ul);
        //6、将导航条nav添加到要显示的div中
        navEle.appendTo("#page_nav_area");
    }
</script>
</body>
</html>