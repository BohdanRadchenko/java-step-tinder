<header class="header">
    <nav class="header__nav">
        <a class="d-inline-block" href="/">
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
                    <span>
                        <img
                                <#if CURRENT_USER.avatar()?? && CURRENT_USER.avatar()?has_content>
                                    src="${CURRENT_USER.avatar()}"
                                <#else >
                                    src="static/img/avatar.jpg"
                                </#if>
                                alt="avatar"
                                width="44"
                        >
                    </span>
                </button>
            </form>
        </#if>
    </div>
</header>