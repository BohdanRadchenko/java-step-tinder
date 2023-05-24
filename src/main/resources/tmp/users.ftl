<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Like or no?</title>
    <style>
        .rectangle {
            width: 200px;
            height: 300px;
            background-color: #e0e0e0;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        .rectangle img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .button-container {
            display: flex;
            justify-content: center;
            margin-top: 10px;
        }
        .button {
            padding: 10px 20px;
            margin: 0 5px;
        }
    </style>
</head>
<body>
<div class="rectangle">
    <img src="${avatar}" alt="User Avatar">
</div>
<form method="post" action="/users">
    <input type="hidden" name="userFrom" value="${userFrom}">
    <input type="hidden" name="userTo" value="${userTo}">
    <div class="button-container">
        <button type="submit" name="response" value="Yes" class="button">Yes</button>
        <button type="submit" name="response" value="No" class="button">No</button>
    </div>
</form>
</body>
</html>
