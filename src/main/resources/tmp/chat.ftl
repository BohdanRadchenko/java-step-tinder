<#import "common/template.ftl" as t>
<#import "common/links.ftl" as links>
<#import "common/script.ftl" as script>

<@t.page>
    <@t.head title="Chat">
        <@links.fac/>
    </@t.head>

    <@t.body_auth>
        <#include "componnets/forms/profile_form.ftl">
    <div class="container">
        <div class="row">
            <div class="chat-main col-6 offset-3">
                <div class="col-md-12 chat-header">
                    <div class="row header-one text-white p-1">
                        <div class="col-md-6 name pl-2">
                            <i class="fa fa-comment"></i>
                            <h6 class="ml-1 mb-0">${chat_name}</h6>
                        </div>
                        <div class="col-md-6 options text-right pr-0">
                            <i class="fa fa-window-minimize hide-chat-box hover text-center pt-1"></i>
                            <p class="arrow-up mb-0">
                                <i class="fa fa-arrow-up text-center pt-1"></i>
                            </p>
                            <i class="fa fa-times hover text-center pt-1"></i>
                        </div>
                    </div>
                    <div class="row header-two w-100">
                        <div class="col-md-6 options-left pl-1">
                            <i class="fa fa-video-camera mr-3"></i>
                            <i class="fa fa-user-plus"></i>
                        </div>
                        <div class="col-md-6 options-right text-right pr-2">
                            <i class="fa fa-cog"></i>
                        </div>
                    </div>
                </div>
                <div class="chat-content">
                    <div class="col-md-12 chats pt-3 pl-2 pr-3 pb-3">
                        <ul class="p-0">
                            <#list messages>
                                <#items as item>
                                    <#if (item.user().id() == currentUser)>
                                        <li class="send-msg float-right mb-2">
                                            <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                                                ${item.content()}
                                            </p>
                                        </li>
                                    <#else>
                                        <li class="receive-msg float-left mb-2">
                                            <div class="sender-img">
                                                <img src="http://nicesnippets.com/demo/image1.jpg" class="float-left">
                                            </div>
                                            <div class="receive-msg-desc float-left ml-2">
                                                <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                                                    ${item.content()}
                                                </p>
                                                <span class="receive-msg-time">${item.user().login()}, ${item.getCreatedMessage()}</span>
                                            </div>
                                        </li>
                                    </#if>
                                </#items>
                            </#list>
                        </ul>
                    </div>

                    <form class="send-msg" method="POST">
                        <div class="col-md-12 p-2 msg-box border border-primary">
                            <div class="row">
                                <div class="col-md-2 options-left">
                                    <i class="fa fa-smile-o"></i>
                                </div>
                                <div class="col-md-7 pl-0">
                                    <input type="text" class="border-0" name="message" placeholder=" Send message"/>
                                    <button class="btn btn-outline-secondary " type="submit">Send</button>
                                </div>

                                <div class="col-md-3 text-right options-right">
                                    <i class="fa fa-picture-o mr-2"></i>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    </@t.body_auth>
</@t.page>
