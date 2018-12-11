<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>展示创建的快递单列表</title>
</head>
<body>
<span style="font-size: 30px;">百达IBK API响应结果：${restResult.message}</span>
<u>
    <table>
        <tr>
            <td>响应码（1：成功，0失败）</td>
            <td>|</td>
            <td>快递单号</td>
        </tr>
    <#list restResult.data as result>
        <tr>
            <td><#if result.resultCode == '1'>成功</#if><#if result.resultCode == '0'>失败</#if></td>
            <td></td>
            <td>${result.transNum}</td>
        </tr>
    </#list>
    </table>
</body>
</html>