import request from '@/utils/request';

/**
 * 查询配置信息
 */
export function queryConfig() {
    return request({
        url: '/dashboard/config/query'
    });
};

/**
 * 保存配置信息
 * @param {Object} config 配置信息
 */
export function saveConfig(config) {
    return request({
        url: '/dashboard/config/save',
        data: config
    });
};

/**
 * 更新配置信息
 * @param {Object} config 配置信息
 */
export function updateConfig(config) {
    return request({
        url: '/dashboard/config/update',
        data: config
    });
};

/**
 * 删除配置信息
 * @param {Object} config 配置信息
 */
export function removeConfig(config) {
    return request({
        url: '/dashboard/config/delete/' + config.id
    });
};

/**
 * 保存布局信息
 * @param {Array} ids id列表
 */
export function saveLayout(ids) {
    return request({
        url: '/dashboard/layout/save',
        data: ids
    });
};

/**
 * 查询API列表
 */
export function queryApis() {
    return request({
        url: '/dashboard/api/query'
    });
};

/**
 * 查询服务列表
 */
export function queryHealth() {
    return request({
        url: '/dashboard/health'
    });
};

/**
 * 最大响应时间TOP10
 * @param {String} window 时间窗口
 * @param {Number} count 记录数
 */
export function queryMaxRtTop(window, count) {
    return request({
        url: '/dashboard/top/rt/max',
        params: { window, count }
    });
};

/**
 * 平均响应时间TOP10
 * @param {String} window 时间窗口
 * @param {Number} count 记录数
 */
export function queryAvgRtTop(window, count) {
    return request({
        url: '/dashboard/top/rt/avg',
        params: { window, count }
    });
};

/**
 * QPSTOP10
 * @param {String} window 时间窗口
 * @param {String} mode 内容：service--服务，api--API
 * @param {Number} count 记录数
 */
export function queryQpsTop(window, mode, count) {
    return request({
        url: '/dashboard/top/qps',
        params: { window, mode, count }
    });
};

/**
 * 限流次数TOP10
 * @param {String} window 时间窗口
 * @param {String} mode 内容：service--服务，api--API
 * @param {Number} count 记录数
 */
export function queryBlockTop(window, mode, count) {
    return request({
        url: '/dashboard/top/block',
        params: { window, mode, count }
    });
};

/**
 * 平均响应时间时序图
 * @param {String} window 时间窗口
 * @param {String} mode 内容：service--服务，api--API
 * @param {String} names 服务名称列表或者API列表
 */
export function queryAvgRtChart(window, mode, names) {
    return request({
        url: '/dashboard/chart/rt',
        params: { window, mode, names }
    });
};

/**
 * QPS时序图
 * @param {String} window 时间窗口
 * @param {String} mode 内容：service--服务，api--API
 * @param {String} names 服务名称列表或者API列表
 */
export function queryQpsChart(window, mode, names) {
    return request({
        url: '/dashboard/chart/qps',
        params: { window, mode, names }
    });
};