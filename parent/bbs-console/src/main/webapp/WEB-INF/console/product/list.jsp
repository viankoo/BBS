<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
//上架
function isShow(){
	//请至少选择一个
	var size = $("input[name='ids']:checked").size();
	if(size == 0){
		alert("请至少选择一个");
		return;
	}
	//你确定删除吗
	if(!confirm("你确定上架吗")){
		return;
	}
	//提交 Form表单
	$("#jvForm").attr("action","isShow.do");
	$("#jvForm").attr("method","post");
	$("#jvForm").submit();
	
}
//下架
function isHide(){
	//请至少选择一个
	var size = $("input[name='ids']:checked").size();
	if(size == 0){
		alert("请至少选择一个");
		return;
	}
	//你确定删除吗
	if(!confirm("你确定下架吗")){
		return;
	}
	//提交 Form表单
	$("#jvForm").attr("action","isHide.do");
	$("#jvForm").attr("method","post");
	$("#jvForm").submit();
	
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="window.location.href='toAdd.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="/product/list.do" method="post" style="padding-top:5px;">
名称: <input type="text" name="name"/>
	<select name="brandId">
		<option value="">请选择品牌</option>
		<option value="1">依琦莲</option>
		<option value="2">凯速（KANSOON）</option>
	</select>
	<select name="isShow">
		<option value="1">上架</option>
		<option selected="selected" value="0">下架</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form id="jvForm">
<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach var="pageproduct" items="${pageproducts.result }">
		<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
			<td><input type="checkbox" name="ids" value="${pageproduct.id }"/></td>
			<td align="center">${pageproduct.id }</td>
			<td align="center">${pageproduct.name }</td>
			<td align="center">
			
			<!-- forTokens 和 forEach 类似 -->
			<c:forTokens items="${pageproduct.imgUrl}" delims="," var="imgurl" begin="0" end="0">
				<img width="50" height="50" src="${imgurl}" />
			</c:forTokens></td>
							
			<td align="center">${pageproduct.isNew }</td>
			<td align="center">${pageproduct.isHot }</td>
			<td align="center">${pageproduct.isCommend }</td>
			<td align="center">${pageproduct.isShow }</td>
			<td align="center">
			<a href="#" class="pn-opt">查看</a> | <a href="#" class="pn-opt">修改</a> | <a href="#" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a> | <a href="../sku/list.do?productId=${pageproduct.id }" class="pn-opt">库存</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15">
	<span class="r inb_a page_b"> <a href="list.do?name=${name}&pageNum=1"><font size="2">首页</font></a> <c:if test="${pageproducts.pageNum<=1}">
			<font size="2">上一页</font>
		</c:if> <c:if test="${pageproducts.pageNum>1}">
			<a href="list.do?name=${name}&pageNum=${pageproducts.pageNum-1}"><font size="2">上一页</font></a>
		</c:if> &nbsp; 
		   <c:if test="${pageproducts.pageNum<=3 }">
           <c:forEach begin="1" end="5" var="ps">
				<c:if test="${pageproducts.pageNum==ps}">
					<strong>${ps}</strong>
				</c:if>
				<c:if test="${pageproducts.pageNum!=ps}">
					<a href="list.do?name=${name}&pageNum=${ps}">${ps}</a>
				</c:if>
				&nbsp;
			</c:forEach> 
			</c:if>
			<c:if test="${pageproducts.pageNum-pageproducts.pages>=-3 }">
           	<c:forEach begin="${pageproducts.pages-5 }" end="${pageproducts.pages }" var="ps">
				<c:if test="${pageproducts.pageNum==ps}">
					<strong>${ps}</strong>
				</c:if>
				<c:if test="${pageproducts.pageNum!=ps}">
					<a href="list.do?name=${name}&pageNum=${ps}">${ps}</a>
				</c:if>
				&nbsp;
			</c:forEach> 
			</c:if>
			<c:if test="${pageproducts.pageNum>3 && pageproducts.pageNum-pageproducts.pages<-3 }">
			<c:forEach begin="${pageproducts.pageNum-2 }" end="${pageproducts.pageNum+2 }" var="ps">
				<c:if test="${pageproducts.pageNum==ps}">
					<strong>${ps}</strong>
				</c:if>
				<c:if test="${pageproducts.pageNum!=ps}">
					<a href="list.do?name=${name}&pageNum=${ps}">${ps}</a>
				</c:if>
				&nbsp;
			</c:forEach>
			</c:if>
			
			
            <c:if test="${pageproducts.pageNum>=pageproducts.pages}">
			<font size="2">下一页</font>
		</c:if> <c:if test="${pageproducts.pageNum<pageproducts.pages}">
			<a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=${pageproducts.pageNum+1}"><font size="2">下一页</font></a>
		</c:if> <a href="list.do?name=${name}&isDisplay=${isDisplay}&pageNum=${pageproducts.pages}"><font size="2">尾页</font></a> 共<var>${pageproducts.pages}</var>页 到第 <input type="text" size="3" id="PAGENO" />页 <input type="button" onclick="javascript:window.location.href = '/product/list.do?&amp;isShow=0&amp;pageNo=' + $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip" />

	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/>
<input class="add" type="button" value="上架" onclick="isShow();"/>
<input class="del-button" type="button" value="下架" onclick="isHide();"/></div>
</form>
</div>
</body>
</html>