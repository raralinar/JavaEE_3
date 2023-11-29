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
    <style>
    * {
      box-sizing: border-box;
    }

    #myInput {
      background-position: 10px 10px;
      background-repeat: no-repeat;
      width: 100%;
      font-size: 16px;
      padding: 12px 20px 12px 40px;
      border: 1px solid #ddd;
      margin-bottom: 12px;
    }

    #myTable {
      border-collapse: collapse;
      width: 100%;
      border: 1px solid #ddd;
      font-size: 18px;
    }

    #myTable th, #myTable td {
      text-align: left;
      padding: 12px;
    }

    #myTable tr {
      border-bottom: 1px solid #ddd;
    }

    #myTable tr.header, #myTable tr:hover {
      background-color: #f1f1f1;
    }
    .round-button {
      display: block;
      width: 50px;
      height: 50px;
      line-height: 50px;
      border: 2px solid #f5f5f5;
      border-radius: 50%;
      color: #f5f5f5;
      text-align: center;
      text-decoration: none;
      background: #464646;
      box-shadow: 0 0 3px gray;
      font-size: 20px;
      font-weight: bold;
    }

    .round-button:hover {
      background: #262626;
    }

    @import url('https://rsms.me/inter/inter.css');

    :root {
      --color-light: white;
      --color-dark: #212121;
      --color-signal: #fab700;

      --color-background: var(--color-light);
      --color-text: var(--color-dark);
      --color-accent: var(--color-signal);

      --size-bezel: .5rem;
      --size-radius: 4px;

      line-height: 1.4;

      font-family: 'Inter', sans-serif;
      font-size: calc(.6rem + .4vw);
      color: var(--color-text);
      background: var(--color-background);
      font-weight: 300;
      padding: 0 calc(var(--size-bezel) * 3);
    }

    h1, h2, h3 {
      font-weight: 900;
    }

    mark {
      background: var(--color-accent);
      color: var(--color-text);
      font-weight: bold;
      padding: 0 0.2em;
    }

    .card {
      background: var(--color-background);
      padding: calc(4 * var(--size-bezel));
      margin-top: calc(4 * var(--size-bezel));
      border-radius: var(--size-radius);
      border: 3px solid var(--color-shadow, currentColor);
      box-shadow: .5rem .5rem 0 var(--color-shadow, currentColor);

      &--inverted {
        --color-background: var(--color-dark);
        color: var(--color-light);
        --color-shadow: var(--color-accent);
      }

      &--accent {
        --color-background: var(--color-signal);
        --color-accent: var(--color-light);
        color: var(--color-dark);
      }

      *:first-child {
        margin-top: 0;
      }
    }


    .l-design-widht {
      max-width: 40rem;
      padding: 1rem;
    }

    .input {
      position: relative;

      &__label {
        position: absolute;
        left: 0;
        top: 0;
        padding: calc(var(--size-bezel) * 0.75) calc(var(--size-bezel) * .5);
        margin: calc(var(--size-bezel) * 0.75 + 3px) calc(var(--size-bezel) * .5);
        background: pink;
        white-space: nowrap;
        transform: translate(0, 0);
        transform-origin: 0 0;
        background: var(--color-background);
        transition: transform 120ms ease-in;
        font-weight: bold;
        line-height: 1.2;
      }
      &__field {
        box-sizing: border-box;
        display: block;
        width: 100%;
        border: 3px solid currentColor;
        padding: calc(var(--size-bezel) * 1.5) var(--size-bezel);
        color: currentColor;
        background: transparent;
        border-radius: var(--size-radius);

        &:focus,
        &:not(:placeholder-shown) {
          & + .input__label {
            transform: translate(.25rem, -65%) scale(.8);
            color: var(--color-accent);
          }
        }
      }
    }


    .button-group {
      margin-top: calc(var(--size-bezel) * 2.5);
    }

    button {
      color: currentColor;
      padding: var(--size-bezel) calc(var(--size-bezel) * 2);
      background: var(--color-accent);
      border: none;
      border-radius: var(--size-radius);
      font-weight: 900;

      &[type=reset]{
        background: var(--color-background);
        font-weight: 200;
      }
    }

    button + button {
      margin-left: calc(var(--size-bezel) * 2);
    }

    .icon {
      display: inline-block;
      width: 1em; height: 1em;
      margin-right: .5em;
    }

    .hidden {
      display: none;
    }

    </style>
</head>
<body>
<form action="filter" method="post">
<h1>Информация о фонотеке</h1>

<table align="center" width="113%" cellpadding="0" cellspacing="10" border="0">
<tr>
<td><input type="text" id="myInput" name="filterSinger" placeholder="Search for singer.." title="Type in a singer"/></td>
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
<td>
    <article class="l-design-widht">
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
<td>
    <article class="l-design-widht">
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
