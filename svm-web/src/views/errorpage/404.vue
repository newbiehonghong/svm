<template>
    <cmpError v-bind:errorCode="errorCode" v-bind:errorDesc="errorDesc"></cmpError>
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
