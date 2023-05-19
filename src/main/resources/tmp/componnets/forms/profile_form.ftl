<#if CURRENT_USER??>
    <form class="form-signup px-3 text-left form-profile" method="POST" enctype="multipart/form-data">

        <div class="row justify-content-center mb-4">
            <div class="avatar-img profile">
                <img
                        alt="avatar"
                        <#if CURRENT_USER.avatar()?? && CURRENT_USER.avatar()?has_content>
                            src="${CURRENT_USER.avatar()}"
                        <#else >
                            src="static/img/avatar.jpg"
                        </#if>
                >
            </div>
        </div>

<#--        IMAGE UPLOAD-->
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Avatar</span>
            </div>
            <div class="custom-file pointer">
                <input
                        name="avatar"
                        type="file"
                        class="custom-file-input"
                        accept="image/*"
                        id="inputFileAvatar"
                        <#if CURRENT_USER.avatar()??>
                            value="${CURRENT_USER.avatar()}"
                        <#else >
                            value=""
                        </#if>
                >
                <label
                        class="custom-file-label"
                        for="inputFileAvatar"
                >
                    Choose file
                </label>
            </div>
        </div>

        <!--    NAME    -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="inputFirstName">First name</label>
                <input
                        name="first_name"
                        type="text"
                        class="form-control"
                        id="inputFirstName"
                        placeholder="Enter first name"
                        <#if CURRENT_USER.firstName()??>
                            value="${CURRENT_USER.firstName()}"
                        <#else >
                                value=""
                        </#if>
                        required
                        autofocus
                >
            </div>
            <div class="col-md-6 mb-3">
                <label for="inputLastName">Last name</label>
                <input
                        name="last_name"
                        type="text"
                        class="form-control"
                        id="inputLastName"
                        placeholder="Enter last name"
                        <#if CURRENT_USER.lastName()??>
                            value="${CURRENT_USER.lastName()}"
                        <#else >
                            value=""
                        </#if>
                        required
                >
            </div>
        </div>

        <!--   PROFESSION    -->
        <div class="row">
            <div class="col-12 mb-3">
                <label for="inputProfession">Your profession</label>
                <input
                        id="inputProfession"
                        class="form-control"
                        name="profession"
                        type="text"
                        placeholder="Enter Your Profession"
                        <#if CURRENT_USER.profession()??>
                            value="${CURRENT_USER.profession()}"
                        <#else >
                            value=""
                        </#if>
                        required
                >
            </div>
        </div>

        <!--    LOGIN    -->
        <div class="row">
            <div class="col-12 mb-3">
                <label for="inputLogin">Your Login</label>
                <input
                        id="inputLogin"
                        class="form-control"
                        name="login"
                        type="text"
                        placeholder="Your Login"
                        <#if CURRENT_USER.login()??>
                            value="${CURRENT_USER.login()}"
                        <#else >
                            value=""
                        </#if>
                        required
                        disabled
                >
            </div>
        </div>

        <!--    EMAIL    -->
        <div class="row">
            <div class="col-12 mb-3">
                <label for="inputEmail">Email address</label>
                <input
                        id="inputEmail"
                        class="form-control"
                        name="email"
                        type="email"
                        placeholder="Email address"
                        <#if CURRENT_USER.email()??>
                            value="${CURRENT_USER.email()}"
                        <#else >
                            value=""
                        </#if>
                        required
                        disabled
                >
            </div>
        </div>

        <div class="row px-3">
            <button
                    class="btn btn-lg btn-primary btn-block m-auto col-12 col-sm-8"
                    type="submit"
            >
                Save
            </button>
        </div>
</form>
</#if>