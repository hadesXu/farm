var token = localStorage.getItem("token");
var userId = localStorage.getItem("userId");
var serverUrl = "http://localhost:8098";
if (userId.length == 0 || token.length == 0) {
    //登录
}

//校验提交
function jsonTokenAjax(url, method, data, callBack, errorback) {
    $.ajax({
        url: serverUrl + url,
        dataType: 'json',
        method: method,
        data: tokenJoint(data),
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: callBack,
        error: errorback
    });
}

//非校验提交
function jsonAjax(url, method, callBack, errorback) {
    $.ajax({
        url: serverUrl + url,
        dataType: 'json',
        method: method,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: callBack,
        error: errorback
    });
}

//拼接token
function tokenJoint(jsonstr) {
    return mergeJsonObject(jsonstr, getAuthorization());
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

function mergeJsonObject(jsonbject1, jsonbject2) {
    var resultJsonObject = {};
    for (var attr in jsonbject1) {
        resultJsonObject[attr] = jsonbject1[attr];
    }
    for (var attr in jsonbject2) {
        resultJsonObject[attr] = jsonbject2[attr];
    }
    return resultJsonObject;
};

