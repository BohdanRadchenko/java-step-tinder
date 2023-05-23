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
<h1>Like or no?</h1>

<div class="circle">
    <img src="${avatar}" alt="Avatar" width="80" height="80">
</div>

<div class="button">
    <button onclick="handleButtonClick('Yes')">Yes</button>
    <button onclick="handleButtonClick('No')">No</button>
</div>
<div id="result"></div>

<script>
    function handleButtonClick(response) {
        console.log('Нажата кнопка: ' + response);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', window.location.href, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                document.getElementById('result').innerHTML = xhr.responseText;
            }
        };
        xhr.send('response=' + response);
    }
</script>

</body>
</html>
