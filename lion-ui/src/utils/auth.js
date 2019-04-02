import Vue from 'vue'
import Storage from 'vue-ls';
import request from '@/utils/request'
import CryptoJS from "crypto-js";

let options = {
  name: 'ls', // name variable Vue.[ls] or this.[$ls],
  storage: 'session', // storage name session, local, memory
};
Vue.use(Storage, options);

const TokenKey = 'Admin-Token'

export function getToken() {
  return Vue.ls.get(TokenKey)
}

export function setToken(token) {
  return Vue.ls.set(TokenKey, token,5 * 60 * 60 * 1000)//保存5小时
}

export function removeToken() {
  return Vue.ls.clear()
}

export function generateToken(userName) {
  return request.get('/system/auth/authority/token/' + userName);
}
export function verifyIdentifyCode(identifyCode) {
  return request.put('system/verification/code/verify/' + identifyCode);
}

export function verifyToken(userName, password, token) {
  function signData() {
    let hmacSha = CryptoJS.enc.Base64.stringify(CryptoJS.HmacSHA256(userName, password));
    return CryptoJS.enc.Base64.stringify(CryptoJS.HmacSHA256(hmacSha, token));
  }
  let params={
    "token": token,
    "signData": signData()
  };
  return request.put('/system/auth/authority/verify',params);
}

export function getUserInfo() {
  return request.get('/systemmgr/organization/users/current/privileges');
}
export function logout() {
  return request.put('/system/auth/authority/logout')
}

