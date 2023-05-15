<form class="form-signup px-3">
    <h1 class="h3 mb-3 font-weight-normal">Sign Up</h1>

    <!--    NAME    -->
    <div class="d-flex flex-row align-items-center mb-4">
        <i class="fas fa-user fa-lg me-3 fa-fw mr-2"></i>
        <div class="form-outline flex-fill mb-0 flex-grow-1">
            <label for="inputLogin" class="sr-only">Your Login</label>
            <input name="${LOGIN}" type="text" id="inputLogin" class="form-control" placeholder="Your Login"
                   required
                   autofocus
            >
        </div>
    </div>

    <!--    EMAIL    -->
    <div class="d-flex flex-row align-items-center mb-4">
        <i class="fas fa-envelope fa-lg me-3 fa-fw mr-2"></i>
        <div class="form-outline flex-fill mb-0 flex-grow-1">
            <label for="inputEmail" class="sr-only">Email address</label>
            <input name="${EMAIL}" type="email" id="inputEmail" class="form-control" placeholder="Email address"
                   required>
        </div>
    </div>

    <!--    PASSWORD    -->
    <div class="d-flex flex-row align-items-center mb-4">
        <i class="fas fa-lock fa-lg me-3 fa-fw mr-2"></i>
        <div class="form-outline flex-fill mb-0 flex-grow-1">
            <label for="inputPassword" class="sr-only">Password</label>
            <input name="${PASSWORD}" type="password" id="inputPassword" class="form-control"
                   placeholder="Password"
                   required>
        </div>
    </div>

    <!--    PASSWORD REPEAT    -->
    <div class="d-flex flex-row align-items-center mb-4">
        <i class="fas fa-key fa-lg me-3 fa-fw mr-2"></i>
        <div class="form-outline flex-fill mb-0 flex-grow-1">
            <label for="inputPasswordRepeat" class="sr-only">Repeat your password</label>
            <input name="${PASSWORD_CONFIRM}" type="password" id="inputPasswordRepeat" class="form-control"
                   placeholder="Repeat your password"
                   required>
        </div>
    </div>

    <div class="row px-3">
        <button
                class="btn btn-lg btn-primary btn-block m-auto col-12 col-sm-8"
                type="submit"
        >
            Sign Up
        </button>
    </div>

    <p class="small fw-bold mt-2 pt-1 mb-0">Have already an account?
        <a href="${LOGIN_LINK}" class="link-danger">
            <u>Login here</u>
        </a>
    </p>
</form>
