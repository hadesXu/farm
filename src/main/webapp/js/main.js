var token = localStorage.getItem("token");
var userId = localStorage.getItem("userId");
//var serverUrl = "http://api.chirs.top";
var serverUrl = "http://127.0.0.1:8098";
// var serverUrl = "http://api.gxyxgtz.com/";
//校验登录
function checkLogin() {
    if (checkNull(userId) || checkNull(token) || userId < 1) {
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
    return object == null || object == undefined || object == '' || object == "undefined";
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
//    alert('error:' + JSON.stringify(msg));
    if (msg.status == "4") {
        localStorage.clear();
        checkLogin();
    }
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

function callBackError(data) {
    if (data.error.code == "1007") {
        location.href = "v_user-login.htm"
    }
    alert(data.error.message);
}

function setUser(data) {
    localStorage.clear();
    localStorage.setItem("userId", data.result.userId);
    localStorage.setItem("nickName", data.result.nick);
    localStorage.setItem("token", decodeURIComponent(data.result.token));
    localStorage.setItem("phone", data.result.phone);
    localStorage.setItem("face", data.result.face);
    localStorage.setItem("shareUrl", data.result.shareUrl);
    localStorage.setItem("parentId", data.result.parentId);
    location.href = "index.html";
}

function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    // return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    return m + '-' + d + ' ' + h + ':' + minute;
};

function formatDateTimeAll(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
};

function formatDateYear(inputTime) {
    var date = new Date(inputTime);
    return date.getFullYear();
}
function formatDateMonth(inputTime) {
    var date = new Date(inputTime);
    return date.getMonth() + 1;
}

function formatDateDay(inputTime) {
    var date = new Date(inputTime);
    var d = date.getDate();
    return d < 10 ? ('0' + d) : d;
}

function footerHtml() {
    var innerHtml = "";
    innerHtml += "<div class='tools' style='width:86%;'>";
    innerHtml += " <ul class='tool-ul clearfix' style='top:-28%;'>";
    innerHtml += "   <li class='tools04'>";
    innerHtml += "      <a href='index.html' tppabs='javascript:;'>";
    innerHtml += "           <img src='../img/wdnc.png' tppabs='javascript:;'>";
    innerHtml += "        </a>";
    innerHtml += "   </li>";
    innerHtml += "    <li class='tools04'>";
    innerHtml += "       <a href='farm_ck.html'>";
    innerHtml += "           <img src='../img/wdck.png' tppabs='javascript:;'>";
    innerHtml += "        </a>";
    innerHtml += "     </li>";
    innerHtml += "     <li class='tools04 up-open'>";
    innerHtml += "     <a href='farm_jyq.htm'>";
    innerHtml += "           <img src='../img/jyq.png' tppabs='javascript:;'>";
    innerHtml += "        </a>";
    innerHtml += "     </li>";
    innerHtml += "     <li class='tools04'>";
    innerHtml += "     <a href='javascript:;' onclick='toSC();'>";
    innerHtml += "           <img src='../img/gwsc.png' tppabs='javascript:;'>";
    innerHtml += "        </a>";
    innerHtml += "    </li>";
    innerHtml += "    <li class='tools04'> ";
    innerHtml += "        <a href='v_user.html' tppabs='javascript:;'>  ";
    innerHtml += "            <img src='../img/grxx.png' tppabs='javascript:;'> ";
    innerHtml += "           </a> ";
    innerHtml += "    </li> ";
    innerHtml += "  </ul> ";
    innerHtml += "</div> ";
    return innerHtml;

}

function toSC() {
    location.href = "http://w.gxjex.com/app/index.php?i=12&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&id=3105&from=singlemessage&isappinstalled=0";
}

function getTimestamp(dateStr) {
    date = dateStr.substring(0, 19);
    date = dateStr.replace(/-/g, '/');
    return new Date(date).getTime();
}

function formatBigDecimal(val) {
//金额转换 分->元 保留2位小数 并每隔3位用逗号分开 1,234.56
    var str = (val).toFixed(2) + '';
    var intSum = str.substring(0, str.indexOf(".")).replace(/\B(?=(?:\d{3})+$)/g, ',');//取到整数部分
    var dot = str.substring(str.length, str.indexOf("."))//取到小数部分搜索
    var ret = intSum + dot;
    return ret;
}


