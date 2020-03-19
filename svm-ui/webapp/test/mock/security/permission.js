mock('/security/permission/save', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock('/security/permission/update', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock("/security/permission/delete/.*", options => {
    return {}
});

mock('/security/permission/queryByType/.*', options => {
    var type = getSinglePathVariable(options.url);
    var result = [];
    if (type == "all" || type == "page") {
        result.push({ id: "p_admin", name: "管理员", type: "page" });
        result.push({ id: "p_test", name: "测试人员", type: "page" });
        result.push({ id: "p_user", name: "普通角色", type: "page" });
    }
    if (type == "all" || type == "service") {
        result.push({ id: "s_user", name: "用户管理", type: "service" });
        result.push({ id: "s_role", name: "角色管理", type: "service" });
        result.push({ id: "s_permission", name: "权限管理", type: "service" });
        result.push({ id: "s_sample", name: "示例权限", type: "service" });
    }
    return result;
});

mock('/security/permission/queryByRoleId', options => {
    var type = getQueryParameter(options.url, "type");
    var result = [];
    if (type == "page") {
        result.push("p_test");
    } else {
        result.push("s_user");
    }
    return result;
});

mock('/security/permission/savePermissionRoles', options => {
    var data = JSON.parse(options.body);
    return data;
});