<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>展示创建的快递单列表</title>
</head>
<body>
<div align="center">
    <span style="font-size: 30px;">百达IBK API响应结果：<span style="color: red">${restResult.message}</span><br/><br/>
    <table style="text-align: center" width="800" border="1" cellspacing="1" cellpadding="0">
        <tr>
            <td>响应码（1：成功，0失败）</td>
            <td>快递单号</td>
            <td>接收号</td>
        </tr>
    <#list restResult.data as result>
        <tr>
            <td>${result.resultCode},<#if result.resultCode == '1'>成功</#if><#if result.resultCode == '0'>失败</#if></td>
            <td>${result.transNum}</td>
            <td>${result.idxNo}</td>
        </tr>
    </#list>
    </table>
</div>
</body>
</html>