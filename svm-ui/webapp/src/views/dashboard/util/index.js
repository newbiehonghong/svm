export default {
    parseConfig: function (config) {
        if (config.mode == "service" && config.services && config.services.length > 0) {
            var serviceArray = [];
            var arr = config.services.split("|");
            for (let i = 0; i < arr.length; i++) {
                serviceArray.push([arr[i]]);
            }
            config.services = serviceArray;
        }
        if (config.mode == "api" && config.apis && config.apis.length > 0) {
            var apiArray = [];
            var arr = config.apis.split("|");
            for (let i = 0; i < arr.length; i++) {
                var index = arr[i].indexOf("/");
                apiArray.push([arr[i].substring(0, index), arr[i].substring(index)]);
            }
            config.apis = apiArray;
        }
        return config;
    },
    formatServices: function (services) {
        if (!services || services.length == 0) {
            return "";
        }

        var serviceArray = [];
        for (let i = 0; i < services.length; i++) {
            serviceArray.push(services[i][0]);
        }
        return serviceArray.join("|");
    },
    formatApis: function (apis) {
        if (!apis || apis.length == 0) {
            return "";
        }

        var apiArray = [];
        for (let i = 0; i < apis.length; i++) {
            apiArray.push(apis[i][0] + apis[i][1]);
        }
        return apiArray.join("|");
    }
}