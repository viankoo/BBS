 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>

<!-- <style type="text/css">
	.page, .page div{overflow:hidden;width:100%;margin-top:5px;}
	.page {text-align:right;margin:0;padding:0;font-size:12px;}
   	.page button{margin:0;cursor:pointer;}
   	.page button.curr{color:black;border-color:red;background-color:transparent; font-weight:bold;cursor:auto;}
   	.page span button {color:blue;}
   	.page select {margin:0;padding:0;width:39px;}
   	.page input {margin:0;padding:0;padding-left:2px;width:27px;}
</style> -->

</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='add.jsp'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="list.do" method="get" style="padding-top:5px;">
品牌名称: <input type="text" name="name" value="${name}"/>
	<select name="isDisplay">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<script>
		$("select[name='isDisplay']").val("${isDisplay}");
	</script>
	
	<input type="submit" class="query" value="查询"/>
</form>
<form id="formList" method="post">
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="isChecked(this.checked);"/></th>
			<th>品牌ID</th>
			<th>品牌名称</th>
			<th>品牌图片</th>
			<th>品牌描述</th>
			<th>排序</th>
			<th>是否可用</th>
			<th>操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${pagebrands.result }" var="brand">
		<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
			<td><input type="checkbox"  name="ids" value="${brand.id }" /></td>
				<td align="center">${brand.id }</td>
				<td align="center">${brand.name }</td>
				<td align="center"><img width="40" height="40" src="${brand.imgUrl }"/></td>
				<td align="center">${brand.description }</td>
				<td align="center">${brand.sort }</td>
				<td align="center">${brand.isDisplay }</td>
				<td align="center">
				<a class="pn-opt" href="toEdit.do?id=${brand.id }">修改</a> | <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}" href="#">删除</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15">
	<span class="r inb_a page_b"> <a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=1"><font size="2">首页</font></a> <c:if test="${pageBrand.pageNum<=1}">
			<font size="2">上一页</font>
		</c:if> <c:if test="${pagebrands.pageNum>1}">
			<a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=${pagebrands.pageNum-1}"><font size="2">上一页</font></a>
		</c:if> &nbsp; 

           <c:forEach begin="1" end="${pagebrands.pages}" var="ps">
			<c:if test="${pagebrands.pageNum==ps}">
				<strong>${ps}</strong>
			</c:if>
			<c:if test="${pagebrands.pageNum!=ps}">
				<a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=${ps}">${ps}</a>
			</c:if>
			&nbsp;
		</c:forEach> 

            <c:if test="${pagebrands.pageNum>=pagebrands.pages}">
			<font size="2">下一页</font>
		</c:if> <c:if test="${pagebrands.pageNum<pagebrands.pages}">
			<a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=${pagebrands.pageNum+1}"><font size="2">下一页</font></a>
		</c:if> <a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=${pagebrands.pages}"><font size="2">尾页</font></a> 共<var>${pagebrands.pages}</var>页 到第 <input type="text" size="3" id="PAGENO" />页 <input type="button" onclick="javascript:window.location.href = '/product/list.do?&amp;isShow=0&amp;pageNo=' + $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip" />

	</span>
</div>
<!-- 分页开始 -->
 <%-- <div class="page">
	
	<c:if test="${pagebrands.pageNum > 1 }">
		<button onclick="gogogo(1)">首页</button>
		<button onclick="gogogo(${pagebrands.pageNum-1})">上一页</button>
	</c:if>
	<span>
		<c:forEach begin="1" end="${pagebrands.pages }" var="i">
			<button class="${pagebrands.pageNum == i ? 'curr' : ''}" onclick="gogogo(${i});">${i}</button>
		</c:forEach>
	</span>
	
	<c:if test="${pagebrands.pageNum < pagebrands.pages }">
		<button onclick="gogogo(${pagebrands.pageNum+1})">下一页</button>
		<button onclick="gogogo(${pagebrands.pages})">尾页</button>
	</c:if>
	<div>
		共 ${pagebrands.pages } 条记录，
		每页 <select id="pageSize">
			<option value="5">5</option>
			<option value="10" ${pagebrands.pageSize == 10 ? 'selected' : '' }>10</option>
			<option value="20" ${pagebrands.pageSize == 20 ? 'selected' : '' }>20</option>
		</select> 条，
		当前页 ${pagebrands.pageNum } / ${pagebrands.pages }，
		跳转到第 <input id="currPage" value="${pagebrands.pageNum }" style="width:35px;" /> 页
		<button onclick="gogogo(currPage.value)">GO</button>
	</div>
</div>
<script type="text/javascript">
	function gogogo(pageNum) {
		location = "list.do?pageNum=" 
		+ pageNum + "&pageSize=" + pageSize.value+"&name=${name}&isDisplay=${isDisplay}";
	}
</script> --%>
<!-- 分页结束 -->
<div style="margin-top:15px;">
<input class="del-button" type="button" value="删除" onclick="optDelete('${name}','${isDisplay }','${pagebrands.pageNum }');"/>
</div>
</form>
</div>
</body>
<script type="text/javascript">
	function isChecked(b){
		$("input[name='ids']").attr("checked",b);
	}
	function optDelete(name,isDisplay,pageNum){
		if($("input[name='ids']:checked").size()==0){
			return ;
		}
		if(!confirm("你确定要删除吗TnT")){
			return ;
		}
		$("#formList")[0].action='delete.do?name=' + name + '&isDisplay='
		+ isDisplay + '&pageNum=' + pageNum;
		$("#formList").submit();
	}
</script>
</html>