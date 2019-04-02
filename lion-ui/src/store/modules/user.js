import * as auth from '@/utils/auth'

const user = {
  state: {
    token: auth.getToken(),
    name: '',
    roles: [],
    visitPermissions: [],
    operationPermissions: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_VISIT_PERMISSIONS: (state, permissions) => {
      if(permissions.length === 0) permissions.push("dashboard");
      state.visitPermissions = permissions
    },
    SET_OPERATION_PERMISSIONS: (state, permissions) => {
      state.operationPermissions = permissions
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {

      const userName = userInfo.username.trim();
      return new Promise((resolve, reject) => {
        auth.verifyIdentifyCode(userInfo.identifyCode).then(r =>{
          if(r.passed){
            //服务器端获取TOKEN
            auth.generateToken(userName).then(result => {
              if(result.passed){
                //验证用户的TOKEN
                auth.verifyToken(userName,userInfo.password,result.token).then(_result => {
                  if (_result.passed) {
                    auth.setToken(_result.token);
                    commit('SET_TOKEN', _result.token);
                    resolve();
                  }
                  else  {
                    auth.removeToken();
                    reject(_result.message);
                  }
                }).catch(error => {
                  auth.removeToken();
                  reject(error);
                });
              }
              else  reject(result.message);
            }).catch(error => {
              reject(error);
            });
          }
          else  reject("验证码错误，请重新输入");
        }).catch(error=>{
          reject(error);
        });
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        auth.getUserInfo().then(result => {
          commit('SET_NAME', result.chinaName);
          //记录当前用户的访问权限
          commit('SET_VISIT_PERMISSIONS', result.visitPrivileges);
          //记录当前用户的操作权限
          commit('SET_OPERATION_PERMISSIONS', result.operationPrivileges);
          resolve(result)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        auth.logout().then(() => {
          commit('SET_TOKEN', '');
          commit('SET_ROLES', []);
          auth.removeToken();
          resolve();
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        auth.removeToken()
        resolve()
      })
    }
  }
}

export default user
