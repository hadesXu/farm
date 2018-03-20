var token = localStorage.getItem("token");
var userId = localStorage.getItem("userId");
if (userId.length == 0 || token.length == 0) {
    //登录
}

//校验提交
function jsonTokenAjax(url, method, data, callBack, errorback) {
    $.ajax({
        url: url,
        dataType: 'json',
        method: method,
        data: tokenJoint(data),
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: callBack,
        error: errorback
    });
}

//非校验提交
function jsonAjax(url, method, data, callBack, errorback) {
    $.ajax({
        url: url,
        dataType: 'json',
        method: method,
        data: data,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: callBack,
        error: errorback
    });
}

//拼接token
function tokenJoint(jsonstr) {
    return jsonstr.push(getAuthorization());
}

//获取用户请求校验信息
function getAuthorization() {
    var tokenArr =
        {
            "userId": userId,
            "token": decodeURIComponent(token)
        }
    return tokenArr;
}

