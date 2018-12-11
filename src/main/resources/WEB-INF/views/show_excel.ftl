<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EXCEL导出结果</title>
</head>
<body>
<div align="center">
    <span style="font-size: 30px;">百达IBK API响应结果：<span style="color: red">${restResult.message}</span><br/><br/>
    <table style="text-align: center" width="800" border="1" cellspacing="1" cellpadding="0">
        <tr>
            <td>快递单号</td>
        </tr>
    <#list restResult.data as result>
        <tr>
            <td>${result.transNum}</td>
        </tr>
    </#list>
    </table>
</div>
</body>
</html>