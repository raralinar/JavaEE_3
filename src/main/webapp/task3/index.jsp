<%@ page import="com.lab3.task1.Music"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.lab3.task1.LibraryServlet.*"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<%@ page contentType="charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Информационная система</title>
    <meta charset=utf-8>
    <link rel="stylesheet" href="../styles.css">
</head>
<body>
<form action="filter" method="post">
<h1>Информация о фонотеке</h1>

<table align="center" width="113%" cellpadding="0" cellspacing="10" border="0">
<tr>
<td><input id="myInput" type="text" name="filterSinger" placeholder="Search for singer.." title="Type in a singer"/></td>
<td><button>OK</button></td>
</tr>
</table>
</form>
<form action="information" method="get">
    <table id="myTable">
        <tr class="header">
            <th>id</th>
            <th>Певец</th>
            <th>Название</th>
            <th>Жанр</th>
        </tr>
        <c:forEach items="${audioFiles}" var="audioFile">
              <tr>
                  <td><c:out value="${audioFile.id}"/></td>
                  <td><c:out value="${audioFile.singer}"/></td>
                  <td><c:out value="${audioFile.title}"/></td>
                  <td><c:out value="${audioFile.genre}"/></td>
              </tr>
        </c:forEach>
    </table>
    <button> Получить полную информацию </button>
</form>
<table align="center" width="100%" cellpadding="0" cellspacing="10" border="0">
<tr>
<td><article class="l-design-widht">
        <div class="card card--inverted">
        <form action="information" method="post">
        <h2>Добавить песню</h2>
            <input type="hidden" name="action" value="add">
            Певец: <input type="text" name="singer"><br>
            Название: <input type="text" name="title"><br>
            Жанр: <input type="text" name="genre"><br>
            <div class="button-group">
                <button>Добавить</button>
            </div>
        </form>
        </div>
        </article>
        </td>
<td><article class="l-design-widht">
            <div class="card card--inverted">
            <form action="information" method="post">
            <h2>Удалить песню</h2>
                 <input type="hidden" name="action" value="delete">
                 ID: <input type="text" name="id"><br>
                <div class="button-group">
                    <button>Удалить</button>
                </div>
            </form>
            </div>
            </article>
            </td>
</tr>
</table>

    <script>
    function myFunction() {
      var input, filter, table, tr, td, i, txtValue;
      input = document.getElementById("myInput");
      filter = input.value.toUpperCase();
      table = document.getElementById("myTable");
      tr = table.getElementsByTagName("tr");
      for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
          txtValue = td.textContent || td.innerText;
          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
          } else {
            tr[i].style.display = "none";
          }
        }
      }
    }
    </script>
</body>
</html>
