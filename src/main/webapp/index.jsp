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
                            <span class="help-block"></span><%--包含在此元素之内的 .control-label、.form-control 和 .help-block 元素都将接受Bootstrap 对表单控件的校验状态的样式--%>
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
                <button type="button" class="btn btn-primary" id="emp_add_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<%--员工修改的模态框--%>
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">员工修改</h4>
            </div>
            <div class="modal-body">
                <%--表单样式参照：https://v3.bootcss.com/css/#forms--%>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="email_update_input" placeholder="email@atguigu.com">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
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
                <button type="button" class="btn btn-primary" id="emp_update_btn">修改</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //设置一个全局变量
    //总记录数
    var totalRecord;

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
            //为编辑按钮添加一个自定义的属性，来表示当前员工id
            editBtn.attr("edit-id",item.empId);

            var delBtn =  $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
            //为删除按钮添加一个自定义的属性来表示当前删除的员工id
            delBtn.attr("del-id",item.empId);

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

        totalRecord = result.extend.pageInfo.total;//给全局变量赋值方便其他方法使用该变量值
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
        //清除表单数据（表单完整重置：表单的数据，表单样式）
        // ----如果每次打开还是上一次新增成功的员工信息，此时各输入框都是校验通过的，在保存时会直接发送AJAX请求保存，不会再校验。所以可以清除之前填写的信息，重新填写校验
        //$("#empAddModal form")[0].reset();//只清除了文本内容，样式并没有改变
        reset_form("#empAddModal form");//清空表单样式及内容

        //发送ajax请求，查出部门信息，显示在下拉列表中
        getDepts("#empAddModal select");

        //弹出模态框。参考：https://v3.bootcss.com/javascript/#通过-javascript-调用
        $("#empAddModal").modal({
            backdrop:"static"
        });
    });

    //清空表单样式及内容
    function reset_form(ele){
        //清空表单内容
        $(ele)[0].reset();
        //清空表单样式
        $(ele).find("*").removeClass("has-error has-success");
        $(ele).find(".help-block").text("");
    }

    //查出所有的部门信息并显示在下拉列表中
    function getDepts(ele){
        //清空之前下拉列表的值
        $(ele).empty();

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
                    optionEle.appendTo(ele);
                });
            }
        });
    }

    //点击保存，保存员工。
    $("#emp_save_btn").click(function(){
        //1、模态框中填写的表单数据提交给服务器进行保存

        //2、先对要提交给服务器的数据进行前端校验
        if(!validate_add_form()){
            return false;//校验失败，结束校验
        };

        //3、判断之前的ajax用户名校验是否成功。如果成功。
        if($(this).attr("ajax-va")=="error"){//判断当前保存按钮自定义属性的值是否表示不可用，不可用则结束校验
            show_validate_msg("#empName_add_input","error",$(this).attr("err-msg"));
            return false;
        }

        //4、发送ajax请求保存员工
        $.ajax({
            url:"${APP_PATH}/emp",
            type:"POST",
            data:$("#empAddModal form").serialize(),//serialize()序列表表格内容为字符串，用于 Ajax 请求。
            success:function(result){
                //alert(result.msg);
                if(result.code == 100) {
                    //员工保存成功，需要如下两个工作：
                    //1、关闭模态框。https://v3.bootcss.com/javascript/#modals-methods
                    $("#empAddModal").modal('hide');

                    //2、来到列表最后一页，显示刚才保存的数据
                    //发送ajax请求显示最后一页数据即可
                    to_page(totalRecord);//传入大于总页数的值即可。这里传入总记录数。（因为之前在MyBatis配置文件已经设置分页插件的reasonable属性为true，pageNum>pages（超过总数时），会查询最后一页）
                }else{
                    //显示失败信息，有哪个字段的错误信息就显示哪个字段的；
                    //console.log(result);//先测试查看result有哪些内容方便后面可以用里面的属性等东西
                    //alert(result.extend.errorFields.email);//测试查看email属性值是什么，可以发现如果该属性没有则为undefined
                    if(undefined != result.extend.errorFields.email){
                        //显示邮箱错误信息
                        show_validate_msg("#email_add_input", "error", result.extend.errorFields.email);
                    }
                    if(undefined != result.extend.errorFields.empName){
                        //显示员工名字的错误信息
                        show_validate_msg("#empName_add_input", "error", result.extend.errorFields.empName);
                    }
                }
            }
        });
    });

    //校验表单数据
    function validate_add_form(){
        //1、拿到要校验的数据，使用正则表达式
        var empName = $("#empName_add_input").val();
        //正则表达式。可以参见jQuery文档
        var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;//此处首尾的两个/是js中的，Java中没有
        //alert(regName.test(empName));//测试正则表达式，满足表达式返回true，不满足返回false
        if(!regName.test(empName)){//校验失败
            //alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
            //注意每次显示新样式之前都应该清空这个元素之前的样式
            /*$("#empName_add_input").parent().addClass("has-error");
            $("#empName_add_input").next("span").text("用户名可以是2-5位中文或者6-16位英文和数字的组合");*/
            show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
            return false;
        }else{
           /* $("#empName_add_input").parent().addClass("has-success");
            $("#empName_add_input").next("span").text("");*/
            show_validate_msg("#empName_add_input", "success", "");
        }

        //2、校验邮箱信息
        var email = $("#email_add_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!regEmail.test(email)){
            //alert("邮箱格式不正确");
            /*$("#email_add_input").parent().addClass("has-error");
            $("#email_add_input").next("span").text("邮箱格式不正确");*/
            show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
            return false;
        }else{
            /*$("#email_add_input").parent().addClass("has-success");
            $("#email_add_input").next("span").text("");*/
            show_validate_msg("#email_add_input", "success", "");
        }
        return true;
    }
    //显示校验结果的提示信息（校验的模式基本相同，抽取出为一个方法）
    function show_validate_msg(ele,status,msg){
        //不管校验成功或失败都要清除当前元素的校验状态
        $(ele).parent().removeClass("has-success has-error");//移除多个样式
        $(ele).next("span").text("");

        if("success"==status){
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        }else if("error" == status){
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }

    //校验用户名是否可用（文本框内容变化时发送ajax请求去后台校验）
    $("#empName_add_input").change(function(){
        //发送ajax请求校验用户名是否可用
        var empName = this.value;
        $.ajax({
            url:"${APP_PATH}/checkuser",
            data:"empName="+empName,
            type:"POST",
            success:function(result){
                if(result.code==100){
                    show_validate_msg("#empName_add_input","success","用户名可用");
                    $("#emp_save_btn").attr("ajax-va","success");//为保存按钮自定义一个属性ajax-va，标识当前用户名是否可用
                }else{
                    show_validate_msg("#empName_add_input","error",result.extend.va_msg);
                    $("#emp_save_btn").attr("ajax-va","error");
                    $("#emp_save_btn").attr("err-msg",result.extend.va_msg);//自定义err-msg以便在点击保存设置前端校验时样式问题
                }
            }
        });
    });

    //编辑功能
    //1、下面click()写法不起作用。我们是 按钮创建之前就绑定了click，所以绑定不上。（按钮创建 在上面build_emps_table方法里，该方法是在页面加载完后调用）
    /*$(".edit_btn").click(function () {
     alert("edit");
     })*/
    //解决方法：1）、可以在创建按钮的时候绑定。2）、绑定点击.live()
    //ps：live方法介绍----jQuery 给所有匹配的元素附加一个事件处理函数，即使这个元素是以后再添加进来的也有效。
   /* $(".edit_btn").live(function () {
        alert("edit");
    })*/
    //上面live方法测试运行时浏览器控制台说它不是一个方法，原因是jquery新版没有live方法（1.7版本以前使用live），使用on进行替代
    $(document).on("click",".edit_btn",function(){
        //alert("edit");

        //1、查出部门信息，并显示部门列表
        getDepts("#empUpdateModal select");
        //2、查出员工信息，显示员工信息
        getEmp($(this).attr("edit-id"));

        $("#empUpdateModal").modal({
            backdrop:"static"
        });
    });

    //获取员工信息
    function getEmp(id){
        $.ajax({
            url:"${APP_PATH}/emp/"+id,
            type:"GET",
            success:function(result){
                console.log(result);
                var empData = result.extend.emp;
                $("#empName_update_static").text(empData.empName);
                $("#email_update_input").val(empData.email);
                $("#empUpdateModal input[name=gender]").val([empData.gender]);//jQuery中val方法返回任意元素的值。包括select。如果多选，将返回一个数组，其包含所选的值。
                $("#empUpdateModal select").val([empData.dId]);
            }
        });
    }
</script>
</body>
</html>