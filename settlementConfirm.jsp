<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="./css/hibiscus.css">
<link rel="stylesheet" href="./css/common.css">
	<link rel="shortcut icon" href="./images/favicon.ico">

<title>決済確認</title>

</head>
<body>

	<jsp:include page="header.jsp" />

	<div>
		<h1>決済確認画面</h1>

		<s:if test="#session.destinationInfoDtoList.size() > 0">
			<div class="complete">宛先を選択してください。</div>

			<s:form action="SettlementCompleteAction">

				<table>
					<thead class="thead">
						<tr>
							<th><label>#</label></th>
							<th><label>姓</label></th>
							<th><label>名</label></th>
							<th><label>ふりがな</label></th>
							<th><label>住所</label></th>
							<th><label>電話番号</label></th>
							<th><label>メールアドレス</label></th>
						</tr>
					</thead>

					<tbody class="tbody">
						<s:iterator value="#session.destinationInfoDtoList" status="st">
							<tr>
								<td><s:if test="#st.index == 0">
										<input class="radio" type="radio" name="destinationId" checked="checked" value="<s:property value='id'/>"/>
									</s:if><s:else>
										<input class="radio" type="radio" name="destinationId" value="<s:property value='id'/>"/>
									</s:else>
									<input type="hidden" name="id" value="<s:property value='id'/>"/>
								</td>
								<td><s:property value="familyName" /></td>
								<td><s:property value="firstName" /></td>
								<td><s:property value="familyNameKana" /><span>　</span> <s:property value="firstNameKana" /></td>
								<td><s:property value="userAddress" /></td>
								<td><s:property value="telNumber" /></td>
								<td><s:property value="email" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>

				<div class="submit_box">
					<s:submit value="決済" id="submit"/>
				</div>
			</s:form>

		</s:if>

		<s:else>
			<div class="complete">宛先情報が登録されていません。</div>
		</s:else>

		<div class="submit_box">
			<form action="CreateDestinationAction">
				<s:submit value="新規登録" id="submit" />
			</form>
		</div>
	</div>

	<div class="margin"></div>

	<%@ include file="footer.jsp" %>

</body>
</html>