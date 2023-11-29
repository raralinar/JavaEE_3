<%@ page import="com.lab3.task4.Sign" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <style>
    button {
          color: white;
          padding: var(--size-bezel) calc(var(--size-bezel) * 2);
          background: #fab700;
          font-weight: 900;
          border-color: black;
          &[type=reset]{
            background: var(--color-background);
            font-weight: 200;
          }
    }
    table {
        border-color: #fab700;
    }
    .center th, td {
      border: 1px solid black;
      border-radius: 10px;
      border-color: #fab700;
    }
    .center {
      margin-left: auto;
      margin-right: auto;
      margin-top: 40px;
    }
    </style>
    <link href="main.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="<c:url value="/static/jquery-3.6.0.min.js"/>"></script>
    <title>TicTac</title>
</head>
<body align="center">
<h1>хох-Крестики-нолики-хох</h1>

<form action="start" method="get">
<button style="height:75px;width:200px"> Начать игру </button>
</form>

<table class="center">
    <tr>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=0'">${data.get(0).getSign()}</td>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=1'">${data.get(1).getSign()}</td>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=2'">${data.get(2).getSign()}</td>
    </tr>
    <tr>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=3'">${data.get(3).getSign()}</td>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=4'">${data.get(4).getSign()}</td>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=5'">${data.get(5).getSign()}</td>
    </tr>
    <tr>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=6'">${data.get(6).getSign()}</td>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=7'">${data.get(7).getSign()}</td>
        <td style="border-color: #fab700" onclick="window.location='/lab3/task4/logic?click=8'">${data.get(8).getSign()}</td>
    </tr>
</table>

<hr>
<c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
<c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>

<c:if test="${winner == CROSSES}">
    <h1>ВЫИГРАЛ ПОЛЬЗОВАТЕЛЬ!</h1>
    <form action="restart" method="post">
        <button style="height:75px;width:200px" onclick="window.location='/lab3/task4/start">Start again</button>
    </form>
</c:if>
<c:if test="${winner == NOUGHTS}">
    <h1>ВЫИГРАЛ КОМПЬЮТЕР!</h1>
    <form action="restart" method="post">
            <button style="height:75px;width:200px" onclick="window.location='/lab3/task4/start">Start again</button>
        </form>
</c:if>
<c:if test="${draw}">
    <h1>ПОБЕДИЛА ДРУЖБА</h1>
   <form action="restart" method="post">
           <button style="height:75px;width:200px" onclick="window.location='/lab3/task4/start">Start again</button>
   </form>
</c:if>

</body>
</html>