<!DOCTYPE html>
<html lang="en">
<head>
    <title>Liked Page</title>
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
    </style>
</head>
<body>
<h1>Liked Users</h1>
<ul>
    <#list likedUsers as user>
        <li>
            <div class="rectangle">
                <img src="${user.avatar()}" alt="User Avatar">
            </div>
        </li>
    </#list>
</ul>
</body>
</html>
