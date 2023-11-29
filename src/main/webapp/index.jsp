<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Starter page</title>
    <style>
        body {
            background-image: url(https://www.schemecolor.com/wallpaper?i=67722&desktop);
        }
        button {
            display: inline-block;
            background-color: #7bd8;
            border-radius: 10px;
            border: 4px double #cccccc;
            color: #ffffff;
            text-align: center;
            font-size: 28px;
            padding: 20px;
            width: 200px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 5px;
        }
        button span {
            cursor: pointer;
            display: inline-block;
            position: relative;
            transition: 0.5s;
        }
        button span:after {
            content: '\00bb';
            position: absolute;
            opacity: 0;
            top: 0;
            right: -20px;
            transition: 0.5s;
        }
        button:hover {
            background-color: #f7c2f9;
        }
        button:hover span {
            padding-right: 25px;
        }
        button:hover span:after {
            opacity: 1;
            right: 0;
        }
        .tasks {
            text-align: center;
            top: 50%;
            position: relative;
        }
    </style>
</head>
<body>
<div class="tasks">
    <button onclick="window.location='/lab3/task1/lib.jsp'" type="button" name="task1">Task1</button>
    <button onclick="window.location='/lab3/task2/lib.jsp'" type="button" name="task2">Task2</button>
    <button onclick="window.location='/lab3/task4/index.jsp'" type="button" name="task5">Task4</button>
</div>
</body>
</html>
