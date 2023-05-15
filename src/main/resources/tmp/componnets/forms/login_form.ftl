<form class="form-signin" method="POST">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

    <label for="inputEmail" class="sr-only">Email</label>
    <input
            name="${EMAIL}"
            type="email"
            id="inputEmail"
            class="form-control"
            placeholder="Email address"
            required
            autofocus
    >
    <label for="inputPassword" class="sr-only">Password</label>
    <input name="${PASSWORD}" type="password" id="inputPassword" class="form-control" placeholder="Password"
           required>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

    <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account?
        <a href="${REGISTER_LINK}" class="link-danger">Register</a>
    </p>
</form>
