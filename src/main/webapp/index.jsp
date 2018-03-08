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
<!-- 搭建显示页面：使用BootStrap搭建（https://v3.bootcss.com/）。官方文档：栅格系统--https://v3.bootcss.com/css/#grid-->
<%--列表页面--%>
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
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
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

<%--员工新增的模态框，参考：https://v3.bootcss.com/javascript/#modals--%>
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <%--表单样式参照：https://v3.bootcss.com/css/#forms--%>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <%--name跟JavaBean的属性名一样--%>
                            <input type="text" name="empName" class="form-control" id="empName_add_input" placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="email_add_input" placeholder="email@atguigu.com">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门提交 部门id 即可 -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //1、页面加载完成以后，直接去发送ajax请求,要到列表分页数据
    $(function () {
        //去首页
        to_page(1);
    })

    //抽取为一个单独的方法，方便调用
    function to_page(pageNo) {
        $.ajax({
            url:"${APP_PATH}/emps",
            data:"pageNo="+pageNo,
            type:"GET",
            success:function (result) {
                //在浏览器控制台查看数据信息
                //console.log(result);
                //1、解析并显示 员工数据
                build_emps_table(result);
                //2、解析并显示 分页信息
                build_page_info(result);
                //3、解析显示右下方 分页条
                build_page_nav(result)
            }
        });
    }

    //1、解析显示员工列表数据
    function build_emps_table(result){
        //清空table表格（因为ajax请求页面只是局部刷新，如果这里不清空数据的话，点击下一页或其他页码，会发现之前的列表数据还在，获取的新数据在此基础上添加）
        $("#emps_table tbody").empty();

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
        //清空信息
        $("#page_info_area").empty();

        $("#page_info_area").append("当前 "+result.extend.pageInfo.pageNum+" 页,总 "+
            result.extend.pageInfo.pages+" 页,总 "+
            result.extend.pageInfo.total+" 条记录");
    }

    //3、解析显示右下方 分页条，点击分页要能去下一页....
    function build_page_nav(result){
        //page_nav_area
        //清空信息
        $("#page_nav_area").empty();

        //1、ul样式
        var ul = $("<ul></ul>").addClass("pagination");
        //构建元素
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));//首页
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));//前一页
        if(result.extend.pageInfo.hasPreviousPage == false){//判断是否有上一页
            firstPageLi.addClass("disabled");//禁用
            prePageLi.addClass("disabled");
        }else {
            //为元素添加点击翻页的事件
            firstPageLi.click(function(){
                to_page(1);
            });
            prePageLi.click(function(){
                to_page(result.extend.pageInfo.pageNum -1);
            });
        }

        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));//下一页
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));//末页
        if(result.extend.pageInfo.hasNextPage == false){//判断是否有下一页
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        }else {
            nextPageLi.click(function(){
                to_page(result.extend.pageInfo.pageNum +1);
            });
            lastPageLi.click(function(){
                to_page(result.extend.pageInfo.pages);
            });
        }

        //2、添加首页和前一页 的提示
        ul.append(firstPageLi).append(prePageLi);
        //1，2，3……。3、遍历给ul中 添加页码提示
        $.each(result.extend.pageInfo.navigatepageNums,function(index,item){

            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if(result.extend.pageInfo.pageNum == item){//遍历每个显示的页码 判断是否与当前列表的页码相同
                numLi.addClass("active");//被选中样式
            }
            //点击动作
            numLi.click(function(){
                to_page(item);
            });

            ul.append(numLi);
        });
        //4、添加下一页和末页 的提示
        ul.append(nextPageLi).append(lastPageLi);

        //5、把构建好的ul加入到nav
        var navEle = $("<nav></nav>").append(ul);
        //6、将导航条nav添加到要显示的div中
        navEle.appendTo("#page_nav_area");
    }

    //点击新增按钮弹出模态框
    $("#emp_add_modal_btn").click(function(){
        //发送ajax请求，查出部门信息，显示在下拉列表中
        getDepts();
        //弹出模态框。参考：https://v3.bootcss.com/javascript/#通过-javascript-调用
        $("#empAddModal").modal({
            backdrop:"static"
        });
    });

    //查出所有的部门信息并显示在下拉列表中
    function getDepts(){
        $.ajax({
            url:"${APP_PATH}/depts",
            type:"GET",
            success:function(result){
                //{"code":100,"msg":"处理成功！",
                //"extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
                //console.log(result);

                //显示部门信息在下拉列表中
                //$("#empAddModal select").append("")
                $.each(result.extend.depts,function(){
                    //下拉框选项
                    var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId);
                    //添加到员工模态框的下拉框 select 中
                    optionEle.appendTo("#empAddModal select");
                });
            }
        });
    }
</script>
</body>
</html>