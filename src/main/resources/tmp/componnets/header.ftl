<header class="header">
    <nav class="header__nav">
        <a class="d-none d-md-inline-block" href="/">
            <img src="static/img/logo_tinder.png" height="46" alt="logo">
        </a>
        <a class="d-none d-md-inline-block" href="/users">users</a>
        <a class="d-none d-md-inline-block" href="/liked">liked</a>
    </nav>

    <div class="header__current">
        <form action="/logout" method="POST" class="header__current-logout">
            <button type="submit" class="rst-btn">
                <i class="fas fa-sign-out-alt"></i>
            </button>
        </form>

        <#if CURRENT_USER??>
            <form action="/profile" class="header__current-profile">
                <button type="submit" class="rst-btn">
                    <img src="${CURRENT_USER.avatar()}" alt="avatar" width="44">
                </button>
            </form>
        </#if>
    </div>
</header>