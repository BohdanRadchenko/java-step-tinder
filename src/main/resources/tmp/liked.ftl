<!DOCTYPE html>
<html>
<head>
    <title>Liked Page</title>
</head>
<body>
<h1>Liked Users</h1>
<ul>
    <#list likedUsers as userId>
        <#assign user = users[userId?string]>

        <li>
            <img src="${user.avatar}" alt="User Avatar">
        </li>
    </#list>
</ul>
</body>
</html>
