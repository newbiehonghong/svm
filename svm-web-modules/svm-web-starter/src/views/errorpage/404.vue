<template>
    <cmp-error :errorCode="errorCode" :errorDesc="errorDesc"></cmp-error>
</template>

<script>
import cmpError from './error';
export default {
    data: function() {
        return {
            errorCode: '404',
            errorDesc: '你所访问的页面不存在'
        }
    },
    components: {
        cmpError
    }
}
</script>
