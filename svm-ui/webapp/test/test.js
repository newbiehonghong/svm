/* 
    因为mock时使用正则表达式匹配，所以最好避免一个rest请求的url完全包含另一个请求的url的情况，比如：
    /security/permission/query和/security/permission/queryByRoleId
    要么把/security/permission/query改成/security/permission/queryByType
    要么先定义BASE_URL="http://localhost:8080"，再调用Mock.mock(BASE_URL + "/security/permission/query", fun);
*/
function mock(url, fun) {
    var patten = url;
    patten = patten.replace(/\//g, '\\/');
    Mock.mock(new RegExp(patten), 'post', fun);
}

function getQueryParameter(url, name) {
    if (url.indexOf("?") < 0) {
        return null;
    }
    const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = url.split("?")[1].match(reg);
    return r != null ? r[2] : null;
}

function getSinglePathVariable(url) {
    if (url.indexOf("?") > 0) {
        url = url.substring(0, url.indexOf("?"));
    }
    var arr = url.split('/');
    return arr[arr.length - 1];
}

function requireJs(url) {
    var script = document.createElement("script");
    script.setAttribute("type", "text/javascript");
    script.setAttribute("src", url);
    document.body.appendChild(script);
}