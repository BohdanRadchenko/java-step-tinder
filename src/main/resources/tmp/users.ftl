<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Like or no?</title>
    <style>
        .circle {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background-color: #e0e0e0;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .button {
            padding: 10px 20px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<img src="${avatar}" alt="User Avatar">
<form method="post" action="/users">
    <input type="hidden" name="userFrom" value="${userFrom}">
    <input type="hidden" name="userTo" value="${userTo}">
    <button type="submit" name="response" value="Yes">Yes</button>
    <button type="submit" name="response" value="No">No</button>
</form>
</body>

</html>
