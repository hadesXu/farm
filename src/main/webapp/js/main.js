var token = localStorage.getItem("token");
var userId = localStorage.getItem("userId");
var serverUrl = "http://120.79.137.135:8098";

//校验登录
function checkLogin() {
    if (checkNull(userId) || checkNull(token)) {
        location.href = "v_user-login.htm"
    }
}
//校验提交
function jsonTokenAjax(url, method, data, callBack, errorback) {
    $.ajax({
        url: serverUrl + url,
        dataType: 'json',
        type: method,
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
        type: method,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: callBack,
        error: errorback
    });
}

function jsonPostAjax(url, data, callBack, errorback) {
    $.ajax({
        url: serverUrl + url,
        dataType: 'json',
        type: "POST",
        data: data,
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
function checkNull(object) {
    return object == null || object == undefined || object == '';
}

function randomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function errorback(msg) {
    if (msg.status == "0") {
        localStorage.clear();
        checkLogin();
    }
    //alert('error:' + JSON.stringify(msg));
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}


