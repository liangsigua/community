/**
 * 一级回复评论方法
 */
function post() {
    var questionId = $("#question_id").val();    //这种方式只有一个   ？？？
    var content = $("#comment_content").val();
    comment2target(questionId, content, 1);
}

/**
 * 二级回复评论方法
 * @param commentId
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, content, 2);
}
//由于一级/二级回复评论都在调用，所以封装成一个方法
function comment2target(targetId, content, type ) {
    if(!content){
        alert("回复的评论内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if(response.code == 200){
                // $("#comment_section").hide();
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=ba9cd94d0bfda4637fc6&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                }else{
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}

/**
 * 展开二级评论方法
 */
function collapseComments(e) {
    //动态取值
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    //获取二级评论的展开状态（data-collapse = in）
    var collapse = e.getAttribute("data-collapse");
    //如果有值说明已经打开了
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");

        //否则说明还没展开过，然后实现展开功能
    }else{
        //当折叠展示出来评论，先发送一个GET请求，获取到data数据
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1){   //不等于1，说明已经加载过评论了，就不去数据库获取数据了，直接展开二级回复列表
            //打开二级评论
            comments.addClass("in");
            //通过这个属性data-collapse = in来判断是否折叠二级评论
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else{                                            //等于1，说明还没加载过评论，接下来使用prepend()方法追加评论写到前端页面去
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {  //获取data下的data，for循环得到comment，把comment都放进去
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comment_list"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //打开二级评论
                comments.addClass("in");
                //通过这个属性data-collapse = in来判断是否折叠二级评论
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}