<template>
    <div class="tags" v-if="showTags">
        <ul>
            <li class="tags-li" v-for="(item,index) in tagsList" :class="{'active': isActive(item.path)}" :key="index">
                <router-link :to="item.path" class="tags-li-title">
                    {{item.title}}
                </router-link>
                <span class="tags-li-icon" @click="closeTag(index)" v-if="index != 0"><i class="el-icon-close"></i></span>
            </li>
        </ul>
    </div>
</template>

<script>
    import bus from '@/bus';
    export default {
        data() {
            return {
                tagsList: []
            }
        },
        methods: {
            isActive(path) {
                return path === this.$route.path;
            },
            // 关闭单个标签
            closeTag(index) {
                const delItem = this.tagsList.splice(index, 1)[0];
                if(delItem.path === this.$route.path) {//关闭的是当前活动标签
                    const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
                    this.$router.push(item ? item.path : '/');//push path有可能把参数丢了
                }

                const cmpName = delItem.name;
                cmpName && bus.$emit('removeTag', cmpName);
            },
            // 设置标签
            setTag(route) {
                const isExist = this.tagsList.some(item => {
                    if(route.name) {
                        //组件内打开tab页用name指定，因为用path指定涉及到tab页刷新等问题比较难处理
                        return item.name === route.name;
                    }
                    return item.path === route.path;
                });

                const cmpName = route.matched[1].components.default.name;
                if(!isExist) {
                    this.tagsList.push({
                        title: route.meta.title,
                        path: route.path,
                        name: cmpName
                    });
                    cmpName && bus.$emit('createTag', cmpName);
                };  
            }
        },
        computed: {
            showTags() {
                console.log(this.tagsList.length)
                return this.tagsList.length > 0;
            }
        },
        watch:{
            $route(newValue, oldValue) {
                this.setTag(newValue);
            }
        },
        created() {
            this.setTag(this.$route);
        }
    }
</script>

<style>
    .tags {
        position: relative;
        height: 30px;
        overflow: hidden;
        background: #fff;
    }

    .tags ul {
        box-sizing: border-box;
        width: 100%;
        height: 100%;
    }

    .tags-li {
        float: left;
        margin: 3px 5px 2px 3px;
        border-radius: 3px;
        font-size: 12px;
        overflow: hidden;
        cursor: pointer;
        height: 23px;
        line-height: 23px;
        border: 1px solid #e9eaec;
        background: #fff;
        padding: 0 5px 0 12px;
        vertical-align: middle;
        color: #666;
        -webkit-transition: all .3s ease-in;
        -moz-transition: all .3s ease-in;
        transition: all .3s ease-in;
    }

    .tags-li:not(.active):hover {
        background: #f8f8f8;
    }

    .tags-li.active {
        color: #fff;
    }

    .tags-li-title {
        float: left;
        max-width: 80px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        margin-right: 5px;
        color: #666;
        outline: none;
    }

    .tags-li.active .tags-li-title {
        color: #fff;
    }

    .tags-close-box {
        position: absolute;
        right: 0;
        top: 0;
        box-sizing: border-box;
        padding-top: 1px;
        text-align: center;
        width: 110px;
        height: 30px;
        background: #fff;
        box-shadow: -3px 0 15px 3px rgba(0, 0, 0, .1);
        z-index: 10;
    }

</style>
