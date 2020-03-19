export default {
    data() {
        return {
            params: {
                list: {},
                edit: {}
            },
            visible: {
                list: false,
                edit: false
            }
        };
    },
    created() {
        this.initComponent();
    },
    methods: {
        initComponent() {
            this.visible.list = true;
            this.bus.$on("nav.jump", name => {
                if (name.indexOf("/") == 0) {
                    name = "list";
                }
                for (let key in this.visible) {
                    this.visible[key] = key == name;
                }
            });
        },
        toggleComponent(name, params) {
            this.params[name] = params;
            for (let key in this.visible) {
                this.visible[key] = key == name;
            }
        },
        doForward(name, title, params) {
            this.toggleComponent(name, params);
            this.bus.$emit("nav.forward", { name, title });
        },
        doBackward(name, params) {
            this.toggleComponent(name, params);
            this.bus.$emit("nav.backward");
        }
    }
};