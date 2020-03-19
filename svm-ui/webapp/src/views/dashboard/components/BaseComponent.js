export default {
    props: {
        config: {
            type: Object,
            required: true
        },
        fullscreen: {
            type: Boolean,
            default: false
        }
    },
    mounted() {
        this.bus.$on("dashboard.refresh", this.doRefresh);
    },
    beforeDestroy() {
        this.bus.$off("dashboard.refresh", this.doRefresh);
    },
    methods: {
        doRefresh(window, id) {
            if (!id || id == this.config.id) {
                this.refresh(window);
            }
        }
    }
};