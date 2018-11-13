
const user = {
    state: {
        token: '',
        nickname: '',
        routes: []
    },

    getters: {
        token: state => {
            return state.token;
        },
        nickname: state => {
            return state.nickname;
        },
        routes: state => {
            return state.routes;
        }
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token;
        },
        SET_NICKNAME: (state, nickname) => {
            state.nickname = nickname;
        },
        SET_ROUTES: (state, routes) => {
            state.routes = routes;
        }
    },

    actions: {
        // 登出
        Logout({ commit, state }) {
            commit('SET_TOKEN', '');
            commit('SET_NICKNAME', '');
        }
    }
}

export default user
